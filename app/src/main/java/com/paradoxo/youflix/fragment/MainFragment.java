package com.paradoxo.youflix.fragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.ExibirVideoActivity;
import com.paradoxo.youflix.R;
import com.paradoxo.youflix.adapter.AdapterPlayList;
import com.paradoxo.youflix.adapter.AdapterVideo;
import com.paradoxo.youflix.enums.AbasEnum;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.modelo.PaginaPlayList;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.PlayList;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTinfo;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import static com.paradoxo.youflix.enums.AbasEnum.ABA_VIDEO;
import static com.paradoxo.youflix.enums.AbasEnum.NENHUMA_ABA;


public class MainFragment extends Fragment {
    private TextView videoTextView, sobreTextView, minhaListaTextView;
    private View view;
    private AbasEnum abaAtual = AbasEnum.NENHUMA_ABA;

    private OnItemListenerMain lister;

    public interface OnItemListenerMain {
        void onItemListerEscolherAba(AbasEnum abaAtual);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemListenerMain) {
            lister = (OnItemListenerMain) context;
        }
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null){
            Log.e("SaveInsta",savedInstanceState.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        carregarBanner();
        carregarPlaylist();
        configurarBotoesToolbar();
        
        return view;
    }

    private void configurarBotoesToolbar() {
        videoTextView = view.findViewById(R.id.videosTextView);
        sobreTextView = view.findViewById(R.id.sobreTextView);
        minhaListaTextView = view.findViewById(R.id.minhListaTextView);

        videoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abaAtual != ABA_VIDEO) {
                    deslizarTextoVideo();
                    carregarVideosRecentes();
                } else {
                    lister.onItemListerEscolherAba(abaAtual);
                }
            }
        });
    }


    private void deslizarTextoVideo() {
        abaAtual = ABA_VIDEO;
        ObjectAnimator expandirX = ObjectAnimator.ofFloat(videoTextView, View.SCALE_X, 1.2f);
        ObjectAnimator expandirY = ObjectAnimator.ofFloat(videoTextView, View.SCALE_Y, 1.2f);
        expandirY.setDuration(100);
        expandirX.setDuration(100);

        final ObjectAnimator moverX = ObjectAnimator.ofFloat(videoTextView, "translationX", -35f);
        moverX.setDuration(300);
        moverX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fadeOutSobre = ObjectAnimator.ofFloat(sobreTextView, View.ALPHA, 0, 0);
        ObjectAnimator fadeOutMinhaLista = ObjectAnimator.ofFloat(minhaListaTextView, View.ALPHA, 0, 0);

        expandirX.start();
        expandirY.start();
        fadeOutSobre.start();
        fadeOutMinhaLista.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moverX.start();
                videoTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_triangulo_baixo, 0);
            }
        }, 100);

    }

    public void restaurarTextoVideo() {
        abaAtual = NENHUMA_ABA;
        carregarPlaylist();

        ObjectAnimator expandirX = ObjectAnimator.ofFloat(videoTextView, View.SCALE_X, 1.0f);
        ObjectAnimator expandirY = ObjectAnimator.ofFloat(videoTextView, View.SCALE_Y, 1.0f);
        expandirY.setDuration(100);
        expandirX.setDuration(100);

        final ObjectAnimator moverX = ObjectAnimator.ofFloat(videoTextView, "translationX", 35f);
        moverX.setDuration(300);
        moverX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator fadeOutSobre = ObjectAnimator.ofFloat(sobreTextView, View.ALPHA, 0, 1);
        ObjectAnimator fadeOutMinhaLista = ObjectAnimator.ofFloat(minhaListaTextView, View.ALPHA, 0, 1);

        expandirX.start();
        expandirY.start();
        fadeOutSobre.start();
        fadeOutMinhaLista.start();
        videoTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moverX.start();
            }
        }, 100);

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
        protected Canal doInBackground(Void... voids) {
            return new YTinfo(getContext()).carregarBanner();
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
        protected void onPreExecute() {
            super.onPreExecute();
            //esconderLayout();
        }

        @Override
        protected PaginaPlayList doInBackground(Void... voids) {
            try {
                return new YTinfo(getContext()).listaPlayLists(new PaginaPlayList());
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

            liberarLayout();
        }
    }

    private void configurarRecyclerPlaylists(List<PlayList> playLists) {
        RecyclerView recyclerView = view.findViewById(R.id.mainRecycler);
        AdapterPlayList adapterPlayList = new AdapterPlayList(playLists, getContext());
        recyclerView.setAdapter(adapterPlayList);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadVideos extends AsyncTask<Void, Void, PaginaVideo> {
        @Override
        protected PaginaVideo doInBackground(Void... voids) {
            try {
                return new YTinfo(getContext()).listaVideos(new PaginaVideo(), false);
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
        RecyclerView recyclerView = view.findViewById(R.id.mainRecycler);
        AdapterVideo adapterVideo = new AdapterVideo(videos, getContext(), true);
        recyclerView.setAdapter(adapterVideo);

        adapterVideo.setonItemClickListener(new AdapterVideo.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String idVideo) {
                Intent intent = new Intent(getContext(), ExibirVideoActivity.class);
                intent.putExtra("idVideo", idVideo);
                startActivity(intent);
            }
        });
    }

    private void liberarLayout() {
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.GONE);
    }

    private void esconderLayout() {
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.GONE);
    }

}
