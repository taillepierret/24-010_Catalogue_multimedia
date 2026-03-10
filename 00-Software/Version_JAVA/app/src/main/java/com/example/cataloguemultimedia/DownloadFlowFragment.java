package com.example.cataloguemultimedia;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cataloguemultimedia.data.Content;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class DownloadFlowFragment extends Fragment {

    private WishlistViewModel wishlistViewModel;

    private TextView stepText;
    private TextView titleText;
    private TextView lastSavedText;
    private Button openLinkButton;
    private Button saveClipboardButton;
    private Button nextButton;

    private List<Content> list;
    private int index = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_download_flow, container, false);

        stepText = v.findViewById(R.id.stepText);
        titleText = v.findViewById(R.id.titleText);
        lastSavedText = v.findViewById(R.id.lastSavedText);
        openLinkButton = v.findViewById(R.id.openLinkButton);
        saveClipboardButton = v.findViewById(R.id.saveClipboardButton);
        nextButton = v.findViewById(R.id.nextButton);

        wishlistViewModel = new ViewModelProvider(requireActivity()).get(WishlistViewModel.class);
        list = wishlistViewModel.getWishlist().getValue();

        if (list == null || list.isEmpty()) {
            finishFlow();
            return v;
        }

        renderStep();

        openLinkButton.setOnClickListener(btn -> {
            String link = getCurrentLink();
            if (link == null) return;
            IntentUtils.openInBrowser(requireContext(), link);
        });

        saveClipboardButton.setOnClickListener(btn -> {
            String clipped = readClipboardText(requireContext());
            if (clipped != null && !clipped.trim().isEmpty()) {
                SavedLinksStore.add(requireContext(), clipped);
                lastSavedText.setText("Dernier lien enregistré : " + clipped);
            } else {
                lastSavedText.setText("Dernier lien enregistré : (rien dans le presse-papiers)");
            }
        });

        nextButton.setOnClickListener(btn -> {
            index++;
            if (index >= list.size()) {
                finishFlow();
            } else {
                renderStep();
            }
        });

        return v;
    }

    private void renderStep() {
        int total = list.size();
        Content c = list.get(index);

        stepText.setText("Étape " + (index + 1) + " / " + total);
        titleText.setText(c != null ? c.getTitle() : "(sans titre)");
        lastSavedText.setText("Dernier lien enregistré : (aucun)");
    }

    private String getCurrentLink() {
        Content c = list.get(index);
        if (c == null) return null;

        // On prend le 1er lien de download (adapte si tu veux un autre)
        if (c.getLinkToDownload() != null && !c.getLinkToDownload().isEmpty()) {
            return c.getLinkToDownload().get(0);
        }
        return null;
    }

    private String readClipboardText(Context ctx) {
        ClipboardManager cm = (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm == null) return null;
        ClipData data = cm.getPrimaryClip();
        if (data == null || data.getItemCount() == 0) return null;
        CharSequence cs = data.getItemAt(0).coerceToText(ctx);
        return cs != null ? cs.toString() : null;
    }

    private void finishFlow() {
        // 1) vider wishlist
        if (wishlistViewModel != null) wishlistViewModel.clear();

        // 2) revenir à la wishlist (pop)
        if (isAdded()) {
            requireActivity().getSupportFragmentManager().popBackStack();

            // 3) popup
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Terminé")
                    .setMessage("Tout a été enregistré.")
                    .setPositiveButton("OK", (d, w) -> d.dismiss())
                    .show();
        }
    }

    public static class WishlistViewModel extends ViewModel {

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

        public void setWishlist(List<Content> newList) {
            if (newList == null) newList = new ArrayList<>();
            wishlistLiveData.setValue(new ArrayList<>(newList));
        }

        public void move(int from, int to) {
            List<Content> current = wishlistLiveData.getValue();
            if (current == null) return;
            if (from < 0 || to < 0 || from >= current.size() || to >= current.size()) return;
            if (from == to) return;

            ArrayList<Content> updated = new ArrayList<>(current);
            Content moved = updated.remove(from);
            updated.add(to, moved);
            wishlistLiveData.setValue(updated);
        }

        public void clear() {
            wishlistLiveData.setValue(new ArrayList<>());
        }
    }
}