package com.chachaup.chachaup;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

public class DashboardArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mMeals;
    private String[] mIngredients;
    private String[] mRecipes;


    public DashboardArrayAdapter(Context mContext, int resource, String[] mMeals, String[] mIngredients, String[] mRecipes){
        super(mContext, resource);
        this.mContext = mContext;
        this.mIngredients = mIngredients;
        this.mMeals = mMeals;
        this.mRecipes = mRecipes;

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        String meal = mMeals[position];
        String ingredients = mIngredients[position];
        String recipe = mRecipes[position];
        return String.format("%s requires the following ingredients to be prepared: %s. The procedure for making it is %s",meal,ingredients,recipe);
    }

    @Override
    public int getCount() {
        return mMeals.length;
    }
}
