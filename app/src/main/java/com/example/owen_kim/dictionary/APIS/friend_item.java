package com.example.owen_kim.dictionary.APIS;

public class friend_item {

    private String user_id;
    private String friend_id;

    public friend_item(String s, String friend_id) {
        user_id = s;
        this.friend_id = friend_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }


}
