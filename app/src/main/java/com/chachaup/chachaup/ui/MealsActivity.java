package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.network.MealDBApi;
import com.chachaup.chachaup.network.MealDBClient;
import com.chachaup.chachaup.models.MealSearchResponse;
import com.chachaup.chachaup.R;
import com.chachaup.chachaup.adapters.DashboardArrayAdapter;
import com.chachaup.chachaup.models.Meal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsActivity extends AppCompatActivity {
    public static final String TAG = MealsActivity.class.getSimpleName();
    @BindView(R.id.mealTextView)
    TextView mMealTextView;
    @BindView(R.id.listViewMealRecipes)
    ListView mListView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBarMeals)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");
        mMealTextView.setText("Here is the recipe for making, " + meal);

        MealDBApi client = MealDBClient.getClient();
        Call<MealSearchResponse> call = client.getMeals(meal);
        call.enqueue(new Callback<MealSearchResponse>() {
            @Override
            public void onResponse(Call<MealSearchResponse> call, Response<MealSearchResponse> response) {
                hideProgressBar();
                if(response.isSuccessful()){
                    List<Meal> mealsList = response.body().getMeals();
//                    String[] categories = new String[mealsList.size()];
                    String[] meals = new String[mealsList.size()];
                    String[] recipes = new String[mealsList.size()];

                    for (int i = 0; i < meals.length; i++){
                        meals[i] = mealsList.get(i).getStrMeal();
                    }
//                    for (int i = 0; i < categories.length; i++){
//                        categories[i] = mealsList.get(i).getStrCategory();
//                    }

                    for (int i = 0; i < recipes.length; i++){
                        recipes[i] = mealsList.get(i).getStrInstructions();
                    }

                    ArrayAdapter adapter = new DashboardArrayAdapter(MealsActivity.this, android.R.layout.simple_list_item_1,meals,recipes);
                    mListView.setAdapter(adapter);
                    showRecipe();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<MealSearchResponse> call, Throwable t) {
                Log.e(TAG,"Error",t);
                hideProgressBar();
                showFailureMessage();

            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRecipe() {
        mListView.setVisibility(View.VISIBLE);
        mMealTextView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
