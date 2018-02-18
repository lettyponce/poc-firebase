package com.example.lfuzzatti.poc1.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static final String PETS_LIST_ALL = "pets_list_all.json";
    public static final String PETS_LIST_VACINAS = "pet_list_vacinas.json";
    public static final String PETS_LIST_MEDICAMENTOS = "pet_list_medicamentos.json";
    public static final String PETS_LIST_TOSAS = "pet_list_tosas.json";

    public static String loadJSONFromFile(Context context, String file) {

        String json = null;

        try{

            InputStream is = loadInputStreamFromAssetFile(context, file);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

        return json;
    }

    public static InputStream loadInputStreamFromAssetFile(Context context, String fileName){
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
