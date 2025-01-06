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
import com.example.cataloguemultimedia.data.ContentRepository;
import com.example.cataloguemultimedia.data.Soundtrack;
import com.example.cataloguemultimedia.data.content;
import com.example.cataloguemultimedia.data.content_type;
import com.example.cataloguemultimedia.databinding.FragmentSearchPageBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchPageFragment extends Fragment
{
    // TODO: Rename and change types of parameters
    private String searchQuery;
    private String contentType;
    private List<content> resultListFromAPI = new ArrayList<content>();
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
        adapter = new ContentAdapter(new ArrayList<>()); // Initialisation de l'adapter
        binding.searchRecyclerView.setAdapter(adapter);

        // Charger les données depuis l'API au démarrage
        fetchDataFromAPI(searchQuery, contentType);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurer le Spinner pour les types de contenu
        String[] contentTypes = new String[content_type.values().length];
        for (int i = 0; i < content_type.values().length; i++) {
            contentTypes[i] = content_type.values()[i].name();
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
            content_type type = content_type.valueOf(selectedContentType);

            // Recharger les données depuis l'API avec les nouvelles valeurs
            fetchDataFromAPI(searchContent, type.toString());
        });
    }

    /**
     * Méthode pour récupérer les données de l'API et mettre à jour l'adapter.
     */
    private void fetchDataFromAPI(String searchContent, String contentType) {
        API_request.fetchData(searchContent, contentType, new API_request.ApiCallback() {
            @Override
            public void onSuccess(JSONArray jsonResult) {
                // Convertir le résultat JSON en liste d'objets `content`
                List<content> resultList = ContentRepository.parseJSONContent(jsonResult);

                // Mettre à jour l'adapter avec les nouvelles données
                adapter.updateData(resultList);
            }

            @Override
            public void onFailure(String error) {
                // Gérer l'erreur et afficher un message
                Log.e("API Error", "Erreur lors de la récupération des données : " + error);
            }
        });
    }
}