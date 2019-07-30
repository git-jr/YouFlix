package com.paradoxo.youflix.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.paradoxo.youflix.R;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.util.YTinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MaisFragment extends Fragment {

    private View view;
    private OnItemListener listener;

    public MaisFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mais, container, false);
        configurarBotaoEntrar();
        configurarBotaoAjuda();

        return view;
    }

    private void configurarBotaoAjuda() {
        view.findViewById(R.id.ajudaTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGitHub = new Intent(Intent.ACTION_VIEW);
                intentGitHub.setData(Uri.parse("https://github.com/git-jr/YouFlix"));
                startActivity(intentGitHub);
            }
        });
    }

    private void configurarBotaoEntrar() {
        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDados();
            }
        });
    }

    public interface OnItemListener {
        void onItemListerStartInterface();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemListener) {
            listener = (OnItemListener) context;
        }
    }

    private void verificarDados() {
        String apiKey = Objects.requireNonNull(((AppCompatEditText) view.findViewById(R.id.apiKeyEditText)).getText()).toString();
        String urlChannel = Objects.requireNonNull(((AppCompatEditText) view.findViewById(R.id.urlCanalEditText)).getText()).toString();

        String caminhoBase = "https://www.youtube.com/";

        if (apiKey.isEmpty() || urlChannel.isEmpty()) {
            notificarErroChave();
            notificarErroUrl();

        } else if (!Patterns.WEB_URL.matcher(urlChannel).matches() || !urlChannel.contains(caminhoBase)) {
            notificarErroUrl();

        } else {
            VerificaDados verificaDados = new VerificaDados(apiKey, String.valueOf(urlChannel.split("/")[urlChannel.split("/").length - 1]));
            verificaDados.execute();
        }

    }

    private void notificarErroChave() {
        view.findViewById(R.id.apiKeyTextInputLayout).setBackgroundResource(R.drawable.bg_edittext_login_erro);
        view.findViewById(R.id.keyInvalidaTextView).setVisibility(View.VISIBLE);
    }

    private void notificarErroUrl() {
        view.findViewById(R.id.urlCanalTextInputLayout).setBackgroundResource(R.drawable.bg_edittext_login_erro);
        view.findViewById(R.id.urlInvalidoTextView).setVisibility(View.VISIBLE);
    }

    private void setPrefString(String texto, String nomeShared) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putString(nomeShared, texto);
        mEditor.apply();
    }

    private void entrar() {
        liberarLayout();
        listener.onItemListerStartInterface();
    }

    private void liberarLayout() {
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.GONE);
    }

    private void esconderLayout() {
        (view.findViewById(R.id.layoutLoad)).setVisibility(View.VISIBLE);
        (view.findViewById(R.id.layoutPrincipal)).setVisibility(View.GONE);
    }

    @SuppressLint("StaticFieldLeak")
    public class VerificaDados extends AsyncTask<Void, Void, Boolean> {
        private String apiKey, urlCanal;

        VerificaDados(String apiKey, String urlCanal) {
            this.apiKey = apiKey;
            this.urlCanal = urlCanal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            esconderLayout();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Canal canal = new YTinfo(getContext()).verificarCanal(apiKey, urlCanal);

            if (canal.getId() != null) {
                setPrefString(apiKey, "apiKey");
                setPrefString(canal.getId(), "idChannel");
                setPrefString(urlCanal, "urlChannel");
                setPrefString(canal.getIdChannelUpload(), "channelUpload");
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean passou) {
            super.onPostExecute(passou);
            if (!passou) {
                notificarErroChave();
                notificarErroUrl();
                liberarLayout();
            } else {
                entrar();
            }
        }
    }

}
