package com.example.cataloguemultimedia;

import static com.example.cataloguemultimedia.API_request.getZtLink;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Spinner: on met directement l'enum (toString() => displayName)
        ArrayAdapter<Content_type> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                Content_type.values()
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.contentTypeSpinner.setAdapter(spinnerAdapter);

        binding.searchButton.setEnabled(false);
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                binding.searchButton.setEnabled(s != null && !s.toString().trim().isEmpty());
            }
        });

        binding.searchButton.setOnClickListener(v -> {
            String searchContent = binding.searchEditText.getText().toString().trim();

            // Ici tu récupères l'enum (propre)
            Content_type selectedType = (Content_type) binding.contentTypeSpinner.getSelectedItem();

            if (!searchContent.isEmpty()) {
                // Si ta méthode attend un String: envoie displayName via toString()
                ((MainActivity) requireActivity()).openSearchWithQuery(searchContent, selectedType.toString());

                // OU (mieux) change openSearchWithQuery pour prendre Content_type directement
                // ((MainActivity) requireActivity()).openSearchWithQuery(searchContent, selectedType);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


