package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.paradoxo.youflix.adapter.AdapterTeste;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurarRecycler();
        initToolbar();


    }

    private void configurarRecycler() {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i != 50; i++) {
            lista.add("Item" + i);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler);
        AdapterTeste adapterTeste = new AdapterTeste(lista);
        recyclerView.setAdapter(adapterTeste);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
