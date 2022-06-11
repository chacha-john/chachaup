package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chachaup.chachaup.adapters.MealsListAdapter;
import com.chachaup.chachaup.network.MealDBApi;
import com.chachaup.chachaup.network.MealDBClient;
import com.chachaup.chachaup.models.MealSearchResponse;
import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsActivity extends AppCompatActivity {
    public static final String TAG = MealsActivity.class.getSimpleName();
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBarMeals)
    ProgressBar mProgressBar;
    private MealsListAdapter mAdapter;

    public List<Meal> mealsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String meal = intent.getStringExtra("meal");

        MealDBApi client = MealDBClient.getClient();
        Call<MealSearchResponse> call = client.getMeals(meal);
        call.enqueue(new Callback<MealSearchResponse>() {
            @Override
            public void onResponse(Call<MealSearchResponse> call, Response<MealSearchResponse> response) {
                hideProgressBar();
                if(response.isSuccessful()){
                    mealsList = response.body().getMeals();
                    mAdapter = new MealsListAdapter(MealsActivity.this, mealsList);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MealsActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

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
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }


}
