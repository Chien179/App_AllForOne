package com.android.app_allforone.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.app_allforone.R;
import com.android.app_allforone.databinding.ActivityMainBinding;
import com.android.app_allforone.fragment.AddNewMovieFragment;
import com.android.app_allforone.fragment.HomePageFragment;
import com.android.app_allforone.fragment.LoginFragment;
import com.android.app_allforone.fragment.ProfileFragment;
import com.android.app_allforone.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Check condition if there is an account logging in
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomePageFragment());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomePageFragment());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.profile:
                    Fragment fragment;

                    //if there is already account logging in, pass data to the profile fragment
                    if (getIntent().getExtras() != null){
                        String email = getIntent().getExtras().getString("email");
                        String fName = getIntent().getExtras().getString("fName");
                        String fLame = getIntent().getExtras().getString("lName");
                        int role = getIntent().getExtras().getInt("role");

                        System.out.println(email);

                        if(role != 1){
                            ProfileFragment profileFragment = new ProfileFragment();

                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            bundle.putString("fName", fName);
                            bundle.putString("lName", fLame);

                            profileFragment.setArguments(bundle);

                            fragment = profileFragment;
                        }else {
                            fragment = new AddNewMovieFragment();
                        }
                    }else {
                        //initialize bundle and put value then pass argument
                        fragment = new LoginFragment();
                    }

                    replaceFragment(fragment);
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}