package com.paradoxo.youflix.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.R;

import java.util.List;

public class AdapterTeste extends RecyclerView.Adapter {
    private List<String> items;
    private Context context;

    public AdapterTeste(List<String> lista, Context context) {
        items = lista;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String item = items.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.img_toolbar));

    }

    
    @Override
    public int getItemCount() {
        return items.size();
    }
}
