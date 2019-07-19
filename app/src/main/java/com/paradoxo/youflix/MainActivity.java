package com.paradoxo.youflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.paradoxo.youflix.adapter.AdapterVideo;
import com.paradoxo.youflix.fragment.MainFragment;
import com.paradoxo.youflix.fragment.SearchFragment;

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

//        MainFragment mainFragment = new MainFragment();
        SearchFragment mainFragment = new SearchFragment();
        fragmentTransaction.add(R.id.framentPrincipal, mainFragment);
        fragmentTransaction.commit();
    }


}
