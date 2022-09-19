package com.chachaup.chachaup.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.chachaup.chachaup.Constants;
import com.chachaup.chachaup.R;
import com.chachaup.chachaup.adapters.FirebaseMealListAdapter;
import com.chachaup.chachaup.adapters.FirebaseMealViewHolder;
import com.chachaup.chachaup.adapters.MealsListAdapter;
import com.chachaup.chachaup.models.Meal;
import com.chachaup.chachaup.models.MealSearchResponse;
import com.chachaup.chachaup.network.MealDBApi;
import com.chachaup.chachaup.network.MealDBClient;
import com.chachaup.chachaup.utils.ItemTouchHelperAdapter;
import com.chachaup.chachaup.utils.OnStartDragListener;
import com.chachaup.chachaup.utils.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedRecipeListActivity extends AppCompatActivity {
    private DatabaseReference mMealReference;
    private FirebaseMealListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;


    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBarMeals)
    ProgressBar mProgressBar;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private MealsListAdapter mAdapter;

    public List<Meal> mealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        ButterKnife.bind(this);


        setUpFirebaseAdapter();
        hideProgressBar();
        showRecipe();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                fetchMealRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
//        mMealReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MEALS).child(uid);
        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_MEALS)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<Meal> options = new FirebaseRecyclerOptions.Builder<Meal>()
                .setQuery(query, Meal.class)
                .build();

        mFirebaseAdapter = new FirebaseMealListAdapter(options, query, this::onStartDrag, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null){
            mFirebaseAdapter.stopListening();
        }
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void addToSharedPreferences(String meal){
        mEditor.putString(Constants.PREFERENCES_MEAL_KEY,meal).apply();
    }

    private void fetchMealRecipes(String query){
        MealDBApi client = MealDBClient.getClient();
        Call<MealSearchResponse> call = client.getMeals(query);
        call.enqueue(new Callback<MealSearchResponse>() {
            @Override
            public void onResponse(Call<MealSearchResponse> call, Response<MealSearchResponse> response) {

                hideProgressBar();

                if (response.isSuccessful()){
                    mealsList = response.body().getMeals();
                    mAdapter = new MealsListAdapter(SavedRecipeListActivity.this, mealsList);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SavedRecipeListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showRecipe();

                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<MealSearchResponse> call, Throwable t) {

            }
        });
    }
    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRecipe() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }
}