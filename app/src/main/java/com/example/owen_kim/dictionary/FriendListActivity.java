package com.example.owen_kim.dictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.APIS.FriendViewAdapter;
import com.example.owen_kim.dictionary.APIS.friend_item;
import com.example.owen_kim.dictionary.Requests.AddFriendRequest;
import com.example.owen_kim.dictionary.Requests.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class FriendListActivity extends AppCompatActivity {
    private Button add;
    private Button delete;
    private ListView listView;
    private FriendViewAdapter adapter;
    private EditText add_friend_id;
    ArrayList<friend_item> list_friend_item;
    HashSet<String> friends;
    String[] rows;
    String fid = null;
    boolean exist;
    RequestQueue queue;
    Response.Listener<String> responseListener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");
        add = (Button) findViewById(R.id.add);
        add_friend_id = (EditText) findViewById(R.id.add_friend_id);
        listView = (ListView) findViewById(R.id.listView);
        list_friend_item = new ArrayList<>();
        friends = new HashSet<>();
        delete = (Button) findViewById(R.id.delete_button);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://133.186.144.151/getFriends.php?user_id='" + user_id + "'");
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    String str = reader.readLine();
                    rows = str.split("/");
                    for(int i = 0; i<rows.length;i++){
                        fid = rows[i];
                        Log.i("fid: ", fid);
                        if(!(fid.equals(""))) {
                            list_friend_item.add(new friend_item(user_id, fid));
                            friends.add(fid);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("url error: ", e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("io error: ", e.toString());
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("exception error: ", e.toString());
                }
            }
        });
        thread.start();
        Log.i("list_friend_item length: ", String.valueOf(list_friend_item.size()));
        adapter = new FriendViewAdapter(this, list_friend_item);
        listView.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String friend_id = add_friend_id.getText().toString().trim();
                Log.i("Friend_id: ", friend_id);
                boolean results = friends.contains(friend_id);
                Log.i("Boolean: ", String.valueOf(results));
                if(friend_id.equals("")){
                    Toast.makeText(getApplicationContext(), "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(results){
                    Log.i("Boolean In: ", String.valueOf(results));
                    Toast.makeText(getApplicationContext(), "이미 친구목록에 존재합니다.", Toast.LENGTH_SHORT).show();
                    add_friend_id.setText("");
                    return;
                }
                else if(friend_id.equals(user_id)) {
                    Toast.makeText(getApplicationContext(), "본인ID는 추가할수 없습니다.", Toast.LENGTH_SHORT).show();
                    add_friend_id.setText("");
                    return;
                }
                responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(exist) return;
                            if(success){
                                Toast.makeText(getApplicationContext(), "추가했습니다", Toast.LENGTH_SHORT).show();
                                add_friend_id.setText("");
                                list_friend_item.add(new friend_item(user_id, friend_id));
                                friends.add(friend_id);
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getApplicationContext(), "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            exist = jsonResponse.getBoolean("success");
                            if(exist){
                                Toast.makeText(getApplicationContext(), "존재하지 않는 ID입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                queue.add( new AddFriendRequest(user_id, friend_id, responseListener2));
                                Toast.makeText(getApplicationContext(), "추가했습니다", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(friend_id, responseListener);
                queue = Volley.newRequestQueue(FriendListActivity.this);
                queue.add(validateRequest);
            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final TextView textView = (TextView) view.findViewById(R.id.friendIds);
//                final String friend_id = textView.getText().toString().trim();
//                Intent toFrDic = new Intent(FriendListActivity.this, DicActivity.class);
//                toFrDic.putExtra("user_id", friend_id);
//                startActivity(toFrDic);
//            }
//        });
    }
}