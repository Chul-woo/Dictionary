package com.example.owen_kim.dictionary.APIS;

public class Diclist_item {
    //추가한 변수
    private String dictionary_image;
    private String engname;
    private int id;

    public String getDictionary_image() {
        return dictionary_image;
    }

    public void setDictionary_image(String dictionary_image) {
        this.dictionary_image = dictionary_image;
    }

    public String getEngname() {
        return engname;
    }
    public int getId() {
        return id;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public Diclist_item(String dictionary_image, String engname, int id) {
        this.dictionary_image = dictionary_image;
        this.engname = engname;
        this.id = id;
    }
}
