package com.example.cataloguemultimedia.data;

import androidx.annotation.NonNull;

public enum Content_type
{
    MOVIE("films"),
    SERIES("series"),
    ANIME("animes");

    private final String displayName;

    // Constructeur pour associer un nom personnalisé
    Content_type(String displayName)
    {
        this.displayName = displayName;
    }

    // Méthode toString pour retourner le nom personnalisé
    @NonNull
    @Override
    public String toString()
    {
        return displayName;
    }
    public static Content_type fromString(String text)
    {
        for (Content_type b : Content_type.values()) {
            if (b.displayName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
    //voici comment utiliser la méthode fromString
    //Content_type type = Content_type.fromString("Film");
}
