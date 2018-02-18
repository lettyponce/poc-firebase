package com.example.lfuzzatti.poc1.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.lfuzzatti.poc1.R;
import com.example.lfuzzatti.poc1.adapter.PetDataAdapter;
import com.example.lfuzzatti.poc1.controller.PetController;
import com.example.lfuzzatti.poc1.controller.SwipeController;
import com.example.lfuzzatti.poc1.controller.SwipeControllerActions;
import com.example.lfuzzatti.poc1.model.Pet;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PetDataAdapter petDataAdapter;
    private PetController petController;

    private RecyclerView recyclerView;
    private SwipeController swipeController;

    private List<Pet> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        petController = new PetController(this);

        setupListPets();
        setupRecyclerViewPets();
    }

    private void setupListPets() {
        pets = petController.listAllPets();
        petDataAdapter = new PetDataAdapter(pets);
    }


    private void setupRecyclerViewPets() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        recyclerView.setAdapter(petDataAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                petDataAdapter.listPets.remove(position);
                petDataAdapter.notifyItemRemoved(position);
                petDataAdapter.notifyItemRangeRemoved(position, petDataAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Toast.makeText(getBaseContext(),
                        "Editando o item: "+ pets.get(position).getNome(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
