package com.android.app_allforone.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.app_allforone.R;
import com.android.app_allforone.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    //Use the concept of View Binding using binding class and database reference
    FragmentProfileBinding fragmentProfileBinding;
    //Initialize variables
    TextView textViewAccount,textViewFirstName,textViewLastName, editTextEmail;
    ImageView imageView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Create an object of this binding class
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());

        String email = null, lName = null, fName = null;

        if (getArguments() != null){
            email = getArguments().getString("email");
            fName = getArguments().getString("fName");
            lName = getArguments().getString("lName");
        }

        //Identify necessary items
        textViewAccount = view.findViewById(R.id.textViewAccountInput);
        editTextEmail= view.findViewById(R.id.textViewEmailInput);
        textViewFirstName = view.findViewById(R.id.textViewFirstNameInput);
        textViewLastName = view.findViewById(R.id.textViewLastNameInput);
        imageView = view.findViewById(R.id.imageViewAvatar);

        textViewAccount.setText(fName + " " + lName);
        editTextEmail.setText(email);
        textViewFirstName.setText(fName);
        textViewLastName.setText(lName);

        return view;
    }
}