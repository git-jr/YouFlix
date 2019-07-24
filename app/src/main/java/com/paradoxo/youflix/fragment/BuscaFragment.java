package com.paradoxo.youflix.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.paradoxo.youflix.ExibirVideoActivity;
import com.paradoxo.youflix.R;
import com.paradoxo.youflix.adapter.AdapterVideo;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTinfo;

import java.io.IOException;
import java.util.List;

public class BuscaFragment extends Fragment {

    private View view;
    private Toolbar toolbar;
    private EditText buscaEditText;


    public BuscaFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_busca, container, false);

        configurarToolbar();

        configurarEditTextBusca();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("SEARC", "sim");
        } else {
            Log.e("SEARC", "NÂO");
        }
    }

    private void configurarEditTextBusca() {
        buscaEditText = view.findViewById(R.id.buscaEditText);
        buscaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getText().toString().isEmpty()) {
                    return true;
                } else {
                    new BuscaVideos().execute(v.getText().toString());
                    Log.e("teclado", "execute");
                    return false;
                }
            }
        });

        buscaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().replace(" ", "").isEmpty()) {
                    toolbar.getMenu().getItem(0).setIcon(R.drawable.ic_mic);
                    toolbar.getMenu().getItem(0).setTitle(getString(R.string.microfone));
                } else {
                    toolbar.getMenu().getItem(0).setIcon(R.drawable.ic_close);
                    toolbar.getMenu().getItem(0).setTitle(getString(R.string.fechar));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void configurarToolbar() {
        toolbar = view.findViewById(R.id.buscaToolbar);
        toolbar.inflateMenu(R.menu.menu_search);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mic && item.getTitle().toString().equals(getString(R.string.fechar))) {
                    buscaEditText.getText().clear();
                }
                return false;
            }
        });
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
                return new YTinfo(getContext()).buscarVideosCanal(new PaginaVideo(), busca[0]).getVideos();

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


















