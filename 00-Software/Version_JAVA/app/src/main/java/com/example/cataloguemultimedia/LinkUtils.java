package com.example.cataloguemultimedia;

import java.net.URI;

public class LinkUtils {

    private static final String BASE = "https://www.zone-telechargement.promo/";

    public static String toAbsoluteUrl(String link) {
        if (link == null) return null;

        link = link.trim();
        if (link.isEmpty()) return null;

        // Déjà une URL complète
        if (link.startsWith("http://") || link.startsWith("https://")) {
            return link;
        }

        // Résout correctement "/?p=..." ou "?p=..." etc.
        return URI.create(BASE).resolve(link).toString();
    }
}
