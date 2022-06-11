package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends AppCompatActivity {
    @BindView(R.id.textViewWelcomeMessageDashboard)
    TextView welcomeMessage;
    @BindView(R.id.listViewRecipes)
    ListView mRecipesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        welcomeMessage.setText("Hi,"+ name);

        mRecipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Dashboard.this,"This is a recipe list item!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
