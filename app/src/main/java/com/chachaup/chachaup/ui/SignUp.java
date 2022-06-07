package com.chachaup.chachaup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUp extends AppCompatActivity {
    @BindView(R.id.textViewLogin)
    TextView mSignIn;
    @BindView(R.id.editTextPersonName)
    EditText mName;
    @BindView(R.id.editTextEmailSignUp)
    EditText mEmail;
    @BindView(R.id.editTextCreatePassword)
    EditText mPassword;
    @BindView(R.id.buttonSignUp)
    Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        ButterKnife.bind(this);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                Toast.makeText(SignUp.this,"Account created successfully...",Toast.LENGTH_LONG).show();
                intent.putExtra("email",email);
                intent.putExtra("pass",password);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                Toast.makeText(SignUp.this,"Taking you to sign in...",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }

}
