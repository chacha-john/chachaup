package com.chachaup.chachaup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.buttonLogin)
    Button mLoginButton;
    @BindView(R.id.editTextEmailLogin)
    EditText mEmail;
    @BindView(R.id.editTextPasswordLogin)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Signing in...", Toast.LENGTH_LONG).show();

            }
        });
    }
}