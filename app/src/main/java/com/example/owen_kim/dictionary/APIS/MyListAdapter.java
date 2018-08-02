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

public class MyListAdapter extends BaseAdapter{

    Context context;
    ArrayList<list_item> list_itemArrayList;
    ViewHolder viewholder;

    public MyListAdapter(Context context, ArrayList<list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
            viewholder = new ViewHolder();
            viewholder.engname_textView  =(TextView)convertView.findViewById(R.id.engname_textview);
            viewholder.dictionary_imageView = (ImageView)convertView.findViewById(R.id.dictionary_imageview);
            convertView.setTag(viewholder);
        } else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.engname_textView.setText(list_itemArrayList.get(position).getEngname());

        Glide.with(context).load(list_itemArrayList.get(position).getDictionary_image()).into(viewholder.dictionary_imageView);

        return convertView;
    }

    class ViewHolder{
        TextView engname_textView;
        ImageView dictionary_imageView;
    }
}
