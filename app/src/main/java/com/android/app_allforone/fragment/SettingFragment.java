package com.android.app_allforone.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.app_allforone.R;
import com.android.app_allforone.databinding.FragmentProfileBinding;
import com.android.app_allforone.databinding.FragmentSettingBinding;
import com.android.app_allforone.ui.addNewMovie;

public class SettingFragment extends Fragment {

    String role;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView textView;
        textView = view.findViewById(R.id.textView14);

       textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getContext(), addNewMovie.class);
               startActivity(intent);
           }
       });

        return inflater.inflate(R.layout.fragment_setting, container, false);

    }
}