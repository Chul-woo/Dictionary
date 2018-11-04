package com.example.owen_kim.dictionary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.owen_kim.dictionary.APIS.Diclist_item;
import com.example.owen_kim.dictionary.APIS.FriendDicAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FriendDicActivity extends Activity{

    FriendDicAdapter adapter;
    ArrayList<Diclist_item> list_itemArrayList;
    String[] rows;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fr_dic);

        ListView listView = (ListView) findViewById(R.id.fr_listview);
        list_itemArrayList = new ArrayList<>();

        Thread thread = new Thread(){
            String user_id = getIntent().getStringExtra("user_id");

            public void run(){
                try {
                    URL url = new URL("http://133.186.144.151/getMydic.php?user_id='" + user_id + "'");
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    BufferedReader reader = new BufferedReader(isr);

                    String str = reader.readLine();

                    rows = str.split("/");

                    for(int i =0;i< rows.length;i++){
                        String[] row1 = rows[i].split(",", 0);

                        String eng_word = row1[0];
                        String img_route = row1[1];

                        list_itemArrayList.add(new Diclist_item("http://133.186.144.151/uploads/"+img_route+"?user_id='"+user_id+"'",eng_word));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FriendDicActivity.this);
                            builder.setMessage("아직 추가된 단어가 없습니다")
                                    .setCancelable(false)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            onBackPressed();
                                        }
                                    });
                            builder.show();
                        }
                    },0);
                }
            }
        };

        thread.start();

        adapter = new FriendDicAdapter(FriendDicActivity.this, list_itemArrayList);
        listView.setAdapter(adapter);
    }
}
