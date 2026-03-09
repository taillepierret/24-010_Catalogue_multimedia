package com.example.cataloguemultimedia;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import com.example.cataloguemultimedia.viewmodel.WishlistViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cataloguemultimedia.data.ContentAdapter;
import com.example.cataloguemultimedia.data.ContentJsonParser;
import com.example.cataloguemultimedia.data.Soundtrack;
import com.example.cataloguemultimedia.data.Content;
import com.example.cataloguemultimedia.data.Content_type;
import com.example.cataloguemultimedia.databinding.FragmentSearchPageBinding;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cataloguemultimedia.R;

public class SearchPageFragment extends Fragment
{
    // TODO: Rename and change types of parameters
    private String searchQuery;
    private String contentType;
    private @NonNull FragmentSearchPageBinding binding;
    private ContentAdapter adapter;

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
        // Initialiser le View Binding
        binding = FragmentSearchPageBinding.inflate(inflater, container, false);

        // Configurer le RecyclerView avec un adapter initialisé à vide
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContentAdapter(content -> {
            // action quand on clique sur "+"
            // ex: ajout à la wishlist
        });
        binding.searchRecyclerView.setAdapter(adapter);

        binding.searchRecyclerView.setAdapter(adapter);
        binding.searchRecyclerView.setAdapter(adapter);

        // Charger les données depuis l'API au démarrage
        if (searchQuery != null && contentType != null) {
            fetchDataFromAPI(searchQuery, contentType);
        }


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurer le Spinner pour les types de contenu
        String[] contentTypes = new String[Content_type.values().length];
        for (int i = 0; i < Content_type.values().length; i++) {
            contentTypes[i] = Content_type.values()[i].name();
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                contentTypes
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.contentTypeSpinner.setAdapter(spinnerAdapter);

        // Restaurer les valeurs précédemment sélectionnées
        binding.searchEditText.setText(searchQuery);
        binding.contentTypeSpinner.setSelection(spinnerAdapter.getPosition(contentType));

        // Bouton de recherche
        binding.searchButton.setOnClickListener(v -> {
            // Récupérer les valeurs actuelles du champ de recherche et du Spinner
            String searchContent = binding.searchEditText.getText().toString();
            String selectedContentType = binding.contentTypeSpinner.getSelectedItem().toString();
            Content_type type = Content_type.valueOf(selectedContentType);

            // Recharger les données depuis l'API avec les nouvelles valeurs
            fetchDataFromAPI(searchContent, type.toString());
        });
        /*WishlistViewModel wishlistViewModel = new ViewModelProvider(requireActivity())
                .get(WishlistViewModel.class);

        adapter = new ContentAdapter(content -> {
            wishlistViewModel.add(content);
        });*/


        binding.searchRecyclerView.setAdapter(adapter);

        WishlistViewModel wishlistViewModel =
                new ViewModelProvider(requireActivity()).get(WishlistViewModel.class);

        adapter = new ContentAdapter(content -> {
            showAudioChoiceDialog(content, wishlistViewModel);
        });

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

    public void performSearchFromOutside(String query)
    {
        if (!isAdded() || binding == null) return;

        binding.searchEditText.setText(query);

        // On récupère le type seulement s’il existe
        String selectedType = "ALL"; // valeur par défaut sûre

        if (binding.contentTypeSpinner.getSelectedItem() != null) {
            selectedType = binding.contentTypeSpinner.getSelectedItem().toString();
        }

        fetchDataFromAPI(query, selectedType);
    }

    private void showAudioChoiceDialog(Content item, WishlistViewModel wishlistViewModel) {

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

    private void addToWishlist(Content item, String chosenAudio, WishlistViewModel wishlistViewModel) {

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