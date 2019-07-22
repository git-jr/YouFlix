package com.paradoxo.youflix.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.R;
import com.paradoxo.youflix.modelo.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter {
    private List<Video> videos;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, String idVideo);
    }

    public void setonItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    AdapterVideo(List<Video> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.itemImageView);
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
        final Video video = videos.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        Picasso.with(context).load(video.getThumbnail().getUrl()).placeholder(context.getResources().getDrawable(R.color.cinza_4)).into(viewHolder.thumbnailImageView);

        viewHolder.thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, video.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return videos.size();
    }
}
