package com.example.owen_kim.dictionary.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest{

    final static private String URL = "http://133.186.144.151/login.php";
    private Map<String, String> parameters;

    public LoginRequest(String user_id, String user_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id",user_id);
        parameters.put("user_pw",user_pw);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
