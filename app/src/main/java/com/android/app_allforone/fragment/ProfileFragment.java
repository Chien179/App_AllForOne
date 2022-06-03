package com.android.app_allforone.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.app_allforone.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment
{
    //Use the concept of View Binding using binding class and database reference
    FragmentProfileBinding fragmentProfileBinding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
    //Initialize variables
    TextView textViewUsername, textViewAccount,textViewFirstName,textViewLastName,textViewPassword;
    EditText editTextUsername;
    ImageView imageView;
    Button btnEditProfile;
    String userName, email;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        //Initialize view
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        //Create an object of this binding class
//        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
//
//        //Identify necessary items
//        textViewAccount = view.findViewById(R.id.textViewAccountInput);
//        editTextUsername = view.findViewById(R.id.editTextUsername);
//        textViewFirstName = view.findViewById(R.id.textViewFirstNameInput);
//        textViewLastName = view.findViewById(R.id.textViewLastNameInput);
//        textViewPassword = view.findViewById(R.id.textViewPasswordInput);
//        btnEditProfile = view.findViewById(R.id.btn_account_edit_username);
//        imageView = view.findViewById(R.id.imageViewAvatar);
//
//        //Check condition if there is an account logging in and retrieve data
//        if (getArguments() != null)
//        {
//            email = getArguments().getString("myAccount");
//            textViewAccount.setText(email);
//            getUserByEmail(email);
//            btnEditProfile.setOnClickListener(view1 -> {
//                userName = editTextUsername.getText().toString();
////                databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("userName").setValue(userName);
//                Toast.makeText(getActivity(),"Update Username successfully",Toast.LENGTH_SHORT).show();
//                editTextUsername.setText(userName);
//            });
//        } else {
//            btnEditProfile.setOnClickListener(view12 -> {
//                userName = editTextUsername.getText().toString();
////                databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("userName").setValue(userName);
//                Toast.makeText(getActivity(),"Update Username successfully",Toast.LENGTH_SHORT).show();
//                editTextUsername.setText(userName);
//            });
//        }
//
//        //Handle edit profile button click event
//
//        return view;
//    }

    //Get the document of the Users node with email parameter
//    public void getUserByEmail(String email)
//    {
//        //Get reference for the Users node
//        databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot)
//            {
//                //Performing task with the retrieving data
//                User user = snapshot.getValue(User.class);
//                assert user != null;
//                Toast.makeText(getActivity(),"Welcome back "+ user.getUserName(),Toast.LENGTH_SHORT).show();
//                //Fetch data to the layout
//                fetchProfileData(user.getUserName(),user.getFirstName(),user.getLastName(),user.getPassword(), user.getImage());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error)
//            {
//                Toast.makeText(getActivity(),"Failed to get user data",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void fetchProfileData(String userName, String firstName, String lastName, String password, String image)
    {
        editTextUsername.setText(userName);
        textViewFirstName.setText(firstName);
        textViewLastName.setText(lastName);
        textViewPassword.setText(password);
        imageView.setImageURI(Uri.parse(image));

        Glide.with(getActivity())
                .load(Uri.parse(image))
                .into(imageView);
    }

//    public void isUsernameChanged()
//    {
//        databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("userName").setValue(textViewUsername.getText().toString());
//    }




}