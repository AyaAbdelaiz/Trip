package com.app.trip;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    Button btnSignup;
    FirebaseAuth auth;
    Button btnSignin;
    TextInputLayout tinputEmail, tinputPassword;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 100;

    //TextInputEditText edEmail,edPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = findViewById(R.id.button4);
        btnSignin = findViewById(R.id.button3);
        tinputEmail = findViewById(R.id.emaillayoutSignIn);
        tinputPassword = findViewById(R.id.passLayoutSignIn);
        signInButton =findViewById(R.id.googleSignIn);
        auth = FirebaseAuth.getInstance();

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
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);

    }


    private void checkEd() {
        String email = getTxt(tinputEmail);
        String pass = getTxt(tinputPassword);
        if (TextUtils.isEmpty(email)) {
            tinputEmail.setError("Cannot be Empty !!!");
            tinputEmail.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            tinputPassword.setError("Cannot be Empty !!!");
            tinputPassword.requestFocus();

        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Sign In Succefully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private String getTxt(TextInputLayout layout) {
        return layout.getEditText().getText().toString();
    }

    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInAccountInfo(task);
        }
    }



   private void signInAccountInfo(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);

            // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(requireContext());
            String userName = account.getDisplayName();
            String email = account.getEmail();

            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        } catch (Exception exception) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}