package com.example.cataloguemultimedia.data;

import androidx.annotation.NonNull;

public enum content_type
{
    MOVIE("films"),
    SERIES("series"),
    ANIME("animes");

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
    public static content_type fromString(String text)
    {
        for (content_type b : content_type.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
    //voici comment utiliser la méthode fromString
    //content_type type = content_type.fromString("Film");
}
