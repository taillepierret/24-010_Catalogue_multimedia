package com.example.cataloguemultimedia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cataloguemultimedia.databinding.FragmentWelcomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchPageFragment extends Fragment
{
    // TODO: Rename and change types of parameters
    private String searchQuery;
    private String contentType;
    private FragmentWelcomeBinding binding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search_page, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Simule une liste de résultats
        List<content> resultList = new ArrayList<>();
        resultList.add(new content ("Titre 1", "Description 1", content_type.MOVIE, new ArrayList<>(Arrays.asList(Soundtrack.VF, Soundtrack.VF, Soundtrack.VOSTFR))));
        resultList.add(new content ("Titre 2", "Description 2", content_type.MOVIE, new ArrayList<>(Arrays.asList(Soundtrack.VOSTFR, Soundtrack.VO))));
        // Ajoute d'autres résultats selon tes données

        ContentAdapter adapter = new ContentAdapter(resultList);
        recyclerView.setAdapter(adapter);

        return view;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Récupérer le Spinner
        Spinner contentTypeSpinner = view.findViewById(R.id.contentTypeSpinner);

        // Convertir les enums en liste de chaînes
        String[] contentTypes = new String[content_type.values().length];
        for (int i = 0; i < content_type.values().length; i++) {
            contentTypes[i] = content_type.values()[i].name();
        }

        // Créer un adapter pour le Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                contentTypes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contentTypeSpinner.setAdapter(adapter);

        String selectedContentType = contentTypeSpinner.getSelectedItem().toString();

        /*binding.searchButton.setEnabled(false);
        binding.searchEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                binding.searchButton.setEnabled(!s.toString().isEmpty());
            }
        });
        binding.searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Récupérer les valeurs sélectionnées
                Spinner contentTypeSpinner = view.findViewById(R.id.contentTypeSpinner);
                String selectedContentType = contentTypeSpinner.getSelectedItem().toString();
                String searchContent = binding.searchEditText.getText().toString();

                // Créer un Bundle pour envoyer les données au fragment cible
                Bundle bundle = new Bundle();
                bundle.putString("searchContent", searchContent);
                bundle.putString("selectedContentType", selectedContentType);

                // Créer le nouveau fragment
                SearchPageFragment searchPageFragment = new SearchPageFragment();
                searchPageFragment.setArguments(bundle);  // Passer les arguments au fragment

                // Naviguer vers le fragment de recherche
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, searchPageFragment);
                fragmentTransaction.commit();
            }
        });*/
    }
}