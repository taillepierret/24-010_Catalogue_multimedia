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

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            String selectedType = binding.contentTypeSpinner.getSelectedItem().toString();

            if (!searchContent.isEmpty()) {
                ((MainActivity) requireActivity()).openSearchWithQuery(searchContent, selectedType);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


