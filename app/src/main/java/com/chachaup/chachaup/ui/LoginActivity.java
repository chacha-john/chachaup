package com.chachaup.chachaup.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chachaup.chachaup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.buttonLogin)
    Button mLoginButton;
    @BindView(R.id.editTextEmailLogin)
    EditText mEmail;
    @BindView(R.id.editTextPasswordLogin)
    EditText mPassword;
    @BindView(R.id.textViewSignUp)
    TextView mSignUp;
    @BindView(R.id.textViewWarning)
    TextView mWarning;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };


        mSignUp.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mSignUp){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            Toast.makeText(LoginActivity.this,"Taking you to sign up...",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

        if (v == mLoginButton){
            loginWithPassword();
        }

    }

    private void loginWithPassword(){
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (email.equals("")){
            mEmail.setError("Please enter your email");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Please enter a valid email address");
            return;
        }
        if (password.equals("")){
            mPassword.setError("Password cannot be blank");
            return;
        }
        if (password.length() < 8){
            mPassword.setError("Your password is too short");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete" + task.isSuccessful());
                        if (!task.isSuccessful()){
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}