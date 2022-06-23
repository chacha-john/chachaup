package com.chachaup.chachaup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chachaup.chachaup.R;
import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.utils.ItemTouchHelperAdapter;
import com.chachaup.chachaup.utils.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class FirebaseMealListAdapter extends FirebaseRecyclerAdapter<Meal, FirebaseMealViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    public FirebaseMealListAdapter(FirebaseRecyclerOptions<Meal> options,
                                   DatabaseReference ref,
                                   OnStartDragListener onStartDragListener,
                                   Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseMealViewHolder holder, int position, @NonNull Meal meal) {
        holder.bindMeal(meal);

    }

    @NonNull
    @Override
    public FirebaseMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item_drag, parent, false);
        return new FirebaseMealViewHolder(view);
    }
}
