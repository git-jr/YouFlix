package com.paradoxo.youflix.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.activity.ExibirVideoActivity;
import com.paradoxo.youflix.R;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.PlayList;
import com.paradoxo.youflix.util.YTinfo;

import java.io.IOException;
import java.util.List;

public class AdapterPlayList extends RecyclerView.Adapter {
    private List<PlayList> playLists;
    private Context context;

    public AdapterPlayList(List<PlayList> playLists, Context context) {
        this.playLists = playLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloPlayListTextView;
        RecyclerView recyclerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloPlayListTextView = itemView.findViewById(R.id.tituloPlayListTextView);
            recyclerView = itemView.findViewById(R.id.playlistRecyclerView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final PlayList playList = playLists.get(position);

        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tituloPlayListTextView.setText(playList.getNome());

        RecyclerView recyclerView = viewHolder.recyclerView;
        PaginaVideo pgVideoVP = new PaginaVideo();
        pgVideoVP.setIdPlayLiit(playList.getId());

        LoadVideos loadVideos = new LoadVideos(pgVideoVP, recyclerView);
        loadVideos.execute();

    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadVideos extends AsyncTask<Void, Void, PaginaVideo> {
        private PaginaVideo pgVideoVP;
        private RecyclerView recyclerView;

        private LoadVideos(PaginaVideo pgVideoVP, RecyclerView recyclerView) {
            this.pgVideoVP = pgVideoVP;
            this.recyclerView = recyclerView;
        }

        @Override
        protected PaginaVideo doInBackground(Void... voids) {
            try {
                return new YTinfo(context).listarVideosDoCanal(pgVideoVP, false);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(PaginaVideo paginaVideo) {
            super.onPostExecute(paginaVideo);

            if (paginaVideo != null) {
                AdapterVideo adapterVideo = new AdapterVideo(paginaVideo.getVideos(), context, false);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(adapterVideo);

                adapterVideo.setonItemClickListener(new AdapterVideo.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, String idVideo) {
                        Intent intent = new Intent(context, ExibirVideoActivity.class);
                        intent.putExtra("idVideo", idVideo);
                        context.startActivity(intent);
                    }
                });

            }
        }
    }
}
