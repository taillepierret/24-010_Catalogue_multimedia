package com.example.cataloguemultimedia;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cataloguemultimedia.data.ContentAdapter;
import com.example.cataloguemultimedia.data.ContentJsonParser;
import com.example.cataloguemultimedia.data.Content;
import com.example.cataloguemultimedia.data.Content_type;
import com.example.cataloguemultimedia.databinding.FragmentSearchPageBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SearchPageFragment extends Fragment
{
    // TODO: Rename and change types of parameters
    private String searchQuery;
    private String contentType;
    private @NonNull FragmentSearchPageBinding binding;
    private ContentAdapter adapter;

    private String pendingQuery = null;
    private String pendingType  = null;
    private ArrayAdapter<String> spinnerAdapter; // pour pouvoir setSelection depuis l'extérieur


    public SearchPageFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Récupérer les arguments transmis au fragment
        if (getArguments() != null)
        {
            searchQuery = getArguments().getString("searchContent");
            contentType = getArguments().getString("selectedContentType");
            Log.d("SearchPageFragment", "searchQuery: " + searchQuery);
            Log.d("SearchPageFragment", "contentType: " + contentType);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchPageBinding.inflate(inflater, container, false);

        // ViewModel
        DownloadFlowFragment.WishlistViewModel wishlistViewModel =
                new ViewModelProvider(requireActivity()).get(DownloadFlowFragment.WishlistViewModel.class);

        // RecyclerView
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Adapter (UNE seule fois)
        adapter = new ContentAdapter(content -> {
            showAudioChoiceDialog(content, wishlistViewModel);
        });

        binding.searchRecyclerView.setAdapter(adapter);

        // Charger les données au démarrage
        if (searchQuery != null && contentType != null) {
            fetchDataFromAPI(searchQuery, contentType);
        }

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ✅ 1) initialise l'adapter (AVANT de l'utiliser)
        String[] contentTypes = new String[Content_type.values().length];
        for (int i = 0; i < Content_type.values().length; i++) {
            contentTypes[i] = Content_type.values()[i].name();
        }

        spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                contentTypes
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.contentTypeSpinner.setAdapter(spinnerAdapter);

        // Restaurer les valeurs précédemment sélectionnées
        binding.searchEditText.setText(searchQuery != null ? searchQuery : "");

        if (contentType != null && spinnerAdapter != null) {
            int pos = spinnerAdapter.getPosition(contentType);
            if (pos >= 0) binding.contentTypeSpinner.setSelection(pos);
        }


        // Bouton de recherche
        binding.searchButton.setOnClickListener(v -> {
            // Récupérer les valeurs actuelles du champ de recherche et du Spinner
            String searchContent = binding.searchEditText.getText().toString();
            String selectedContentType = binding.contentTypeSpinner.getSelectedItem().toString();
            Content_type type = Content_type.valueOf(selectedContentType);

            // Recharger les données depuis l'API avec les nouvelles valeurs
            fetchDataFromAPI(searchContent, type.toString());
        });

        // Si une recherche a été demandée avant que la vue/spinner soit prêt
        if (pendingQuery != null || pendingType != null) {
            applyExternalSearch(pendingQuery, pendingType);
            pendingQuery = null;
            pendingType  = null;
        }

    }

    /**
     * Méthode pour récupérer les données de l'API et mettre à jour l'adapter.
     */
    private void fetchDataFromAPI(String searchContent, String contentType){

        binding.loadingProgressBar.setVisibility(View.VISIBLE);
        API_request.fetchDataFromZt(searchContent, contentType, new API_request.ApiCallback() {

            @Override
            public void onSuccess(JSONArray jsonResult) {
                if (!isAdded() || binding == null) return;
                requireActivity().runOnUiThread(() -> {
                    List<Content> resultList = ContentJsonParser.parseJSONContent(jsonResult);
                    adapter.updateData(resultList);
                    binding.loadingProgressBar.setVisibility(View.GONE);
                });
            }

            @Override
            public void onFailure(String error) {
                if (!isAdded() || binding == null) return;
                requireActivity().runOnUiThread(() -> {
                    adapter.updateData(new ArrayList<>());
                    binding.loadingProgressBar.setVisibility(View.GONE);
                });

                Log.e("API Error", error);
            }
        });
    }

    public void performSearchFromOutside(String query) {
        performSearchFromOutside(query, null);
    }

    public void performSearchFromOutside(String query, String selectedContentType) {
        // Si la vue n’est pas prête, on stocke et on appliquera après (dans onViewCreated)
        if (!isAdded() || binding == null) {
            pendingQuery = query;
            pendingType  = selectedContentType;
            return;
        }

        applyExternalSearch(query, selectedContentType);
    }

    private void applyExternalSearch(String query, String selectedContentType) {
        if (!isAdded() || binding == null) return;

        String q = (query != null) ? query : "";
        binding.searchEditText.setText(q);

        // Type: si on nous en donne un, on le force dans le spinner
        if (selectedContentType != null && spinnerAdapter != null) {
            int pos = spinnerAdapter.getPosition(selectedContentType);
            if (pos >= 0) binding.contentTypeSpinner.setSelection(pos);
        }

        // Type final utilisé pour l’API (spinner si possible, sinon ALL)
        String typeToUse = "ALL";
        if (binding.contentTypeSpinner.getSelectedItem() != null) {
            typeToUse = binding.contentTypeSpinner.getSelectedItem().toString();
        } else if (selectedContentType != null) {
            typeToUse = selectedContentType;
        }

        fetchDataFromAPI(q, typeToUse);
    }


    private void showAudioChoiceDialog(Content item, DownloadFlowFragment.WishlistViewModel wishlistViewModel) {

        ArrayList<String> audios = item.getSoundtrack(); // ton getter existe déjà

        if (audios == null || audios.isEmpty()) {
            android.widget.Toast.makeText(requireContext(),
                    "Aucune version audio disponible",
                    android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        // Si une seule version -> on ajoute direct (pas besoin de demander)
        if (audios.size() == 1) {
            addToWishlist(item, audios.get(0), wishlistViewModel);
            return;
        }

        final String[] items = audios.toArray(new String[0]);
        final int[] selectedIndex = {0};

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Quelle version audio ?")
                .setSingleChoiceItems(items, 0, (dialog, which) -> selectedIndex[0] = which)
                .setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Ajouter", (dialog, which) -> {
                    String chosenAudio = items[selectedIndex[0]];
                    addToWishlist(item, chosenAudio, wishlistViewModel);
                })
                .show();
    }

    private void addToWishlist(Content item, String chosenAudio, DownloadFlowFragment.WishlistViewModel wishlistViewModel) {

        // IMPORTANT : on crée une COPIE pour éviter de modifier l’objet de la liste de recherche
        Content toSave = new Content(
                item.getTitle(),
                item.getLinkToImage(),
                new ArrayList<>(item.getLinkToDownload()),
                new ArrayList<>(item.getSoundtrack()),
                item.GetDate()
        );

        toSave.setSelectedAudio(chosenAudio);

        wishlistViewModel.add(toSave);

        android.widget.Toast.makeText(
                requireContext(),
                item.getTitle() + " (" + chosenAudio + ") enregistré dans la wishlist",
                android.widget.Toast.LENGTH_SHORT
        ).show();
    }






}