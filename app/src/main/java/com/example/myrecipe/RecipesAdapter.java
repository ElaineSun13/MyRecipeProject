package com.example.myrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.Model.Recipe;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.TasksViewHolder> {

    interface recipeClickListner {
        public void recipeClicked(Recipe selectedCity);
    }

    private Context mCtx;
    public List<Recipe> recipes;
    recipeClickListner listner;

    public RecipesAdapter(Context mCtx, List<Recipe> recipes) {
        this.mCtx = mCtx;
        this.recipes = recipes;
        listner = (recipeClickListner) mCtx;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_recipes, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Recipe t = recipes.get(position);
        holder.nameTextView.setText(t.getName());
        holder.servingsTextView.setText(t.getServings());
        holder.timeTextView.setText(t.getTotalTime());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView, servingsTextView, timeTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recipe_name);
            servingsTextView = itemView.findViewById(R.id.recipe_servings);
            timeTextView = itemView.findViewById(R.id.recipe_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());
            listner.recipeClicked(recipe);
        }
    }
}
