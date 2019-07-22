package com.paradoxo.youflix.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import com.paradoxo.youflix.R;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.util.YTinfo;

public class MaisFragment extends Fragment {


    private View view;
    private OnItemListener listener;

    public MaisFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mais, container, false);

        configurarBotaoEntrar();

        return view;
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
        } else {
            throw new ClassCastException();
        }
    }

    private void verificarDados() {

        String apiKey = ((AppCompatEditText) view.findViewById(R.id.apiKeyEditText)).getText().toString();
        String urlChannel = ((AppCompatEditText) view.findViewById(R.id.urlCanalEditText)).getText().toString();

        String caminhoBase = "https://www.youtube.com/user/";
        if (apiKey.isEmpty() || urlChannel.isEmpty()) {
            notificarErroChave();
            notificarErroUrl();

        } else if (!Patterns.WEB_URL.matcher(urlChannel).matches() || !urlChannel.contains(caminhoBase)) {
            notificarErroUrl();
        } else {
            VerificaDados verificaDados = new VerificaDados(apiKey, urlChannel.replace(caminhoBase, ""));
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

            YTinfo yTinfo = new YTinfo();
            Canal canal = yTinfo.verificarCanal(apiKey, urlCanal);

            if (canal.getId() == null) {
                Log.e("Id Channel", "Não localizado");
            } else {
                Log.e("Id Channel", canal.getId());
                setPrefString(apiKey, "apiKey");
                setPrefString(canal.getId(), "idChannel");
                setPrefString(urlCanal, "urlChannel");
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

}
