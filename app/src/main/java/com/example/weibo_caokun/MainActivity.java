package com.example.weibo_caokun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        boolean hasAgreed = preferences.getBoolean("hasAgreedToPrivacyPolicy", false);

        if (!hasAgreed) {
            // 显示隐私政策界面
            Intent intent = new Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);
        } else {
            // 显示闪屏页面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(MainActivity.this, Activity2.class);
                    startActivity(mainIntent);
                    finish(); // 结束当前闪屏Activity
                }
            }, 3000); // 延时启动
        }


    }
}