package com.example.cataloguemultimedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private final List<content> resultList;

    public ContentAdapter(List<content> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        content item = resultList.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.soundtrackTextView.setText(item.getSoundtrack().toString());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView, soundtrackTextView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            soundtrackTextView = itemView.findViewById(R.id.soundTrackTextView);
        }
    }
}
