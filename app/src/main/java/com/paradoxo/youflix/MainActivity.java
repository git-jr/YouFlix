package com.paradoxo.youflix;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.paradoxo.youflix.enums.AbasEnum;
import com.paradoxo.youflix.enums.GuiasEnum;
import com.paradoxo.youflix.fragment.DownloadFragment;
import com.paradoxo.youflix.fragment.MainFragment;
import com.paradoxo.youflix.fragment.MaisFragment;
import com.paradoxo.youflix.fragment.EmBreveFragment;
import com.paradoxo.youflix.fragment.BuscaFragment;

import static com.paradoxo.youflix.enums.GuiasEnum.GUIA_BUSCA;
import static com.paradoxo.youflix.enums.GuiasEnum.GUIA_DOWNLOAD;
import static com.paradoxo.youflix.enums.GuiasEnum.GUIA_EM_BREVE;
import static com.paradoxo.youflix.enums.GuiasEnum.GUIA_HOME;
import static com.paradoxo.youflix.enums.GuiasEnum.GUIA_MAIS;

public class MainActivity extends AppCompatActivity implements MaisFragment.OnItemListener, MainFragment.OnItemListenerMain {

    TextView homeTextView, buscasTextView, emBreveTextView, downloadsTextView, maisTextView;
    private Fragment mainFragment, buscaFragment, emBreveFragment, downloadsFragment, maisFragment;
    private Fragment fragmentAtual = null;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        buscaFragment = new BuscaFragment();
        emBreveFragment = new EmBreveFragment();
        downloadsFragment = new DownloadFragment();
        maisFragment = new MaisFragment();

        inicializarBottomViewCustomizado();

        if (getPrefString("apiKey").isEmpty() || getPrefString("urlChannel").isEmpty()) {
            adicionarFragment(new MaisFragment(), " ");
            colorirBottomView(GUIA_MAIS);
        } else {
            iniciarInterface();
        }

    }

    private void inicializarBottomViewCustomizado() {
        homeTextView = findViewById(R.id.homeTextView);
        buscasTextView = findViewById(R.id.buscasTextView);
        emBreveTextView = findViewById(R.id.emBreveTextView);
        downloadsTextView = findViewById(R.id.downloadsTextView);
        maisTextView = findViewById(R.id.maisTextView);
    }

    private void iniciarInterface() {
        configurarBottomViewCustomizado();
        adicionarFragment(mainFragment, "MainFragment");
        configurarTelaCheia();
    }

    private void configurarBottomViewCustomizado() {

        homeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragment(mainFragment, "MainFragment");
                colorirBottomView(GUIA_HOME);
            }
        });


        buscasTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragment(buscaFragment, "BuscaFragment");
                colorirBottomView(GUIA_BUSCA);
            }
        });

        emBreveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragment(emBreveFragment, "EmBreveFragment");
                colorirBottomView(GUIA_EM_BREVE);
            }
        });

        downloadsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragment(downloadsFragment, "DownloadFragment");
                colorirBottomView(GUIA_DOWNLOAD);

            }
        });

        maisTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragment(maisFragment, "MaisFragment");
                colorirBottomView(GUIA_MAIS);

            }
        });

        colorirBottomView(GUIA_HOME);
    }

    private void configurarTelaCheia() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void adicionarFragment(Fragment fragment, String tagFragmento) {
        if (getSupportFragmentManager().getFragments().contains(fragment)) {
            Log.e("frag", "Já existe");

            if (fragmentAtual != null) {
                getSupportFragmentManager().beginTransaction().hide(fragmentAtual).commit();
            }
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        } else {
            Log.e("frag", "Ainda não existe");
            getSupportFragmentManager().beginTransaction().add(R.id.framentPrincipal, fragment, tagFragmento).addToBackStack(null).commit();
        }

        fragmentAtual = fragment;
    }

    private void colorirBottomView(GuiasEnum novaGuia) {
        if (novaGuia == GUIA_HOME) {
            homeTextView.setTextColor(Color.WHITE);
            homeTextView.getCompoundDrawables()[1].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        } else {
            homeTextView.setTextColor(getResources().getColor(R.color.cinza_7B));
            homeTextView.getCompoundDrawables()[1].setColorFilter(getResources().getColor(R.color.cinza_7B), PorterDuff.Mode.SRC_IN);
        }

        if (novaGuia == GUIA_BUSCA) {
            buscasTextView.setTextColor(Color.WHITE);
            buscasTextView.getCompoundDrawables()[1].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        } else {
            buscasTextView.setTextColor(getResources().getColor(R.color.cinza_7B));
            buscasTextView.getCompoundDrawables()[1].setColorFilter(getResources().getColor(R.color.cinza_7B), PorterDuff.Mode.SRC_IN);
        }

        if (novaGuia == GUIA_EM_BREVE) {
            emBreveTextView.setTextColor(Color.WHITE);
            emBreveTextView.getCompoundDrawables()[1].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        } else {
            emBreveTextView.setTextColor(getResources().getColor(R.color.cinza_7B));
            emBreveTextView.getCompoundDrawables()[1].setColorFilter(getResources().getColor(R.color.cinza_7B), PorterDuff.Mode.SRC_IN);
        }

        if (novaGuia == GUIA_DOWNLOAD) {
            downloadsTextView.setTextColor(Color.WHITE);
            downloadsTextView.getCompoundDrawables()[1].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        } else {
            downloadsTextView.setTextColor(getResources().getColor(R.color.cinza_7B));
            downloadsTextView.getCompoundDrawables()[1].setColorFilter(getResources().getColor(R.color.cinza_7B), PorterDuff.Mode.SRC_IN);
        }

        if (novaGuia == GUIA_MAIS) {
            maisTextView.setTextColor(Color.WHITE);
            maisTextView.getCompoundDrawables()[1].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        } else {
            maisTextView.setTextColor(getResources().getColor(R.color.cinza_7B));
            maisTextView.getCompoundDrawables()[1].setColorFilter(getResources().getColor(R.color.cinza_7B), PorterDuff.Mode.SRC_IN);

        }
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
                MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");
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
