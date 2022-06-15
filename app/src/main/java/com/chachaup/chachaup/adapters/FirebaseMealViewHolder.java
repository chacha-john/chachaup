package com.chachaup.chachaup.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chachaup.chachaup.Constants;
import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.ui.MealDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirebaseMealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseMealViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

    }

    public void bindMeal(Meal meal){
        ImageView recipeImageView = (ImageView) mView.findViewById(R.id.mealImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.mealNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView instructionsTextView = (TextView) mView.findViewById(R.id.instructionsTextView);

        Picasso.get().load(meal.getStrMealThumb()).into(recipeImageView);
        nameTextView.setText(meal.getStrMeal());
        categoryTextView.setText(meal.getStrCategory());
        instructionsTextView.setText(meal.getStrInstructions());
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Meal> meals = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    meals.add(dataSnapshot.getValue(Meal.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, MealDetailActivity.class);
                intent.putExtra("position",itemPosition);
                intent.putExtra("meals", Parcels.wrap(meals));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
