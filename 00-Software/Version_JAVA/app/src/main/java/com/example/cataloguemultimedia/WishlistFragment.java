package com.example.cataloguemultimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cataloguemultimedia.data.ContentAdapter;
import com.example.cataloguemultimedia.viewmodel.WishlistViewModel;

public class WishlistFragment extends Fragment {

    private ContentAdapter wishlistAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        RecyclerView rv = view.findViewById(R.id.wishlistRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Pas de bouton "+" dans wishlist -> on passe null
        wishlistAdapter = new ContentAdapter(null);
        rv.setAdapter(wishlistAdapter);

        WishlistViewModel wishlistViewModel =
                new ViewModelProvider(requireActivity()).get(WishlistViewModel.class);

        wishlistViewModel.getWishlist().observe(getViewLifecycleOwner(), list -> {
            wishlistAdapter.updateData(list);
        });

        return view;
    }
}
