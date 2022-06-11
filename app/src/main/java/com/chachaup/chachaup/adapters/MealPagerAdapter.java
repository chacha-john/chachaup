package com.chachaup.chachaup.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.ui.MealDetailFragment;

import java.util.List;

public class MealPagerAdapter extends FragmentPagerAdapter {
    private List<Meal> mMeals;

    public MealPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Meal> meals){
        super(fm, behavior);
        mMeals = meals;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return MealDetailFragment.newInstance(mMeals.get(position));
    }

    @Override
    public int getCount() {
        return mMeals.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mMeals.get(position).getStrMeal();
    }
}
