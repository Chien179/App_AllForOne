package com.android.app_allforone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.app_allforone.R;
import com.android.app_allforone.models.User;
import com.android.app_allforone.ui.MainActivity;
import com.android.app_allforone.ui.SignUpActivity;
import com.android.app_allforone.utils.Check;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private Button btnLogin;
    private TextView txtSignUp;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    public LoginFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        addVews(view);
        addListener();

        return view;
    }

    private void addVews(View view){
        btnLogin = view.findViewById(R.id.btnLogin);
        txtSignUp = view.findViewById(R.id.txtsignUp);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
    }

    private void addListener(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login()
    {
        String email = Objects.requireNonNull(etEmail.getText()).toString();
        String pass = Objects.requireNonNull(etPassword.getText()).toString();

        if (Check.checkEmpty(email, etEmail) &&
                Check.checkEmpty(pass, etPassword) &&
                Check.checkFormat(email, etEmail)){
            firebaseAuth.signInWithEmailAndPassword(email, pass).
                    addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Login success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getContext(), MainActivity.class);

                            DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User user = snapshot.getValue(User.class);

                                    assert user != null;
                                    intent.putExtra("email", user.getEmail());
                                    intent.putExtra("fName", user.getFirstName());
                                    intent.putExtra("lName", user.getLastName());
                                    intent.putExtra("role", user.getRole());

                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else {
                            Toast.makeText(getContext(),"Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
