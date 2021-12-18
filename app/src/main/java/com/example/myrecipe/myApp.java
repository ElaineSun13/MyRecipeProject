package com.example.myrecipe;

import android.app.Application;

import com.example.myrecipe.Model.Recipe;
import com.example.myrecipe.database.DatabaseService;
import com.example.myrecipe.networking.JsonService;
import com.example.myrecipe.networking.NetworkingService;

public class myApp extends Application {

   public NetworkingService getNetworkingService() {
      return networkingService;
   }

   DatabaseService dbService = new DatabaseService();

   public DatabaseService getDbService() {
      return dbService;
   }

   private NetworkingService networkingService = new NetworkingService();
   private JsonService jsonService = new JsonService();
//   public ArrayList<Recipe> recipes = new ArrayList<>();

   public Recipe selectedRecipe = null;

   public JsonService getJsonService() {
      return jsonService;
   }
//   public void addRecipe(Recipe recipe) {
//      recipes.add(recipe);
//   }

}
