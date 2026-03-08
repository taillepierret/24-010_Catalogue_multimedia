package com.example.cataloguemultimedia.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cataloguemultimedia.data.Content;

import java.util.ArrayList;
import java.util.List;

public class WishlistViewModel extends ViewModel {

    private final MutableLiveData<List<Content>> wishlistLiveData =
            new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Content>> getWishlist() {
        return wishlistLiveData;
    }

    public void add(Content content) {
        if (content == null) return;

        List<Content> current = wishlistLiveData.getValue();
        if (current == null) current = new ArrayList<>();

        // Anti-doublon simple (à améliorer si tu as un vrai id unique)
        for (Content c : current) {
            if (c != null && c.getTitle() != null && c.getTitle().equals(content.getTitle())) {
                return;
            }
        }

        List<Content> updated = new ArrayList<>(current);
        updated.add(content);
        wishlistLiveData.setValue(updated);
    }

    public void remove(Content content) {
        List<Content> current = wishlistLiveData.getValue();
        if (current == null) return;

        List<Content> updated = new ArrayList<>(current);
        updated.remove(content); // marche mieux si Content a equals/hashCode
        wishlistLiveData.setValue(updated);
    }
}
