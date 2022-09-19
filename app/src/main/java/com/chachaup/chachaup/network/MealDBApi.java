package com.chachaup.chachaup.network;

import com.chachaup.chachaup.models.MealSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealDBApi {
    @GET("search.php")
    Call<MealSearchResponse> getMeals(
            @Query("s") String mealName);
}