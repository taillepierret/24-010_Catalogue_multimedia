package com.example.cataloguemultimedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

    public static void openInBrowser(Context ctx, String url) {
        if (url == null) return;
        url = url.trim();
        if (url.isEmpty()) return;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}