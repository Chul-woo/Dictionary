package com.example.owen_kim.dictionary.Requests;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DicRegisterRequest extends StringRequest{

    final static private String URL = "http://133.186.144.151/addMydic.php";
    private Map<String, String> parameters;

    public DicRegisterRequest(String user_id, String eng_word, String img_route, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id",user_id);
        parameters.put("eng_word",eng_word);
        parameters.put("img_route",img_route);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


}
