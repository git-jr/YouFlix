package com.paradoxo.youflix.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.paradoxo.youflix.R;

public class EmBreveFragment extends Fragment {

    private View view;

    public EmBreveFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nada_ainda, container, false);
        return view;
    }

}


















