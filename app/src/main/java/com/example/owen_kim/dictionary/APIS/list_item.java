package com.example.owen_kim.dictionary.APIS;

public class list_item {
    //추가한 변수
    private String dictionary_image;
    private String engname;

    public String getDictionary_image() {
        return dictionary_image;
    }

    public void setDictionary_image(String dictionary_image) {
        this.dictionary_image = dictionary_image;
    }

    public String getEngname() {
        return engname;

    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public list_item(String dictionary_image, String engname) {
        this.dictionary_image = dictionary_image;
        this.engname = engname;
    }
}
