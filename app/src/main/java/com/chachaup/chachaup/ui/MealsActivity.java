package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.MealDBApi;
import com.chachaup.chachaup.MealDBClient;
import com.chachaup.chachaup.MealSearchResponse;
import com.chachaup.chachaup.R;
import com.chachaup.chachaup.adapters.DashboardArrayAdapter;
import com.chachaup.chachaup.models.Meal;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsActivity extends AppCompatActivity {

    @BindView(R.id.listViewMealRecipes)
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");

        MealDBApi client = MealDBClient.getClient();
        Call<MealSearchResponse> call = client.getMeals(meal);
        call.enqueue(new Callback<MealSearchResponse>() {
            @Override
            public void onResponse(Call<MealSearchResponse> call, Response<MealSearchResponse> response) {
                if(response.isSuccessful()){
                    List<Meal> mealsList = response.body().getMeals();
                    String[] categories = new String[mealsList.size()];
                    String[] meals = new String[mealsList.size()];
                    String[] ingredients = new String[mealsList.size()];
                    String[] recipes = new String[mealsList.size()];

                    for (int i = 0; i < meals.length; i++){
                        meals[i] = mealsList.get(i).getStrMeal();
                    }
                    for (int i = 0; i < categories.length; i++){
                        categories[i] = mealsList.get(i).getStrCategory();
                    }

                    for (int i = 0; i < recipes.length; i++){
                        recipes[i] = mealsList.get(i).getStrInstructions();
                    }

                    ArrayAdapter adapter = new DashboardArrayAdapter(MealsActivity.this, android.R.layout.simple_list_item_1,meals,recipes);
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MealSearchResponse> call, Throwable t) {

            }
        });
    }


}
