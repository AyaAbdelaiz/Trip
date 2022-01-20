package com.app.trip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseAuth auth;
    TextInputLayout username, email, password, confirmPass;
    Button signUp;
    boolean isError = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);

        username = findViewById(R.id.userLayout);
        email = findViewById(R.id.emailLayout);
        password = findViewById(R.id.etPasswordLayout);
        confirmPass = findViewById(R.id.confirmpassword);
        signUp = findViewById(R.id.signUpBtn);


        auth = FirebaseAuth.getInstance();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });


    }

    private void createUser() {
        String usernameTxt = getTxt(username);
        String emailTxt = getTxt(email);
        String passTxt = getTxt(password);
        String confirmpassTxt = getTxt(confirmPass);

        if (TextUtils.isEmpty(usernameTxt)) {
            username.setError("Cannot be Empty !!!");
            username.requestFocus();


        } else if (TextUtils.isEmpty(emailTxt)) {
            email.setError("Cannot be Empty !!!");
            email.requestFocus();


        } else if (TextUtils.isEmpty(passTxt)) {
            password.setError("Cannot be Empty !!!");
            password.requestFocus();


        } else if (TextUtils.isEmpty(confirmpassTxt)) {

            confirmPass.setError("Cannot be Empty !!!");
            confirmPass.requestFocus();

        } else if (!TextUtils.isEmpty(confirmpassTxt)) {
            if (confirmpassTxt.equals(passTxt)) {
                confirmPass.setError(null);
                auth.createUserWithEmailAndPassword(emailTxt, passTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Sign Up Succefully", Toast.LENGTH_SHORT).show();
                        } else {
                          //  Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("aya", "onComplete: "+task.getException().getMessage());
                        }
                    }
                });

            } else {
                confirmPass.setError("Password not match !!!");
                confirmPass.requestFocus();
            }

        }

    }

    private String getTxt(TextInputLayout layout) {
        return layout.getEditText().getText().toString();
    }


}