package com.example.myrecipe.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.Model.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    void addNewRecipe(Recipe c);


    @Delete
    void deleteRecipe(Recipe c);

    @Query("SELECT * FROM Recipe")
    List<Recipe> getAllRecipes();



}
