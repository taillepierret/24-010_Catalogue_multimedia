package com.example.cataloguemultimedia;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SavedLinksStore {

    private static final String PREF = "saved_links_pref";
    private static final String KEY = "links";

    public static void add(Context ctx, String link) {
        if (link == null) return;
        link = link.trim();
        if (link.isEmpty()) return;

        List<String> current = getAll(ctx);
        current.add(link);
        saveAll(ctx, current);
    }

    public static List<String> getAll(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        String raw = sp.getString(KEY, "[]");
        ArrayList<String> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(raw);
            for (int i = 0; i < arr.length(); i++) out.add(arr.optString(i));
        } catch (Exception ignored) {}
        return out;
    }

    public static void clear(Context ctx) {
        saveAll(ctx, new ArrayList<>());
    }

    private static void saveAll(Context ctx, List<String> links) {
        JSONArray arr = new JSONArray();
        if (links != null) {
            for (String s : links) arr.put(s);
        }
        ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY, arr.toString())
                .apply();
    }
}