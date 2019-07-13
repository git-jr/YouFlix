package com.paradoxo.youflix.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.R;

import java.util.List;

public class AdapterTeste extends RecyclerView.Adapter {
    private List<String> items;

    public AdapterTeste(List<String> lista) {
        items = lista;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.itemTextView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teste, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String item = items.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.itemTextView.setText(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
