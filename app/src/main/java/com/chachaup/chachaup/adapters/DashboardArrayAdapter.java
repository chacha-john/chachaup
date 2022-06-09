package com.chachaup.chachaup.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

public class DashboardArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mMeals;
    private String[] mRecipes;


    public DashboardArrayAdapter(Context mContext, int resource, String[] mMeals, String[] mRecipes){
        super(mContext, resource);
        this.mContext = mContext;
        this.mMeals = mMeals;
        this.mRecipes = mRecipes;

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        String meal = mMeals[position];
//        String ingredients = mIngredients[position];
        String recipe = mRecipes[position];
        return String.format("%s preparation procedure is %s",meal,recipe);
    }

    @Override
    public int getCount() {
        return mMeals.length;
    }
}
