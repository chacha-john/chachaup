package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chachaup.chachaup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealSearchActivity extends AppCompatActivity {
    @BindView(R.id.buttonFindMeal)
    Button mFindMeal;
    @BindView(R.id.editTextMealName)
    EditText mMealName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_meal);
        ButterKnife.bind(this);

        mFindMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealSearchActivity.this, MealsActivity.class);
                intent.putExtra("meal",mMealName.getText().toString());
                Toast.makeText(MealSearchActivity.this,"Searching meal...",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
