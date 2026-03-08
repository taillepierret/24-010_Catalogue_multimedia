package com.example.cataloguemultimedia.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cataloguemultimedia.R;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    public interface OnAddClickListener {
        void onAddClicked(Content content);
    }

    private final List<Content> resultList = new ArrayList<>();
    private final OnAddClickListener addClickListener;

    public ContentAdapter(OnAddClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }

    // si tu veux garder ton updateData()
    public void updateData(List<Content> newList) {
        resultList.clear();
        if (newList != null) resultList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Content item = resultList.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.soundtrackTextView.setText(String.valueOf(item.getSoundtrack()));

        if (addClickListener == null) {
            holder.addToWishlistButton.setVisibility(View.GONE);
        } else {
            holder.addToWishlistButton.setVisibility(View.VISIBLE);
            holder.addToWishlistButton.setOnClickListener(v -> addClickListener.onAddClicked(item));
        }


        // TODO: charger l'image item.getLinkToImage() avec Glide/Picasso si tu veux
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView descriptionTextView;
        TextView soundtrackTextView;
        ImageView myImageView;
        Button addToWishlistButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            soundtrackTextView = itemView.findViewById(R.id.soundTrackTextView);
            myImageView = itemView.findViewById(R.id.myImageView);
            addToWishlistButton = itemView.findViewById(R.id.addToWishlistButton);
        }
    }
}
