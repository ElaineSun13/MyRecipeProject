package com.example.myrecipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.Model.Recipe;
import com.example.myrecipe.networking.JsonService;
import com.example.myrecipe.networking.NetworkingService;

import java.util.ArrayList;

public class


SearchActivity extends AppCompatActivity implements
        RecipesAdapter.recipeClickListner, NetworkingService.NetworkingListener {

    ArrayList<Recipe> recipes = new ArrayList<>();
//    ArrayList<String> names = new ArrayList<>();
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    NetworkingService networkingService;
    JsonService jsonService;
    private ProgressBar progressBar;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recipe_names);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipesAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        setTitle("Search for new recipes..");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        networkingService = ( (myApp)getApplication()).getNetworkingService();
        jsonService = ( (myApp)getApplication()).getJsonService();


        networkingService.setListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        String searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for recipes");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                networkingService.fetchRecipeData(query);
                progressBar.setVisibility(View.VISIBLE);
//                networkingService.fetchRecipeData("Parmesan Crisps");
                Log.d("query", query);//
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkingService = ( (myApp)getApplication()).getNetworkingService();
        jsonService = ( (myApp)getApplication()).getJsonService();
        networkingService.setListener(this);
    }

    @Override
    public void recipeClicked(Recipe selectedRecipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        ( (myApp)getApplication()).selectedRecipe = selectedRecipe;

        startActivity(intent);
    }


    @Override
    public void APINetworkListner(String jsonString) {
        progressBar.setVisibility(View.GONE);
        recipes = jsonService.parseRecipeAPIData(jsonString);
        adapter.recipes = recipes;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void APINetworkingListerForImage(Bitmap image) {

    }

}
