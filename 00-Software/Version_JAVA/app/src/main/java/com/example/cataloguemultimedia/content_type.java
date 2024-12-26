package com.example.cataloguemultimedia;

import androidx.annotation.NonNull;

public enum content_type {
    MOVIE("Film"),
    SERIES("Series"),
    ANIME("Anime");

    private final String displayName;

    // Constructeur pour associer un nom personnalisé
    content_type(String displayName) {
        this.displayName = displayName;
    }

    // Méthode toString pour retourner le nom personnalisé
    @NonNull
    @Override
    public String toString() {
        return displayName;
    }
}
