package com.android.app_allforone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.app_allforone.R;
import com.android.app_allforone.ui.addNewMovie;

public class SettingFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView textView;
        textView = view.findViewById(R.id.textView14);

       textView.setOnClickListener(view1 -> {
           Intent intent = new Intent(getContext(), addNewMovie.class);
           startActivity(intent);
       });

        return inflater.inflate(R.layout.fragment_setting, container, false);

    }
}