package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {

    public static boolean checkConnection(Context context){
        try{
            return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
