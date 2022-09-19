package com.chachaup.chachaup.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.ui.MealDetailActivity;
import com.chachaup.chachaup.ui.MealsActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MealsListAdapter extends RecyclerView.Adapter<MealsListAdapter.MealViewHolder> {
    private List<Meal> mMeals;
    private Context mContext;

    public MealsListAdapter(Context context, List<Meal> meals){
        mContext = context;
        mMeals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item,parent,false);
        MealViewHolder viewHolder = new MealViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.bindMeal(mMeals.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.mealImageView)
        ImageView mMealImageView;
        @BindView(R.id.mealNameTextView)
        TextView mMealTextView;
        @BindView(R.id.categoryTextView) TextView mCategoryTextView;

        private Context mContext;

        public MealViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext=itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindMeal(Meal meal){
            mMealTextView.setText(meal.getStrMeal());
            mCategoryTextView.setText(meal.getStrCategory());
            Picasso.get().load(meal.getStrMealThumb()).into(mMealImageView);
        }


        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, MealDetailActivity.class);
            intent.putExtra("position",itemPosition);
            intent.putExtra("meals", Parcels.wrap(mMeals));
            mContext.startActivity(intent);
        }
    }
}
