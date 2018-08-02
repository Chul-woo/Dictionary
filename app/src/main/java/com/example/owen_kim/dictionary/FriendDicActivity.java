package com.example.owen_kim.dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.APIS.CustomDialog;
import com.example.owen_kim.dictionary.APIS.FriendViewAdapter;
import com.example.owen_kim.dictionary.APIS.FriendViewAdapter;
import com.example.owen_kim.dictionary.APIS.MyListAdapter;
import com.example.owen_kim.dictionary.APIS.friend_item;
import com.example.owen_kim.dictionary.APIS.list_item;
import com.example.owen_kim.dictionary.Requests.FriendsAddRequest;
import com.example.owen_kim.dictionary.Requests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static com.example.owen_kim.dictionary.APIS.FriendViewAdapter.checkBox;

public class FriendDicActivity extends AppCompatActivity{

    private Button addFriend, deleteFriend;
    private ListView listView;
    private CustomDialog dialog;
    private EditText inputFriend;
    FriendViewAdapter adpater;
    ArrayList<friend_item> friend_items;
    String userId;
    String[] rows;
    int selectedPos = -1;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdic);

        userId = getIntent().getStringExtra("user_id");

        addFriend = (Button) findViewById(R.id.addFriend);
        listView = (ListView) findViewById(R.id.fr_listview);

        listView.setAdapter(adpater);

        friend_items = new ArrayList<friend_item>();

        Thread thread = new Thread(){
            public void run(){
                try{
                    URL url = new URL("http://133.186.144.151/getFriends.php?user_id= '" +userId + "'");

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    BufferedReader reader = new BufferedReader(isr);

                    String str = reader.readLine();

                    rows = str.split("/");

                    for (int i = 0; i < rows.length; i++)
                    {
                        String[] row1 = rows[i].split(",",0);

                        String friend_id = row1[0];

                        friend_items.add(new friend_item("http://133.186.144.151/user_id='"+userId+"'",friend_id));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();

/*

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String friend_id = inputFriend.getText().toString();
                final String user_id = userId;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                if(dialog.addedName.equals("")){
                                    Toast.makeText(getApplicationContext(), "추가 할 아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), friend_id + "님을 추가했습니다.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                FriendsAddRequest friendsAddRequest = new FriendsAddRequest(user_id, friend_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FriendDicActivity.this);
                queue.add(friendsAddRequest);


            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
        *///////////////
    }
}

