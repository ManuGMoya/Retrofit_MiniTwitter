package com.manu.retrofit_minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.manu.retrofit_minitwitter.R;

public class SharedPreferencesManager {

    // Nombre del fichero donde guardaremos las preferencias
    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    // Metodo constructor
    private SharedPreferencesManager(){}

    // Metodo
    private static SharedPreferences getSharedPreferences (){
        // Obtenemos el contexto desde nuestra clase MyApp - MyApp.getContext()
       return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    // METODOS DE GUARDADO DE PREFERENCIAS. Por parametro recibimos la etiqueta y su valor
    // Guardado de String
    public static void setSomeStringValue(String dataLabel, String dataValue){
        // En primer lugar, necesitamos el editor
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        // Le pasamos los datos al editor (clave + valor)
        editor.putString(dataLabel,dataValue);
        // Guardamos la preferencia
        editor.commit();
    }

    // Guardado de boolean
    public static void setSomeBooleanValue(String dataLabel, boolean dataValue){
        // En primer lugar, necesitamos el editor
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        // Le pasamos los datos al editor (clave + valor)
        editor.putBoolean(dataLabel,dataValue);
        // Guardamos la preferencia
        editor.commit();
    }


    // METODOS PARA OBTENCION DE PREFERENCIAS
    public static String getSomeStringValue(String dataLabel){
        // El segundo valor es que devolvemos en caso de no existir ese dataLabel
        return getSharedPreferences().getString(dataLabel,null);
    }

    public static Boolean getSomeBooleanValue(String dataLabel){
        return getSharedPreferences().getBoolean(dataLabel,false);
    }

}
