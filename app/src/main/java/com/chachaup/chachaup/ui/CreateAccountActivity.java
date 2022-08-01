package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    public String mNameString;

    @BindView(R.id.textViewLogin)
    TextView mSignInTextView;
    @BindView(R.id.editTextPersonName)
    EditText mName;
    @BindView(R.id.editTextEmailSignUp)
    EditText mEmail;
    @BindView(R.id.editTextEnterPhoneSignUp)
    EditText mPhone;
    @BindView(R.id.editTextCreatePassword)
    EditText mPassword;
    @BindView(R.id.editTextRepeatPassword)
    EditText mPasswordRepeat;
    @BindView(R.id.buttonSignUp)
    Button mSignUp;
    @BindView(R.id.textViewLoading) TextView mLoadingTextView;
    @BindView(R.id.progressBarCreateAccount)
    ProgressBar mLoadingProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        createAuthStateListener();

        mSignUp.setOnClickListener(this);
        mSignInTextView.setOnClickListener(this);

    }
    private void showProgressBar(){
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mLoadingTextView.setText("The sign up process is in progress");
        mLoadingTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mLoadingTextView.setVisibility(View.GONE);
        mLoadingProgressBar.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        if (v == mSignUp){
            createNewAccount();
        }

        if (v == mSignInTextView){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            Toast.makeText(CreateAccountActivity.this,"Taking you to sign in...",Toast.LENGTH_SHORT).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void createNewAccount(){
        mNameString = mName.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String email = mEmail.getText().toString().toLowerCase().trim();
        final String password = mPassword.getText().toString().trim();
        final String confirmPassword = mPasswordRepeat.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validPassword = isValidPassword(password, confirmPassword);
        boolean validName = isValidName(mNameString);
        boolean validPhone = isValidPhone(phone);

        if (!validEmail || !validName || !validPassword || !validPhone) return;

        showProgressBar();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressBar();

                        if (task.isSuccessful()){
                            Log.d(TAG, "Authentication successful");
                            createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(),"Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null){
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
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

    private boolean isValidEmail(String email){
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail){
            mEmail.setError("Please enter a valid email address");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name){
        if (name.equals("")){
            mName.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword){
        if (password.length() < 8 ){
            mPassword.setError("Your password should contain at least 8 characters");
            return false;
        } else if (!password.equals(confirmPassword)){
            mPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private boolean isValidPhone(String phone){
        if (!(phone != null && Patterns.PHONE.matcher(phone).matches())){
            mPhone.setError("Enter a valid phone");
            return false;
        }
        return true;
    }
    private void createFirebaseUserProfile(final FirebaseUser user){
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(mNameString)
                .build();

        user.updateProfile(changeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                            Toast.makeText(getApplicationContext(),"The dispaly name has been set",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
