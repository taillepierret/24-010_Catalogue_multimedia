package com.example.cataloguemultimedia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchPageFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchPageFragment newInstance(String param1, String param2)
    {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
}