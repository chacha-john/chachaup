package com.chachaup.chachaup.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.ui.MealDetailActivity;
import com.chachaup.chachaup.utils.ItemTouchHelperAdapter;
import com.chachaup.chachaup.utils.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseMealListAdapter extends FirebaseRecyclerAdapter<Meal, FirebaseMealViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private Query mQuery;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private ChildEventListener mChildEventListener;
    private ArrayList<Meal> mMeals = new ArrayList<>();

    public FirebaseMealListAdapter(FirebaseRecyclerOptions<Meal> options,
                                   Query query,
                                   OnStartDragListener onStartDragListener,
                                   Context context){
        super(options);
//        mRef = ref.getRef();
        mQuery = query.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mMeals.add(snapshot.getValue(Meal.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mMeals, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mMeals.remove(position);
        getRef(position).removeValue();

    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseMealViewHolder holder, int position, @NonNull Meal meal) {
        holder.bindMeal(meal);
        holder.recipeImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MealDetailActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.putExtra("meals", Parcels.wrap(mMeals));
                mContext.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public FirebaseMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_drag, parent, false);
        return new FirebaseMealViewHolder(view);
    }

    private void setIndexInFirebase(){
        for (Meal meal : mMeals){
            int index = mMeals.indexOf(meal);
            Query query = getRef(index);
            meal.setIndex(Integer.toString(index));
            query.getRef().setValue(meal);
        }
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mQuery.removeEventListener(mChildEventListener);
    }
}
