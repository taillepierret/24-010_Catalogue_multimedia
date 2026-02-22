package com.example.cataloguemultimedia.data;

import androidx.annotation.NonNull;

public enum Soundtrack
{
    VF("VF"),
    VOSTFR("VOSTFR"),
    VO("VO"),
    MULTI("MULTI");

    private final String displayName;

    // Constructeur pour associer un nom personnalisé
    Soundtrack(String displayName) {
        this.displayName = displayName;
    }

    // Méthode toString pour retourner le nom personnalisé
    @NonNull
    @Override
    public String toString() {
        return displayName;
    }
}
