package com.example.owen_kim.dictionary.APIS;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseSystem {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseSystem(Activity activity){
        this.activity = activity;
    }

    public void onBackPressed(){
        if(isAfter2Seconds()){
            backKeyPressedTime = System.currentTimeMillis();
            //현재시간을 다시 초기화

            Toast.makeText(activity, "한 번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isBefore2Seconds()){
            programShutdown();
            toast.cancel();
        }
    }

    private Boolean isAfter2Seconds(){
        return System.currentTimeMillis() > backKeyPressedTime + 2000;
    }

    private Boolean isBefore2Seconds(){
        return System.currentTimeMillis() <= backKeyPressedTime + 2000;
    }

    private void programShutdown(){
        activity.moveTaskToBack(true);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
