package com.example.lfuzzatti.poc1.controller;

import android.content.Context;

import com.example.lfuzzatti.poc1.adapter.PetDataAdapter;
import com.example.lfuzzatti.poc1.model.Pet;
import com.example.lfuzzatti.poc1.model.PetList;
import com.example.lfuzzatti.poc1.model.Vacina;
import com.example.lfuzzatti.poc1.util.FileUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PetController {

    private Context context;
    private Gson gson;

    /**
     * Construtor obtendo o contexto atual
     * @param context
     */
    public PetController(Context context) {
        this.context = context;
    }

    /**
     * Método responsavel em listar todos os Pets
     * @return List<Pet> lista de pet
     */
    public List<Pet> listAllPets() {

        List<Pet> pets = new ArrayList<>();
        gson = new Gson();

        String jsonPets = FileUtil.loadJSONFromFile(context, FileUtil.PETS_LIST_ALL);
        PetList petList = gson.fromJson(jsonPets, PetList.class);

        for (Pet pet : petList.getPets()) {
            pets.add(pet);
        }

        return pets;
    }

}
