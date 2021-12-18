package com.example.myrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myrecipe.Model.Recipe;
import com.example.myrecipe.database.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipesAdapter.recipeClickListner, DatabaseService.DatabaseListener{

    RecyclerView recipeView;
    RecipesAdapter adapter;
    DatabaseService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Recipes");
        //recycler view
        recipeView = findViewById(R.id.dbrecipes);

        dbService = ((myApp)getApplication()).getDbService();
        dbService.getDbInstance(this);
        dbService.getAllRecipesFromDB();
        dbService.listener = this;

        recipeView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RecipesAdapter(this, ((myApp)getApplication()).recipes);
        adapter = new RecipesAdapter(this,new ArrayList<>(0));
        adapter.listner = this;


        recipeView.setAdapter(adapter);

        Log.d("MainActivity", "adapter.recipes"+ adapter.recipes.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        adapter.recipes = ((myApp)getApplication()).recipes;
//        adapter.notifyDataSetChanged();
    }
    public void addNewRecipe(View view) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        startActivity(searchIntent);
    }

    @Override
    public void recipeClicked(Recipe selectedRecipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("readOnly", true);

        ( (myApp)getApplication()).selectedRecipe = selectedRecipe;

        startActivity(intent);
    }

    @Override
    public void databaseRecipesListener(List<Recipe> dbRecipes) {
        adapter.recipes = dbRecipes;
        adapter.notifyDataSetChanged();
    }
}