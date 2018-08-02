package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    Context context;
    private String id;
    private String password;
    SharedPreferences sharedPreferences;

    public User(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user_name",Context.MODE_PRIVATE );
    }

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getId() {
        id = sharedPreferences.getString("userdata", "");
        return id;
    }

    public void setId(String id) {
        this.id = id;
        sharedPreferences.edit().putString("userdata", id).commit();
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
