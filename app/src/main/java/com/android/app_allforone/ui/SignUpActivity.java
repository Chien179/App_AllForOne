package com.android.app_allforone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.app_allforone.R;
import com.android.app_allforone.fragment.LoginFragment;
import com.android.app_allforone.models.User;
import com.android.app_allforone.utils.Check;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

//    private FirebaseAuth mAuth;
    private EditText editTextFName,
        editTextLName,
        editTextEmail,
        editTextPass,
        editTextConfPass;

    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        addViews();
        addListener();
    }

    private void addViews(){
        editTextFName = (EditText) findViewById(R.id.editTextFname);
        editTextLName = (EditText) findViewById(R.id.editTextLname);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextConfPass = (EditText) findViewById(R.id.editTextRepass);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    private void addListener(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate()
    {
        String fName,lName,email,pass,confPass;
        fName = editTextFName.getText().toString().trim();
        lName = editTextLName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        pass = editTextPass.getText().toString().trim();
        confPass = editTextConfPass.getText().toString().trim();

        if (Check.checkEmpty(fName, editTextFName) &&
                Check.checkEmpty(lName, editTextLName) &&
                Check.checkEmpty(email, editTextEmail) &&
                Check.checkEmpty(lName, editTextLName) &&
                Check.checkEmpty(pass, editTextPass) &&
                Check.checkEmpty(confPass, editTextConfPass) &&
                Check.checkFormat(pass, editTextEmail) &&
                Check.checkConfPass(pass, confPass, editTextConfPass)){
            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = new User(email, fName, lName, 0);
                    firebaseDatabase.getReference("users")
                            .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid()).setValue(user).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful())
                                {
                                    Toast.makeText(SignUpActivity.this,"Register successfully",Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(SignUpActivity.this, LoginFragment.class));
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else {
                    Toast.makeText(SignUpActivity.this,"Failed to register",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}