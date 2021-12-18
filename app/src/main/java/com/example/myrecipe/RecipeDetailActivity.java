package com.example.myrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myrecipe.Model.Recipe;
import com.example.myrecipe.database.DatabaseService;
import com.example.myrecipe.networking.JsonService;
import com.example.myrecipe.networking.NetworkingService;

import java.util.ArrayList;

public class
RecipeDetailActivity extends AppCompatActivity  implements NetworkingService.NetworkingListener{

    TextView nameText;
    ListView ingredientsView;
    ListView instructionsView;

    ImageView imageView;
    NetworkingService networkingService;
    JsonService jsonService;
    Recipe selectedRecipe;
    MenuItem menuItemAdd;
    boolean readOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        networkingService = ( (myApp)getApplication()).getNetworkingService();
        jsonService = ( (myApp)getApplication()).getJsonService();
        networkingService.setListener(this);
        readOnly=getIntent().getBooleanExtra("readOnly",false);

        selectedRecipe = ( (myApp)getApplication()).selectedRecipe;
        networkingService.getImageData2(selectedRecipe.getImage());

        setTitle(selectedRecipe.getName());

        ingredientsView = findViewById(R.id.ingredients);
        instructionsView = findViewById(R.id.instructions);
        ArrayList<String> ingredients=jsonService.jsonArrayToStringArray(selectedRecipe.getIngredients());
        ArrayList<String> instructions=jsonService.jsonArrayToStringArray(selectedRecipe.getInstructions());

        final ArrayAdapter<String> ingredientsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,ingredients);
        ingredientsView.setAdapter(ingredientsAdapter);

        final ArrayAdapter<String> instructionsAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,instructions);
        instructionsView.setAdapter(instructionsAdapter);

        imageView = findViewById(R.id.image);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!readOnly){
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.save_menu,menu);
            menuItemAdd=menu.findItem(R.id.save);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.save:{
                DatabaseService dbService = ((myApp)getApplication()).getDbService();
                dbService.saveNewRecipe(selectedRecipe);
                finish();

                navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
                break;
            }
            default:
                finish();
        }
        return true;
    }



    @Override
    public void APINetworkListner(String jsonString) {
    }

    @Override
    public void APINetworkingListerForImage(Bitmap image) {
        imageView.setImageBitmap(image);
    }


}