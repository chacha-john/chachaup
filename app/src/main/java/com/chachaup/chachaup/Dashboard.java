package com.chachaup.chachaup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.listViewRecipes)
    ListView mRecipesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        welcomeMessage.setText("Hi,"+ email);

        DashboardArrayAdapter adapter = new DashboardArrayAdapter(this, android.R.layout.simple_list_item_1,meals,ingredients,recipes);
        mRecipesList.setAdapter(adapter);

        mRecipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Dashboard.this,"This is a recipe list item!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
