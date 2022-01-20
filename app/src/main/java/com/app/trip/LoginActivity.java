package com.app.trip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnSignup;
    FirebaseAuth auth;
    Button btnSignin;
    TextInputLayout tinputEmail, tinputPassword;
    //TextInputEditText edEmail,edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignup = findViewById(R.id.button4);
        btnSignin=findViewById(R.id.button3);
        tinputEmail = findViewById(R.id.emaillayoutSignIn);
        tinputPassword = findViewById(R.id.passLayoutSignIn);
        auth=FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEd();
            }
        });
    }
    private void checkEd() {
        String email=getTxt(tinputEmail);
        String pass=getTxt(tinputPassword);
        if (TextUtils.isEmpty(email)) {
            tinputEmail.setError("Cannot be Empty !!!");
            tinputEmail.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            tinputPassword.setError("Cannot be Empty !!!");
            tinputPassword.requestFocus();

        }else{
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign In Succefully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getTxt(TextInputLayout layout) {
        return layout.getEditText().getText().toString();
    }
}