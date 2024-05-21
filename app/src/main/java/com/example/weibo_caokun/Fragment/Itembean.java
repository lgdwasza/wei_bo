package com.example.weibo_caokun.Fragment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Itembean {
    @SerializedName("code")
    public Integer code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public DataDTO data;


    public static class DataDTO {
        @SerializedName("records")
        public List<RecordsDTO> records;
        @SerializedName("total")
        public Integer total;
        @SerializedName("size")
        public Integer size;
        @SerializedName("current")
        public Integer current;
        @SerializedName("pages")
        public Integer pages;


        public static class RecordsDTO {
            @SerializedName("id")
            public Integer id;
            @SerializedName("userId")
            public Integer userId;
            @SerializedName("username")
            public String username;
            @SerializedName("phone")
            public String phone;
            @SerializedName("avatar")
            public String avatar;
            @SerializedName("title")
            public String title;
            @SerializedName("videoUrl")
            public String videoUrl;
            @SerializedName("poster")
            public String poster;
            @SerializedName("images")
            public List<String> images;
            @SerializedName("likeCount")
            public Integer likeCount;
            @SerializedName("likeFlag")
            public Boolean likeFlag;
            @SerializedName("createTime")
            public String createTime;
        }
    }
}
