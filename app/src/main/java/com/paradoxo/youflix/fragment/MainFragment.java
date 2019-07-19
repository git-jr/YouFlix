package com.paradoxo.youflix.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.R;
import com.paradoxo.youflix.adapter.AdapterPlayList;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.modelo.PaginaPlayList;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.PlayList;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTinfo;
import com.paradoxo.youflix.util.YTlist;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;


public class MainFragment extends Fragment {

    private View view;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        carregarBanner();
        carregarPlaylist();
        carregarVideosRecentes();


        return view;
    }

    private void carregarBanner() {
        LoadCanal loadCanal = new LoadCanal();
        loadCanal.execute();
    }

    private void carregarPlaylist() {
        LoadPlaylists loadPlaylists = new LoadPlaylists();
        loadPlaylists.execute();
    }

    private void carregarVideosRecentes() {
        LoadVideos loadVideos = new LoadVideos();
        loadVideos.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadCanal extends AsyncTask<Void, Void, Canal> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Canal doInBackground(Void... voids) {
            YTinfo YTinfo = new YTinfo();
            return YTinfo.carregarBanner();
        }

        @Override
        protected void onPostExecute(final Canal canal) {
            super.onPostExecute(canal);
            try {
                Picasso.with(view.getContext()).load(canal.getBanner()).into((ImageView) view.findViewById(R.id.bannerPricipalImageView));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadPlaylists extends AsyncTask<Void, Void, PaginaPlayList> {
        @Override
        protected PaginaPlayList doInBackground(Void... voids) {
            try {
                return YTlist.listaPlayLists(new PaginaPlayList(), false);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(PaginaPlayList paginaPlayList) {
            super.onPostExecute(paginaPlayList);

            if (paginaPlayList != null) {
                for (PlayList playList : paginaPlayList.getPlayLists()) {
                    Log.e("Nome PlayList", playList.getNome());
                    if (playList.getNome().contains("Vídeos com celular")) {
                        paginaPlayList.getPlayLists().set(paginaPlayList.getPlayLists().size() - 1, playList); // Por questão de estética, essa playlist em específico será mostra por último
                        paginaPlayList.getPlayLists().remove(paginaPlayList.getPlayLists().indexOf(playList)); // Por questão de estética, essa playlist em específico será mostra por último
                        break;
                    }
                }

                configurarRecyclerPlaylists(paginaPlayList.getPlayLists());
            }
        }
    }

    private void configurarRecyclerPlaylists(List<PlayList> playLists) {
        RecyclerView recyclerView = view.findViewById(R.id.mainRecycler);
        AdapterPlayList adapterPlayList = new AdapterPlayList(playLists, getContext());
        recyclerView.setAdapter(adapterPlayList);
        recyclerView.smoothScrollToPosition(4);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadVideos extends AsyncTask<Void, Void, PaginaVideo> {
        @Override
        protected PaginaVideo doInBackground(Void... voids) {
            try {
                return YTlist.listaVideos(new PaginaVideo(), false);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(PaginaVideo paginaVideo) {
            super.onPostExecute(paginaVideo);

            if (paginaVideo != null) {
                configurarRecylerVideos(paginaVideo.getVideos());

            }
        }
    }

    private void configurarRecylerVideos(List<Video> videos) {
    }

}
