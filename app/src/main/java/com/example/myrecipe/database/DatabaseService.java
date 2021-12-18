package com.example.myrecipe.database;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.myrecipe.Model.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseService {

    public interface DatabaseListener{
        void databaseRecipesListener(List<Recipe> dbrecipes);
    }
    public DatabaseListener listener;


    public static RecipesDatabase dbInstance;


    ExecutorService recipesExecutor = Executors.newFixedThreadPool(4);
    Handler recipesHandler = new Handler(Looper.getMainLooper());

    private void buildDB(Context context){
        dbInstance = Room.databaseBuilder(context,
                RecipesDatabase.class, "recipes_database").build();
    }


    public RecipesDatabase getDbInstance(Context context){
        if (dbInstance == null)
            buildDB(context);

        return dbInstance;
    }


   public void  getAllRecipesFromDB(){
        //dbService.dbInstance.getDao().getAllRecipes();

        recipesExecutor.execute(new Runnable() {
            @Override
            public void run() {
               List<Recipe> recipes = dbInstance.getDao().getAllRecipes();
               recipesHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       listener.databaseRecipesListener(recipes);
                   }
               });
            }
        });
    }

    public void saveNewRecipe(Recipe c){
        recipesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbInstance.getDao().addNewRecipe(c);
            }
        });
    }























}
