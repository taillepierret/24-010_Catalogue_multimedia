package com.example.cataloguemultimedia;

import static com.example.cataloguemultimedia.API_request.getZtLink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cataloguemultimedia.data.Content;
import com.example.cataloguemultimedia.data.ContentJsonParser;
import com.example.cataloguemultimedia.data.Content_type;
import com.example.cataloguemultimedia.databinding.FragmentWelcomeBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentWelcomeBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getZtLinkFromAPI();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1) Spinner
        String[] contentTypes = new String[Content_type.values().length];
        for (int i = 0; i < Content_type.values().length; i++) {
            contentTypes[i] = Content_type.values()[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                contentTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 2) Bouton activé seulement si texte
        binding.searchButton.setEnabled(false);
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                binding.searchButton.setEnabled(s != null && !s.toString().trim().isEmpty());
            }
        });

        // 3) Click (une seule fois, pas de setOnClickListener dans setOnClickListener)
        binding.searchButton.setOnClickListener(v -> {
            String searchContent = binding.searchEditText.getText().toString().trim();
            if (!searchContent.isEmpty()) {
                ((MainActivity) requireActivity()).openSearchWithQuery(searchContent);
            }
        });
    }

    private void getZtLinkFromAPI(){
        API_request.getZtLink(new API_request.ApiGetZtLinkCallback()
        {
            @Override
            public void onSuccess(String ZtLink)
            {
                // Mettre à jour l'adapter avec les nouvelles données
                //adapter.updateData(ZtLink);
                Log.d("API Result", ZtLink);
            }

            @Override
            public void onFailure(String error)
            {
                // Gérer l'erreur et afficher un message
                String ZtLink = "";
                //adapter.updateData(ZtLink);
                Log.e("API Error", "Erreur lors de la récupération des données : " + error);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

