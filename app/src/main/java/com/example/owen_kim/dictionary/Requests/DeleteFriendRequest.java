package com.example.owen_kim.dictionary.Requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteFriendRequest extends StringRequest {
    final static private String URL = "http://133.186.144.151/deleteFriends1.php";
    private Map<String, String> parameters;
    public DeleteFriendRequest(String user_id, String friend_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_id", user_id);
        parameters.put("friend_id",friend_id);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
