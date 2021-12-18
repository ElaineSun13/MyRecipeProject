package com.example.myrecipe.networking;

import com.example.myrecipe.Model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {


    public ArrayList<String> jsonArrayToStringArray(String jsonString){
        ArrayList<String> result = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i<jsonArray.length(); i++) {
                result.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public ArrayList<Recipe> parseRecipeAPIData(String jsonRecipeString){
        ArrayList<Recipe> data = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonRecipeString);// root
            JSONArray recipeArray = jsonObject.getJSONArray("data");
            for (int i = 0; i<recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);
                String name = recipeObject.getString("name");
                String image = recipeObject.getString("image");
                Recipe recipeData = new Recipe(name,image);
                recipeData.setIngredients(recipeObject.getString("ingredients"));
                recipeData.setInstructions(recipeObject.getString("instructions"));
                recipeData.setServings(recipeObject.getString("servings"));
                recipeData.setTotalTime (recipeObject.getJSONObject("time").getString("total"));
                data.add(recipeData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    //
}
