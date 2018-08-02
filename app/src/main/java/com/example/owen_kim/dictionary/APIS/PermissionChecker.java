package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionChecker {
    private final Context context;

    public PermissionChecker(Context context){
        this.context = context;
    }

    public boolean lacksPermission(String[] permissions){
        for(String permission : permissions){
            if(lacksPermission(permission)){
                return true;
            }
        }
        return false;
    }

    /*
     for(String permission : permissions){
            if(lacksPermission(permission)){
                return true;
            }
        }

        return false;
     */

    public boolean lacksPermission(String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }

}
