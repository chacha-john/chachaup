package com.chachaup.chachaup.network;

import static com.chachaup.chachaup.Constants.MEAL_DB_BASE_URL;

import com.chachaup.chachaup.Constants;
import com.chachaup.chachaup.network.MealDBApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealDBClient {
    private static Retrofit retrofit = null;
    public static MealDBApi getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(MEAL_DB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MealDBApi.class);
    }
}
