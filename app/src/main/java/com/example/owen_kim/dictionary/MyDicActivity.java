package com.example.owen_kim.dictionary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.owen_kim.dictionary.APIS.MyListAdapter;
import com.example.owen_kim.dictionary.APIS.Diclist_item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MyDicActivity extends AppCompatActivity{

    ListView listView;
    MyListAdapter myListAdapter;
    ArrayList<Diclist_item> list_itemArrayDiclist;
    String[] rows;
    int selectedPos = -1;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dic);

        listView = (ListView)findViewById(R.id.my_listview);
        list_itemArrayDiclist = new ArrayList<Diclist_item>();

        Thread thread = new Thread(){
            String user_id = getIntent().getStringExtra("user_id");

            @Override
            public void run() {
                try {
                    URL url = new URL("http://133.186.144.151/getMydic.php?user_id='"+user_id+"'");
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    BufferedReader reader = new BufferedReader(isr);

                    String str = reader.readLine();


                    rows = str.split("/");

                    for (int i = 0; i < rows.length; i++)
                    {
                        String[] row1 = rows[i].split(",",0);

                        String eng_word = row1[0];
                        String img_route = row1[1];

                        list_itemArrayDiclist.add(new Diclist_item("http://133.186.144.151/uploads/"+img_route+"?user_id='"+user_id+"'",eng_word));
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyDicActivity.this);
                            builder.setMessage("먼저 사전에 추가하세요.")
                                    .setCancelable(false)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        // 확인 버튼 클릭시 설정
                                        public void onClick(DialogInterface dialog, int whichButton) {
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

        myListAdapter = new MyListAdapter(MyDicActivity.this, list_itemArrayDiclist);
        listView.setAdapter(myListAdapter);
    }


}
