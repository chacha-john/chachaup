package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.Constants;
import com.chachaup.chachaup.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealSearchActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.buttonFindMeal)
    Button mFindMeal;
    @BindView(R.id.editTextMealName)
    EditText mMealName;
    @BindView(R.id.buttonSavedMeals) Button mSavedMeals;

    private DatabaseReference mSearchedMeal;
    private ValueEventListener mSearchedMealListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSearchedMeal = FirebaseDatabase
                .getInstance()
                        .getReference()
                                .child(Constants.FIREBASE_CHILD_SEARCHED_MEAL);

        mSearchedMealListener = mSearchedMeal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot: snapshot.getChildren()){
                    String meal = shot.getValue().toString();
                    Log.d("Meal updated", "Meal: " + meal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal);
        ButterKnife.bind(this);

        mFindMeal.setOnClickListener(this);
        mSavedMeals.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == mFindMeal){
            String meal = mMealName.getText().toString();

            saveMealToFirebase(meal);
            Intent intent = new Intent(MealSearchActivity.this, MealsActivity.class);
            intent.putExtra("meal",meal);
            Toast.makeText(MealSearchActivity.this,"Searching meal...",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        if (v == mSavedMeals){
            Intent intent = new Intent(MealSearchActivity.this, SavedRecipeListActivity.class);
            startActivity(intent);
        }
    }

    private void saveMealToFirebase(String meal){
        mSearchedMeal.push().setValue(meal);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedMeal.removeEventListener(mSearchedMealListener);
    }
}
