package com.example.weibo_caokun.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.weibo_caokun.Activity2;
import com.example.weibo_caokun.Activity3;
import com.example.weibo_caokun.R;
import com.google.gson.Gson;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Handler handler = new Handler(Looper.getMainLooper());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   private ImageView imageView2;
  private   TextView textView3;
  private   TextView textView2;
  private   TextView textView4;
  private   TextView textView5;
   private Mybean myBean;
    public Fragment2() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance() {
        Fragment2 fragment = new Fragment2();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        TextView textView1=(TextView)view.findViewById(R.id.f_t_v_2);
        textView2=(TextView)view.findViewById(R.id.f_t_v_3);
        textView3=(TextView)view.findViewById(R.id.textView4);
        textView4=(TextView)view.findViewById(R.id.textView5);
        textView5=(TextView)view.findViewById(R.id.textView3);

        ImageView imageView2 = (ImageView) view.findViewById(R.id.f_i_v_1);


        SharedPreferences sp = getContext().getSharedPreferences("get_token",MODE_PRIVATE);
        String token = sp.getString("token","");

        Log.i("f2", "token:"+token);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://hotfix-service-prod.g.mi.com/weibo/api/user/info")
                .addHeader("Authorization","Bearer "+token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("请求失败",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 成功
                if (response.isSuccessful()) {
                    Log.i("fragment2", "success! CODE=" + response.code());
                    ResponseBody body = response.body();
                    if (body != null) {
                        String content = body.string();
                        Gson gson = new Gson();
                        Log.i("fragment2", "SHUJU:" + content);
                         myBean = gson.fromJson(content, Mybean.class);
                        if(myBean.code==403) {
                            Log.i("fragment1", "code ");
                            Intent mainIntent = new Intent(getContext(), Activity3.class);
                            startActivity(mainIntent);
                        }
                        else {
                            Log.i("fragment2", "SHUJU:" + content);


                            SharedPreferences sp = getContext().getSharedPreferences("get_token",MODE_PRIVATE);
                            String token = sp.getString("token","");

                            if(token!=""){
                                Log.i("denglu", "?"+token);
                                view.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(Fragment2.this)
                                                .load(myBean.data.avatar)
                                                .into(imageView2);
                                        textView2.setVisibility(View.VISIBLE);
                                        textView5.setVisibility(View.INVISIBLE);
                                        textView3.setText(myBean.data.username);
                                        textView4.setVisibility(View.INVISIBLE);
                                        textView2.setEnabled(true);

                                        textView2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                SharedPreferences.Editor editor = getContext().getSharedPreferences("get_token", MODE_PRIVATE).edit();
                                                editor.clear();
                                                editor.apply();
                                                view.post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        textView2.setVisibility(View.INVISIBLE);
                                                        textView5.setVisibility(View.VISIBLE);
                                                        textView4.setVisibility(View.VISIBLE);
                                                        textView3.setText("请先登录");
                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }

                        }




                    }
                }
            }
        });




        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(), "miao", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(getContext(), Activity3.class);
                startActivity(mainIntent);

            }
        });

        return view;
    }


}