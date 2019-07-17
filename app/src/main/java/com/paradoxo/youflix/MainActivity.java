package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.paradoxo.youflix.adapter.AdapterTeste;
import com.paradoxo.youflix.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adicionarFragmentPrincipal();

        //configurarRecycler();
        //initToolbar();

    }

    private void adicionarFragmentPrincipal() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        MainFragment mainFragment = new MainFragment();
//        SearchFragment mainFragment = new SearchFragment();
        fragmentTransaction.add(R.id.framentPrincipal,mainFragment);
        fragmentTransaction.commit();
    }

    private void configurarRecycler() {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i != 10; i++) {
            lista.add("Item" + i);
        }

        RecyclerView recyclerView = findViewById(R.id.minhaListaRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        AdapterTeste adapterTeste = new AdapterTeste(lista, this);
        recyclerView.setAdapter(adapterTeste);
        recyclerView.smoothScrollToPosition(8);

        RecyclerView recyclerView1 = findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(adapterTeste);
        recyclerView1.smoothScrollToPosition(8);

        RecyclerView recyclerView2 = findViewById(R.id.recycler2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(adapterTeste);
        recyclerView2.smoothScrollToPosition(8);

    }


}
