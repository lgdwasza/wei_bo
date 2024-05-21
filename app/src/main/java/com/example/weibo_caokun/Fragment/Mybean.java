package com.example.weibo_caokun.Fragment;

import com.google.gson.annotations.SerializedName;



public class Mybean {


    @SerializedName("code")
    public Integer code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public DataDTO data;


    public static class DataDTO {
        @SerializedName("id")
        public Integer id;
        @SerializedName("username")
        public String username;
        @SerializedName("phone")
        public String phone;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("loginStatus")
        public Boolean loginStatus;
    }
}
