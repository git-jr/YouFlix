package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.paradoxo.youflix.fragment.MainFragment;
import com.paradoxo.youflix.fragment.NadaAindaFragment;
import com.paradoxo.youflix.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarTelaCheia();
        adicionarFragmentPrincipal();
        configurarBottomViewCustomizado();

        startActivity(new Intent(this, ExibirVideoActivity.class));
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

        (findViewById(R.id.minhListaTextView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarFragmentNadaAinda();
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

}
