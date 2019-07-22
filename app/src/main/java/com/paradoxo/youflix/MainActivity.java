package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.paradoxo.youflix.fragment.MainFragment;
import com.paradoxo.youflix.fragment.MaisFragment;
import com.paradoxo.youflix.fragment.NadaAindaFragment;
import com.paradoxo.youflix.fragment.SearchFragment;
import com.paradoxo.youflix.modelo.Canal;
import com.paradoxo.youflix.util.YTinfo;

public class MainActivity extends AppCompatActivity implements MaisFragment.OnItemListener {

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarTelaCheia();

        if (getPrefString("apiKey").isEmpty() || getPrefString("urlChannel").isEmpty()) {
            MaisFragment fragment = new MaisFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.framentPrincipal, fragment).commit();
        } else {
            iniciarInterface();
        }

    }

    private void iniciarInterface() {
        adicionarFragmentPrincipal();

        configurarBottomViewCustomizado();
    }

    private void configurarBottomViewCustomizado() {
        (findViewById(R.id.homeTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentPrincipal();

            }
        });


        (findViewById(R.id.buscasTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentBuscas();
            }
        });

        (findViewById(R.id.emBreveTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentNadaAinda();
            }
        });

        (findViewById(R.id.downloadsTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentNadaAinda();
            }
        });

        (findViewById(R.id.maisTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentMais();
            }
        });
    }

    private void configurarTelaCheia() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void adicionarFragmentPrincipal() {
        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.framentPrincipal, fragment).commit();

    }

    private void adicionarFragmentBuscas() {
        SearchFragment fragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framentPrincipal, fragment).commit();

    }

    private void adicionarFragmentNadaAinda() {
        NadaAindaFragment fragment = new NadaAindaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framentPrincipal, fragment).commit();

    }

    private void adicionarFragmentMais() {
        MaisFragment fragment = new MaisFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framentPrincipal, fragment).commit();

    }

    private String getPrefString(String nomeShared) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getString(nomeShared, "");
    }

    @Override
    public void onItemLister() {
        iniciarInterface();
    }
}
