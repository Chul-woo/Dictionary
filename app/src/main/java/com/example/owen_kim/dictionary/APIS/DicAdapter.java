package com.example.owen_kim.dictionary.APIS;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.owen_kim.dictionary.FriendListActivity;
import com.example.owen_kim.dictionary.R;
import com.example.owen_kim.dictionary.Requests.DeleteDicRequest;
import com.example.owen_kim.dictionary.Requests.DeleteFriendRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.ERROR;

public class DicAdapter extends BaseAdapter{

    Context context;
    ArrayList<Diclist_item> list_itemArrayDiclist;
    ViewHolder viewholder;
    TextToSpeech tts;

    public DicAdapter(Context context, ArrayList<Diclist_item> list_itemArrayDiclist) {
        this.context = context;
        this.list_itemArrayDiclist = list_itemArrayDiclist;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayDiclist.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayDiclist.get(position);
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
            viewholder.delete_button = (Button)convertView.findViewById(R.id.delete_button);
            viewholder.speaker = (ImageButton)convertView.findViewById(R.id.speaker);
            convertView.setTag(viewholder);
        } else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        viewholder.engname_textView.setText(list_itemArrayDiclist.get(position).getEngname());
        viewholder.speaker.setTag(position);
        viewholder.delete_button.setTag(position);

        tts = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.ENGLISH);

                }
            }
        });

        viewholder.speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = Integer.parseInt( (view.getTag().toString()) );
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.3f);
                tts.speak(list_itemArrayDiclist.get(position).getEngname(),TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        viewholder.delete_button.setOnClickListener(new View.OnClickListener() {
            int position, id;
            String eng_name;
            @Override
            public void onClick(View view) {
                position = Integer.parseInt( (view.getTag().toString()) );
                eng_name = list_itemArrayDiclist.get(position).getEngname();
                id = list_itemArrayDiclist.get(position).getId();

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Log.i("2: ", "2번");
                            //Toast.makeText(context, String.valueOf(success), Toast.LENGTH_SHORT).show();
                            if(success){
                                Toast.makeText(context, eng_name+ "이(가) 삭제되었습니다", Toast.LENGTH_SHORT).show();
                                list_itemArrayDiclist.remove(position);
                                notifyDataSetChanged();
                            }else{
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteDicRequest deleteDicRequest = new DeleteDicRequest(id+"", listener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteDicRequest);
            }
        });

        Glide.with(context).load(list_itemArrayDiclist.get(position).getDictionary_image()).into(viewholder.dictionary_imageView);

        return convertView;
    }

    class ViewHolder{
        TextView engname_textView;
        ImageView dictionary_imageView;
        Button delete_button;
        ImageButton speaker;
    }
}
