package com.chachaup.chachaup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends AppCompatActivity {
    private String[] meals = new String[] {"Matoke","Tea","Coffee"};
    private String[] ingredients = new String[] {"bananas, tomato, onions, garlic, ginger, tallow",
            "green tea, water","coffee, water"};
    private String[] recipes = new String[] {"Chop your spices into a dry bowl, add tallow to the mixture and allow it to cook for five minutes in medium heat, add the peeled bananas and 1 litre of water and simmer for 25 minutes. Your delicious meal is ready to be served.","Boil the water until it is hot enough to drink, add green tea and allow it to boil for a few more minutes.","Boil the water until it is hot enough to drink, add coffee and allow it to boil for a few more minutes."};
    @BindView(R.id.textViewWelcomeMessageDashboard)
    TextView welcomeMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        welcomeMessage.setText("Hi,"+ email);

    }
}
