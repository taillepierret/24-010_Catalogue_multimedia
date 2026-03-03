package com.example.cataloguemultimedia;

import android.os.Bundle;

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

public class SearchPageFragment extends Fragment
{
    // TODO: Rename and change types of parameters
    private String searchQuery;
    private String contentType;
    private List<Content> resultListFromAPI = new ArrayList<Content>();
    private @NonNull FragmentSearchPageBinding binding;
    private ContentAdapter adapter;
    private String lastSearchQuery;

    private String pendingQuery;

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
        adapter = new ContentAdapter(new ArrayList<>()); // Initialisation de l'adapter
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

        /*if (pendingQuery != null) {
            performSearch(pendingQuery);
            pendingQuery = null;
        }*/

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
    }

    /**
     * Méthode pour récupérer les données de l'API et mettre à jour l'adapter.
     */
    private void fetchDataFromAPI(String searchContent, String contentType){

        binding.loadingProgressBar.setVisibility(View.VISIBLE);
        lastSearchQuery = searchContent;
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

    public void setPendingQuery(String query) {
        this.pendingQuery = query;
    }

    public void performSearchFromOutside(String query) {

        if (!isAdded() || binding == null) return;

        binding.searchEditText.setText(query);

        // On récupère le type seulement s’il existe
        String selectedType = "ALL"; // valeur par défaut sûre

        if (binding.contentTypeSpinner.getSelectedItem() != null) {
            selectedType = binding.contentTypeSpinner.getSelectedItem().toString();
        }

        fetchDataFromAPI(query, selectedType);
    }



}