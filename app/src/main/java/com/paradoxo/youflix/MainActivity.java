package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paradoxo.youflix.enums.AbasEnum;
import com.paradoxo.youflix.fragment.MainFragment;
import com.paradoxo.youflix.fragment.MaisFragment;
import com.paradoxo.youflix.fragment.NadaAindaFragment;
import com.paradoxo.youflix.fragment.SearchFragment;
import com.paradoxo.youflix.modelo.PaginaVideo;
import com.paradoxo.youflix.modelo.Video;
import com.paradoxo.youflix.util.YTlist;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MaisFragment.OnItemListener, MainFragment.OnItemListenerMain {

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getPrefString("apiKey").isEmpty() || getPrefString("urlChannel").isEmpty()) {
            MaisFragment fragment = new MaisFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.framentPrincipal, fragment).commit();
        } else {
            iniciarInterface();
        }
    }

    private void iniciarInterface() {
        configurarTelaCheia();
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
        getSupportFragmentManager().beginTransaction().add(R.id.framentPrincipal, fragment, "MainFrag").commit();

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
    public void onItemListerStartInterface() {
        iniciarInterface();
    }

    @Override
    public void onItemListerEscolherAba(AbasEnum abaAtual) {
        escolherAba(abaAtual);
    }


    private void escolherAba(AbasEnum abaAtual) {
        final LinearLayout layoutEscolherAbar = findViewById(R.id.layoutEscolherAbar);
        layoutEscolherAbar.setVisibility(View.VISIBLE);

        configurarListenerEscolhaAbas(layoutEscolherAbar);

        switch (abaAtual) {
            case ABA_VIDEO: {
                ((TextView) findViewById(R.id.opcaoVideosTextView)).setTypeface(null, Typeface.BOLD);
                break;
            }
            case NENHUMA_ABA: {
                ((TextView) findViewById(R.id.opcaoTudoTextView)).setTypeface(null, Typeface.BOLD);
                break;
            }
        }
    }

    private void configurarListenerEscolhaAbas(final LinearLayout layoutEscolherAbar) {
        TextView tudoTextView = findViewById(R.id.opcaoTudoTextView);
        TextView videosTextView = findViewById(R.id.opcaoVideosTextView);

        tudoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEscolherAbar.setVisibility(View.GONE);
                MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("MainFrag");
                if (fragment != null) {
                    fragment.restaurarTextoVideo();
                }
            }
        });

        videosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEscolherAbar.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.fecharEscolherAbaImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEscolherAbar.setVisibility(View.GONE);
            }
        });
    }
}
