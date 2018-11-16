package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.owen_kim.dictionary.R;

import java.util.ArrayList;

public class FriendDicAdapter extends BaseAdapter{

    Context context;
    ArrayList<Diclist_item> list_itemArrayList;
    ViewHolder viewholder;

    public FriendDicAdapter(Context context, ArrayList<Diclist_item> list_itemArrayList){
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return list_itemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return list_itemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item,null);
            viewholder = new ViewHolder();
            viewholder.engname_textView = (TextView) view.findViewById(R.id.engname_textview);
            viewholder.dictionary_imageView = (ImageView) view.findViewById(R.id.dictionary_imageview);
            viewGroup.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) viewGroup.getTag();
        }

        viewholder.engname_textView.setText(list_itemArrayList.get(i).getEngname());
        Glide.with(context).load(list_itemArrayList.get(i).getDictionary_image()).into(viewholder.dictionary_imageView);

        return view;
    }

    private class ViewHolder {
        TextView engname_textView;
        ImageView dictionary_imageView;
    }

}
