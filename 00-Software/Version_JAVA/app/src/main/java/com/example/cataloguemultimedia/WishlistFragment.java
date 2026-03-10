package com.example.cataloguemultimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cataloguemultimedia.data.ContentAdapter;

public class WishlistFragment extends Fragment {

    private ContentAdapter wishlistAdapter;
    private DownloadFlowFragment.WishlistViewModel wishlistViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        RecyclerView rv = view.findViewById(R.id.wishlistRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        wishlistAdapter = new ContentAdapter(null);
        rv.setAdapter(wishlistAdapter);

        wishlistViewModel =
                new ViewModelProvider(requireActivity()).get(DownloadFlowFragment.WishlistViewModel.class);

        wishlistViewModel.getWishlist().observe(getViewLifecycleOwner(), list -> {
            wishlistAdapter.updateData(list);
        });

        // Drag & drop
        ItemTouchHelper.SimpleCallback callback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {

                        int from = viewHolder.getAdapterPosition();
                        int to = target.getAdapterPosition();

                        // 1) bouger dans l'adapter (UI)
                        wishlistAdapter.moveItem(from, to);

                        // 2) persister l'ordre dans le ViewModel
                        wishlistViewModel.setWishlist(wishlistAdapter.getItemsSnapshot());
                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        // pas de swipe
                    }

                    @Override
                    public boolean isLongPressDragEnabled() {
                        return true;
                    }
                };

        new ItemTouchHelper(callback).attachToRecyclerView(rv);

        // Bouton "Lancer le téléchargement"
        Button start = view.findViewById(R.id.startDownloadButton);
        start.setOnClickListener(v -> {
            // On navigue vers un fragment d'assistant (wizard)
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, new DownloadFlowFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}