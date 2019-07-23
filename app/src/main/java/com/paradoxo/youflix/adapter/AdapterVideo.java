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
import com.paradoxo.youflix.modelo.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter {
    private final boolean videoNaVertical;
    private List<Video> videos;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View v, String idVideo);
    }

    public void setonItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public AdapterVideo(List<Video> videos, Context context, boolean videoNaVertical) {
        this.videos = videos;
        this.context = context;
        this.videoNaVertical = videoNaVertical;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView tituloVideoItemTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.itemImageView);
            if (videoNaVertical)
                tituloVideoItemTextView = itemView.findViewById(R.id.tituloVideoItemTextView);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (videoNaVertical) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_vertical, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_horizontal, parent, false);
        }
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
        if (videoNaVertical) {
            viewHolder.tituloVideoItemTextView.setText(video.getTitulo());

            viewHolder.tituloVideoItemTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, video.getId());
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return videos.size();
    }
}
