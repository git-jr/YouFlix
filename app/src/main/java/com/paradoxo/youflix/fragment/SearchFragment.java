package com.paradoxo.youflix.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.ExibirVideoActivity;
import com.paradoxo.youflix.R;
import com.paradoxo.youflix.adapter.AdapterVideo;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTlist;

import java.io.IOException;
import java.util.List;

public class SearchFragment extends Fragment {


    private View view;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        configurarToolbar();

        ((EditText) view.findViewById(R.id.buscaEditText)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e("teclado", "teclado");
                if (v.getText().toString().isEmpty()) {
                    return true;
                } else {
                    new BuscaVideos().execute(v.getText().toString());
                    Log.e("teclado", "execute");
                    return false;
                }
            }
        });

        return view;
    }

    private void configurarToolbar() {
        Toolbar toolbar = view.findViewById(R.id.buscaToolbar);
        toolbar.inflateMenu(R.menu.menu_search);

    }

    private void esconderLayout() {
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.GONE);
        (view.findViewById(R.id.layoutRecyler)).setVisibility(View.GONE);
    }

    private void liberarLayoutRecycler() {
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.GONE);
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.GONE);
        (view.findViewById(R.id.layoutRecyler)).setVisibility(View.VISIBLE);
    }

    private void liberarLayoutNadaEnontrado() {
        ((TextView) view.findViewById(R.id.tituloMensagTextView)).setText(getString(R.string.nao_temos_essa_opcao));
        ((TextView) view.findViewById(R.id.conteudoMensagTextView)).setText(getString(R.string.tente_buscar_outro));
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.GONE);
    }

    @SuppressLint("StaticFieldLeak")
    private class BuscaVideos extends AsyncTask<String, Void, List<Video>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            esconderLayout();

        }

        @Override
        protected List<Video> doInBackground(String... busca) {
            try {
                Log.e("execute: ", busca[0]);
                return YTlist.buscarVideosCanal(new PaginaVideo(), busca[0]).getVideos();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Video> videos) {
            super.onPostExecute(videos);

            if (videos != null && videos.isEmpty()) {
                liberarLayoutNadaEnontrado();
            } else {
                configurarRecylerVideos(videos);
                liberarLayoutRecycler();
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
}


















