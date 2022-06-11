package com.chachaup.chachaup.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.chachaup.chachaup.R;
import com.chachaup.chachaup.adapters.MealPagerAdapter;
import com.chachaup.chachaup.models.Meal;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private MealPagerAdapter adapterViewPager;
    List<Meal> meals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        ButterKnife.bind(this);

        meals = Parcels.unwrap(getIntent().getParcelableExtra("meals"));
        int startingPosition = getIntent().getIntExtra("position",0);

        adapterViewPager = new MealPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, meals);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}