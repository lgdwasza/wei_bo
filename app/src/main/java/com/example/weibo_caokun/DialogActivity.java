package com.example.weibo_caokun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dialog dialog = new Dialog(this, R.style.transparent_activity);
        dialog.setContentView(R.layout.activity_dialog);
        dialog.show();
        Button button =dialog.findViewById(R.id.d_b);
        TextView textView=dialog.findViewById(R.id.d_t);
        TextView textView2=dialog.findViewById(R.id.d_t_1);

        //富文本
        SpannableString spannableString = new SpannableString("欢迎使用 iH微博 ，我们将严格遵守相关法律和隐私政策保护您的个人隐私，请您阅读并同意《用户协议》与《隐私政策》");
        //颜色

        //链接

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(DialogActivity.this,"打开用户协议",Toast.LENGTH_SHORT).show();

            }
        },43,49,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(DialogActivity.this,"打开隐私政策",Toast.LENGTH_SHORT).show();

            }
        },50,56,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.royalblue)),43,49, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.royalblue)),50,56, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        textView2.setText(spannableString);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAgreedToPrivacyPolicy();
                Intent mainIntent = new Intent(DialogActivity.this, Activity2.class);
                startActivity(mainIntent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0); // 强制关闭应用
            }
        });

        }

    void onUserAgreedToPrivacyPolicy() {
        SharedPreferences preferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("hasAgreedToPrivacyPolicy", true);
        editor.apply();
        //判断是否接收
    }

}