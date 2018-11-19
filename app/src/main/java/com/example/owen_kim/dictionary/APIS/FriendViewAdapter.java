package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.owen_kim.dictionary.DicActivity;
import com.example.owen_kim.dictionary.FriendListActivity;
import com.example.owen_kim.dictionary.R;
import com.example.owen_kim.dictionary.Requests.DeleteFriendRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendViewAdapter extends BaseAdapter {

    private Context context;
    ViewHolder viewHolder;
    private ArrayList<friend_item> friend_items = new ArrayList<friend_item>();

    public FriendViewAdapter(Context context, ArrayList<friend_item> friend_items){
        this.context = context;
        this.friend_items = friend_items;
    }

    @Override
    public int getCount() {
        return friend_items.size();
    }

    @Override
    public Object getItem(int i) {
        return friend_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.friend_listview,null);
            viewHolder = new FriendViewAdapter.ViewHolder();
            viewHolder.name  =(TextView)view.findViewById(R.id.friendIds);
            viewHolder.delete_button = (Button)view.findViewById(R.id.delete_button);
            view.setTag(viewHolder);
        } else{
            viewHolder = (FriendViewAdapter.ViewHolder)view.getTag();
        }

        viewHolder.name.setText(friend_items.get(position).getFriend_id());
        viewHolder.delete_button.setTag(position);
        viewHolder.name.setTag(position);

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            int position;
            String friend_id, user_id;
            @Override
            public void onClick(View v) {
                position = Integer.parseInt( (v.getTag().toString()) );
                user_id = friend_items.get(position).getUser_id();
                friend_id = friend_items.get(position).getFriend_id();
                Intent toFrDic = new Intent(context, DicActivity.class);
                toFrDic.putExtra("user_id", user_id);
                toFrDic.putExtra("friend_id", friend_id);
                context.startActivity(toFrDic);
            }
        });

        viewHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            int position;
            String friend_id;
            @Override
            public void onClick(View view) {
                position = Integer.parseInt( (view.getTag().toString()) );
                String user_id = friend_items.get(position).getUser_id();
                friend_id = friend_items.get(position).getFriend_id();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Log.i("2: ", "2번");
                            //Toast.makeText(context, String.valueOf(success), Toast.LENGTH_SHORT).show();
                            if(success){
                                Toast.makeText(context, friend_id+ "님이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                friend_items.remove(position);
                                notifyDataSetChanged();
                            }else{
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteFriendRequest deleteFriendRequest = new DeleteFriendRequest(user_id, friend_id, listener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteFriendRequest);
            }
        });

        return view;
    }

    class ViewHolder{
        TextView name;
        Button delete_button;
    }

}
