package com.chachaup.chachaup.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MealDetailFragment extends Fragment {
    @BindView(R.id.mealName)
    TextView mTextView;
    @BindView(R.id.areaTextView) TextView mAreaTextView;
    @BindView(R.id.instructions) TextView mInstructions;
    @BindView(R.id.youtubeTextView) TextView mYoutube;
    @BindView(R.id.mealImage)
    ImageView mMealImage;

    private Meal meal;

    public MealDetailFragment() {
        // Required empty public constructor
    }

    public static MealDetailFragment newInstance(Meal meal) {
        MealDetailFragment fragment = new MealDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("meal", Parcels.wrap(meal));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        meal = Parcels.unwrap(getArguments().getParcelable("meal"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);
        ButterKnife.bind(this,view);

        Picasso.get().load(meal.getStrMealThumb()).into(mMealImage);

        mAreaTextView.setText(meal.getStrArea());
        mInstructions.setText(meal.getStrInstructions());
        mTextView.setText(meal.getStrMeal());
        mYoutube.setText(meal.getStrYoutube());
        return view;
    }

//    public void onClick(View v){
//        if (v == mYoutube){
//            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(meal.getStrYoutube()));
//            startActivity(youtubeIntent);
//        }
//    }
}