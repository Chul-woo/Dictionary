package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.owen_kim.dictionary.R;

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
            //viewHolders.checkBox.setVisibility(View.GONE);
            //checkBox = viewHolders.checkBox;
            view.setTag(viewHolder);
        } else{
            viewHolder = (FriendViewAdapter.ViewHolder)view.getTag();
        }

        viewHolder.name.setText(friend_items.get(position).getFriend_id());

        return view;
    }

    class ViewHolder{
        TextView name;
    }

}
