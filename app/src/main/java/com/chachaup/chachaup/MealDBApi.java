package com.chachaup.chachaup;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealDBApiInterface {
    @GET("search")
    Call<MealSearchResponse> getMeals(
            @Query("strMeal") String mealName
    );
}
