package com.example.weibo_caokun;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.Http2Reader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Activity3 extends AppCompatActivity implements TextWatcher {
    private TextView textView;
    private TextView textView2;
    private EditText editText;
    private EditText editText2;
    private Button button;
    private String token;
    Gson gson = new Gson();
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .build();
    private static final String TAG = "Activity3";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg==obtainMessage()) {
                Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
         textView =findViewById(R.id.textView10);
         textView2 =findViewById(R.id.f_t_v_2);
        editText=findViewById(R.id.editText2);
         editText2=findViewById(R.id.editText3);
         button=findViewById(R.id.button);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);



       //按钮监听
        editText.addTextChangedListener(this);
        editText2.addTextChangedListener(this);
        button.setEnabled(false);
        button.setAlpha(0.5f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Activity3.this, "1", Toast.LENGTH_SHORT).show();
                Login();
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //判断是否手机号是否为11位
                if (editText.getText().length()==11) {
                    //验证码计时
                    textView.setEnabled(false);
                    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            textView.setTextColor(getColor(R.color.grey));
                            textView.setText("获取验证码 (" + millisUntilFinished / 1000 + "s)");
                        }

                        @Override
                        public void onFinish() {

                            textView.setEnabled(true);
                            textView.setText("发送验证码");
                            textView.setTextColor(getColor(R.color.myblue));
                        }
                    }.start();


                    String i =editText.getText().toString();
                    phone(i);
                } else {
                    Toast.makeText(Activity3.this, "请输入完整的手机号", Toast.LENGTH_SHORT).show();
                }

            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Activity3.this, Activity2.class);
                startActivity(mainIntent);
            }
        });


    }



    //验证码
    public static final MediaType jsonMedia=MediaType.parse("application/json;charset=utf-8");
    public void phone(String phone){

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("phone",phone);
        String json=gson.toJson(hashMap);
        RequestBody requestBody=RequestBody.create(jsonMedia,json);

// 构建Request对象
        Request request = new Request.Builder().post(requestBody)
                .addHeader("content-type","application/json")
                .url("https://hotfix-service-prod.g.mi.com/weibo/api/auth/sendCode")
                .build();

// 发送请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "Failure ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "success! CODE=" + response.code());
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String content = body.string();
                        Log.i(TAG, "数据 " + content);
                    }
                }
            }
        });
    }



    private void Login() {
        final String phone = editText.getText().toString();
        final String smscode = editText2.getText().toString();

        OkHttpClient client = new OkHttpClient();
        final JSONObject jsonObject = new JSONObject();

        try {//提交的参数
            jsonObject.put("phone", phone);
            jsonObject.put("smsCode", smscode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType mMediaType = MediaType.parse("application/json; charset=utf-8");
        final RequestBody requestBody = RequestBody.create(mMediaType, jsonObject.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url("https://hotfix-service-prod.g.mi.com/weibo/api/auth/login")
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
                Log.i("请求情况：", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    Log.i("响应状态", "响应成功");
                    String content = body.string();
                    Gson gson = new Gson();
                    LoginBean loginBean = gson.fromJson(content, LoginBean.class);
                    Log.i(TAG, "onResponse: "+content);
                    String loginResultCode = loginBean.getCode();
                    String resultdata=loginBean.getMsg();
                    String resultdata2=loginBean.getData();

                    Log.i("返回状态码", loginResultCode);
                    Log.i("msg", resultdata);
                    Log.i("data", resultdata2);
                    //响应成功,判断状态码
                    if (loginResultCode.equals("200")) {
                        Log.i("登录状态", "登录成功");
                        //获取token
                        token = loginBean.getData();
                        Log.i(TAG, "t"+token);
                        // 把token保存到本地
                        SharedPreferences.Editor editor = getSharedPreferences("get_token", MODE_PRIVATE).edit();
                        editor.putString("token", token);
                        editor.putString("phone", phone);
                        editor.putString("smscode", smscode);
                        editor.apply();

                        //保存token

                        Message message = handler.obtainMessage();
                        message.obj = token;
                        handler.sendMessage(message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
                            }
                        });
                        startActivity(new Intent(Activity3.this, Activity2.class));
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run: ok");
                            }
                        });
                    }
                }

            }

        });


    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (editText.getText().length()==11 && (editText2.getText().length()==6)){
            button.setEnabled(true);//设置按钮
            button.setAlpha(1);
        }
        else
        {
            button.setEnabled(false);
        }


    }
}



