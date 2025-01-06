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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Initialiser le View Binding
        binding = FragmentSearchPageBinding.inflate(inflater, container, false);

        // Configurer le RecyclerView
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create data list to display
        List<content> resultList = resultListFromAPI;

        // Configure l'adapter pour le RecyclerView
        ContentAdapter adapter = new ContentAdapter(resultList);
        binding.searchRecyclerView.setAdapter(adapter);

        return binding.getRoot(); // Retourne la vue root générée par le binding
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
        binding.contentTypeSpinner.setAdapter(adapter);

        String selectedContentType = contentTypeSpinner.getSelectedItem().toString();

        binding.searchEditText.setText(searchQuery); //on donne la valeur trouvee precedemment
        binding.contentTypeSpinner.setSelection(adapter.getPosition(contentType)); //on donne la valeur trouvee precedemment
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
                
            }
        });
        binding.searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JSONArray jsonArray;
                // Récupérer les valeurs sélectionnées
                Spinner contentTypeSpinner = view.findViewById(R.id.contentTypeSpinner);
                String selectedContentType = contentTypeSpinner.getSelectedItem().toString();
                content_type type = content_type.valueOf(selectedContentType);
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

                API_request.fetchData(searchContent, type.toString(), new API_request.ApiCallback() {
                    @Override
                    public void onSuccess(JSONArray jsonResult)
                    {
                        // Traiter les données
                        resultListFromAPI = ContentRepository.parseJSONContent(jsonResult);
                        // Mettre à jour l'adapter
                        ContentAdapter adapter = new ContentAdapter(resultListFromAPI);
                        binding.searchRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(String error) {
                        // Gérer l'erreur
                        Log.e("API Error", error);
                    }
                });
            }
        });
    }
}