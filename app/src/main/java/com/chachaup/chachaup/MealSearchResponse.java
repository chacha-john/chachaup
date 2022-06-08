
package com.chachaup.chachaup;

import java.util.List;
import javax.annotation.Generated;

import com.chachaup.chachaup.models.Meal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MealSearchResponse {

    @SerializedName("meals")
    @Expose
    private List<Meal> meals = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MealSearchResponse() {
    }

    /**
     * 
     * @param meals
     */
    public MealSearchResponse(List<Meal> meals) {
        super();
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
