package com.example.myrecipe.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myrecipe.Model.Recipe;


@Database(version = 2,entities = {Recipe.class})
public abstract class RecipesDatabase extends RoomDatabase {
    abstract RecipeDAO getDao();
}
