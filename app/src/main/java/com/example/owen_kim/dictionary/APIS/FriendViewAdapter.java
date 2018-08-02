package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owen_kim.dictionary.R;
import static com.example.owen_kim.dictionary.MainActivity.uid;
import java.util.ArrayList;

public class FriendViewAdapter extends BaseAdapter {

    Context context;
    ViewHolders viewHolders;
    private ArrayList<friend_item> friend_items = new ArrayList<friend_item>();

    public static CheckBox checkBox;

    public FriendViewAdapter(){

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

    public void addFriend(String friend_id){
        friend_item item = new friend_item(uid,friend_id);

        item.setFriend_id(friend_id);

        friend_items.add(item);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item,null);
            viewHolders = new FriendViewAdapter.ViewHolders();
            viewHolders.name  =(TextView)view.findViewById(R.id.friendIds);
            viewHolders.checkBox = (CheckBox) view.findViewById(R.id.isChecked);
            viewHolders.checkBox.setVisibility(View.GONE);
            checkBox = viewHolders.checkBox;
            view.setTag(viewHolders);
        } else{
            viewHolders = (FriendViewAdapter.ViewHolders)view.getTag();
        }

        viewHolders.name.setText(friend_items.get(position).getFriend_id());

        return view;
    }

    class ViewHolders{
        TextView name;
        CheckBox checkBox;
    }

}
