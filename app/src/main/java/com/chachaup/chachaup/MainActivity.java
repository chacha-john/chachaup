package com.chachaup.chachaup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    @BindView(R.id.textViewSignUp)
    TextView mSignUp;
    @BindView(R.id.textViewWarning)
    TextView mWarning;

//    private boolean isAllFieldsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        isAllFieldsChecked = CheckAllFields();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                Toast.makeText(MainActivity.this,"Taking you to sign up...",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mEmail.getText().toString();
                String userPassword = mPassword.getText().toString();
                Intent intent = getIntent();
                String email = intent.getStringExtra("email");
                String pass = intent.getStringExtra("pass");
                String name = intent.getStringExtra("name");

                if(userEmail.equals(email) && userPassword.equals(pass)){
                    Toast.makeText(MainActivity.this,"Signing in...", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(MainActivity.this, Dashboard.class);
                    intent1.putExtra("name",name);
                    startActivity(intent1);
                } else{
                    mWarning.setText(getString(R.string.warning));
                }

            }
        });
    }
    // validation of fields
//    private boolean CheckAllFields() {
//        if(mEmail.length() == 0){
//            mEmail.setError("This field is required");
//            return false;
//        }
//        if(mPassword.length() == 0){
//            mPassword.setError("This field is required");
//            return false;
//        } else if (mPassword.length() < 8){
//            mPassword.setError("Password must be a minimum of 8 characters");
//            return false;
//        }
//        return true;
//    }
}