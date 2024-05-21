package com.example.weibo_caokun.Fragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Item1 implements MultiItemEntity{
   private String name;
   private String data;
   private String image;
   private String user;
   private int button1;
   private int button2;
   private int button3;


    public Item1() {
    }

    public Item1(String name, String data, String image, String user, int button1, int button2, int button3) {
        this.name = name;
        this.data = data;
        this.image = image;
        this.user = user;
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getButton1() {
        return button1;
    }

    public void setButton1(int button1) {
        this.button1 = button1;
    }

    public int getButton2() {
        return button2;
    }

    public void setButton2(int button2) {
        this.button2 = button2;
    }

    public int getButton3() {
        return button3;
    }

    public void setButton3(int button3) {
        this.button3 = button3;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
