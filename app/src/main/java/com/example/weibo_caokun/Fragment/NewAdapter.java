package com.example.weibo_caokun.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import com.example.weibo_caokun.Activity2;
import com.example.weibo_caokun.Activity3;
import com.example.weibo_caokun.Activity4;
import com.example.weibo_caokun.NineGridView;
import com.example.weibo_caokun.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

public class NewAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> implements LoadMoreModule {
    private Handler handler = new Handler();
    private Runnable updateProgressTask;

    //选择布局类型
    public NewAdapter(@NonNull List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.post1);
        addItemType(1, R.layout.post2);
        addItemType(2, R.layout.post3);
        addItemType(3, R.layout.post4);
        addChildClickViewIds(R.id.p1bt_1);//点击事件（新版本）
        addChildClickViewIds(R.id.p1iv_2);//点击事件（新版本）
        addChildClickViewIds(R.id.p1iv_3);//点击事件（新版本）
        addChildClickViewIds(R.id.p1iv_4);//点击事件（新版本）


        addChildClickViewIds(R.id.p3bt_1);
        addChildClickViewIds(R.id.angv);
        addChildClickViewIds(R.id.p3bt_2);
        addChildClickViewIds(R.id.p3bt_3);


        addChildClickViewIds(R.id.p4bt_1);
        addChildClickViewIds(R.id.p4bt_2);
        addChildClickViewIds(R.id.p4bt_3);

        // addChildClickViewIds(R.id.button_1_1);


    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, MultiItemEntity item) {


        //根据不同布局绑定参数
        switch (item.getItemType()) {
            case 0:
                Item item0 = (Item) item;
                ImageView imageView = holder.getView(R.id.p1iv_1);
                Glide.with(getContext()).load(item0.getUser())
                        .into(imageView);
                ImageView imageView2 = holder.getView(R.id.p1iv_2);
                //宽高比较设置尺寸
                Glide.with(getContext())
                        .asBitmap()
                        .load(item0.getImage())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                int width = resource.getWidth();
                                int height = resource.getHeight();

                                // 根据获取的宽高比例进行动态设置
                                if (width > height) {
                                    Glide.with(getContext()).load(item0.getImage()).override(600, 400).into(imageView2);
                                } else {
                                    Glide.with(getContext()).load(item0.getImage()).override(400, 600).into(imageView2);
                                }
                            }
                        });

                imageView2.setOnClickListener(new View.OnClickListener() {
                    //大图浏览
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "这是第1张图，共一张图片", Toast.LENGTH_SHORT).show();
                        int position = holder.getLayoutPosition();
                        Intent intent=new Intent(getContext(), Activity4.class);
                        intent.putExtra("name",item0.getName());
                        intent.putExtra("icon",item0.getUser());
                        intent.putExtra("image",item0.getImage());
                        List<String> list= Collections.singletonList(item0.getImage());
                        intent.putExtra("imagelist",(Serializable)list );
                        intent.putExtra("index",1);
                        intent.putExtra("total",1);
                        getContext().startActivity(intent);
                    }
                });

                holder.setText(R.id.p1tv_1, item0.getName());
                holder.setText(R.id.p1tv_2, item0.getData());
                //内容不超过6行显示
                TextView textView1 = holder.getView(R.id.p1tv_2);

                textView1.setMaxLines(6);
                if (textView1.getLineCount() > 6) {
                    int count = textView1.getText().toString().length();
                    Log.i("count", "conut= " + count);
                }


                holder.setBackgroundResource(R.id.p1bt_1, item0.getButton1());
                holder.setBackgroundResource(R.id.p1iv_3, item0.getButton2());
                ImageView p1_3 = holder.getView(R.id.p1iv_3);
                p1_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断登录
                        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
                        String token = sp.getString("token", "");
                        if (token != "") {
                            if (item0.getZaned() == false) {
                                Log.i("zan?", "" + item0.getZaned());
                                holder.setText(R.id.p1_zan, item0.getZan() + "");

                                v.setBackgroundResource(R.drawable.h);//点赞
                                animation(v);
                                //发送请求
                                like(item0.getId());
                                item0.setZaned(true);
                            } else if (item0.getZaned() == true) {//取消点赞
                                v.setBackgroundResource(R.drawable.p2_2);
                                holder.setText(R.id.p1_zan, "点赞");
                                unlike(item0.getId());
                                animation2(v);
                                item0.setZaned(false);
                            }
                        } else {
                            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Activity3.class);
                            getContext().startActivity(intent);
                        }

                    }
                });


                holder.setBackgroundResource(R.id.p1iv_4, item0.getButton3());
                break;
            case 1:
                Item1 item1 = (Item1) item;
                ImageView imageView11 = holder.getView(R.id.p2iv_1);
                Glide.with(getContext()).load(item1.getUser())
                        .into(imageView11);
                ImageView imageView12 = holder.getView(R.id.p2iv_2);
                Glide.with(getContext()).load(item1.getImage())
                        .into(imageView12);

                holder.setText(R.id.p2tv_1, item1.getName());
                holder.setText(R.id.p2tv_2, item1.getData());
                holder.setBackgroundResource(R.id.p2bt_1, item1.getButton1());
                holder.setBackgroundResource(R.id.p2bt_2, item1.getButton2());
                holder.setBackgroundResource(R.id.p2bt_3, item1.getButton3());
                break;
            case 2:
                Item2 item2 = (Item2) item;
                ImageView imageView21 = holder.getView(R.id.p3iv_1);
                Glide.with(getContext()).load(item2.getUser())
                        .into(imageView21);
//九宫格

                NineGridView nineGridView = holder.getView(R.id.angv);
                Log.i("list", "list: " + item2.getImage());
                nineGridView.setImageUrls(item2.getImage(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        int imageIndex = nineGridView.indexOfChild(v);
                        int max = item2.getImage().size();
                        Intent intent=new Intent(getContext(), Activity4.class);
                        intent.putExtra("name",item2.getName());
                        intent.putExtra("icon",item2.getUser());
                        intent.putExtra("index",(imageIndex+1));
                        intent.putExtra("total",max);
                        intent.putExtra("image",item2.getImage().get(imageIndex));
                        intent.putExtra("imagelist",(Serializable)item2.getImage());

                        getContext().startActivity(intent);
                        Toast.makeText(getContext(), "点击了第" + position + "条数据的第" + (imageIndex+1) + "张图片，共有" + max + "张图片", Toast.LENGTH_SHORT).show();
                    }
                });


                holder.setText(R.id.p3tv_1, item2.getName());
                holder.setText(R.id.p3tv_2, item2.getData());
                TextView textView3 = holder.getView(R.id.p3tv_2);
                textView3.setMaxLines(6);
                holder.setBackgroundResource(R.id.p3bt_1, item2.getButton1());
                ImageView p3bt_2=holder.getView(R.id.p3bt_2);
                p3bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断登录
                        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
                        String token = sp.getString("token", "");
                        if (token != "") {
                            if (item2.getZaned() == false) {
                                Log.i("zan?", "" + item2.getZaned());
                                holder.setText(R.id.p3_zan, item2.getZan() + "");

                                v.setBackgroundResource(R.drawable.h);//点赞
                                animation(v);
                                //发送请求
                                like(item2.getId());
                                item2.setZaned(true);
                            } else if (item2.getZaned() == true) {//取消点赞
                                v.setBackgroundResource(R.drawable.p2_2);
                                holder.setText(R.id.p3_zan, "点赞");
                                unlike(item2.getId());
                                animation2(v);
                                item2.setZaned(false);
                            }
                        } else {
                            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Activity3.class);
                            getContext().startActivity(intent);
                        }

                    }
                });
                holder.setBackgroundResource(R.id.p3bt_2, item2.getButton2());
                holder.setBackgroundResource(R.id.p3bt_3, item2.getButton3());
                break;


            case 3:
                Item3 item3 = (Item3) item;
                ImageView imageView31 = holder.getView(R.id.p4iv_1);
                Glide.with(getContext()).load(item3.getUser())
                        .into(imageView31);
                holder.setText(R.id.p4tv_1, item3.getName());
                holder.setText(R.id.p4tv_2, item3.getData());
                TextView textView4 = holder.getView(R.id.p4tv_2);
                textView4.setMaxLines(6);
                holder.setBackgroundResource(R.id.p4bt_1, item3.getButton1());
                ImageView p4bt_2=holder.getView(R.id.p4bt_2);
                p4bt_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //判断登录
                        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
                        String token = sp.getString("token", "");
                        if (token != "") {
                            if (item3.getZaned() == false) {
                                Log.i("zan?", "" + item3.getZaned());
                                holder.setText(R.id.p4_zan, item3.getZan() + "");

                                v.setBackgroundResource(R.drawable.h);//点赞
                                animation(v);
                                //发送请求
                                like(item3.getId());
                                item3.setZaned(true);
                            } else if (item3.getZaned() == true) {//取消点赞
                                v.setBackgroundResource(R.drawable.p2_2);
                                holder.setText(R.id.p4_zan, "点赞");
                                unlike(item3.getId());
                                animation2(v);
                                item3.setZaned(false);
                            }
                        } else {
                            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), Activity3.class);
                            getContext().startActivity(intent);
                        }

                    }
                });
                holder.setBackgroundResource(R.id.p4bt_2, item3.getButton2());
                holder.setBackgroundResource(R.id.p4bt_3, item3.getButton3());

                //视频
                SurfaceView imageView32 = holder.getView(R.id.p4vd_1);
                ImageView imageView4 = holder.getView(R.id.full);
                ImageView imageView5 = holder.getView(R.id.imagehead);//封面
                Glide.with(getContext()).load(item3.getUser())
                        .into(imageView5);
                ImageButton start = holder.getView(R.id.start);
                ProgressBar progressBar = holder.getView(R.id.progress_bar);
                String videoUrl = item3.getImage();
                SurfaceHolder sholder = imageView32.getHolder();
                MediaPlayer mPlayer = new MediaPlayer();

                // SurfaceView回调
                sholder.addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(@NonNull SurfaceHolder holder) {
                        Glide.with(getContext()).load(item3.getImage2())
                                .into(imageView5);
                        mPlayer.setDisplay(sholder); // surfaceView 与 MediaPlay进行关联
                        try {
                            // 设置数据源
                            mPlayer.setDataSource(videoUrl);
                            mPlayer.prepare();
                            mPlayer.setLooping(true);//设置是否循环播放
                            start.setOnClickListener(v -> {
                                imageView4.setVisibility(View.VISIBLE);
                                imageView5.setVisibility(View.INVISIBLE);
                                mPlayer.start();
                                start.setBackgroundResource(R.drawable.p8);
                                updateProgressBar(mPlayer, progressBar);
                                start.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.VISIBLE);

                            });
                            imageView32.setOnClickListener(v -> {
                                if (mPlayer.isPlaying()) {
                                    mPlayer.pause();
                                    start.setBackgroundResource(R.drawable.p7);
                                    start.setVisibility(View.VISIBLE);
                                }
                            });
                            imageView4.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            progressBar.setMax(mPlayer.getDuration());
                        } catch (Exception e) {
                            Log.i("F1", "surfaceCreated: ");
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

                    }

                    @Override
                    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                        if (mPlayer != null) {
                            mPlayer.release();
                        }
                    }
                });

                break;


        }
    }

    public static final MediaType jsonMedia = MediaType.parse("application/json;charset=utf-8");
    Gson gson = new Gson();


    private void like(Long id) {
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("id", id);
        String json = gson.toJson(hashMap);
        RequestBody requestBody = RequestBody.create(jsonMedia, json);
        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Log.i("zan", "token:" + token);//获取token

// 构建Request对象
        Request request = new Request.Builder().post(requestBody)
                .addHeader("Authorization", "Bearer " + token)
                .url("https://hotfix-service-prod.g.mi.com/weibo/like/up")
                .build();

// 发送请求

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("like", "Failure ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("like", "success! CODE=" + response.code());
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String content = body.string();
                        Log.i("like", "数据 " + content);
                    }
                }
            }
        });

    }

    private void unlike(Long id) {
        HashMap<String, Long> hashMap = new HashMap<>();
        hashMap.put("id", id);
        String json = gson.toJson(hashMap);
        RequestBody requestBody = RequestBody.create(jsonMedia, json);
        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Log.i("unlike", "token:" + token);//获取token
// 构建Request对象
        Request request = new Request.Builder().post(requestBody)
                .addHeader("Authorization", "Bearer " + token)
                .url("https://hotfix-service-prod.g.mi.com/weibo/like/down")
                .build();

// 发送请求

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("like", "Failure ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("unlike", "success! CODE=" + response.code());
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String content = body.string();
                        Log.i("unlike", "数据 " + content);
                    }
                }
            }
        });
    }

    private void animation(View view) {//点赞动画
        ObjectAnimator rotationYAnimator = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 0f, 360f);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.2f);
        ObjectAnimator scaleXBAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1.2f, 1f);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.2f);
        ObjectAnimator scaleYBAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotationYAnimator).with(scaleXAnimator).with(scaleYAnimator).before(scaleYBAnimator).before(scaleXBAnimator);
        animatorSet.setDuration(1000);


        animatorSet.start();
        Log.i("animation", "animation: ");
    }

    private void animation2(View view) {//取消点赞动画


        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0.8f);
        ObjectAnimator scaleXBAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.8f, 1f);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0.8f);
        ObjectAnimator scaleYBAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.8f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimator).with(scaleYAnimator).before(scaleYBAnimator).before(scaleXBAnimator);
        animatorSet.setDuration(1000);


        animatorSet.start();
        Log.i("animation", "animation: ");
    }

    //进度条
    private void updateProgressBar(MediaPlayer mediaPlayer, ProgressBar progressBar) {
        progressBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            updateProgressTask = () -> updateProgressBar(mediaPlayer, progressBar);
            handler.postDelayed(updateProgressTask, 1000);
        }
    }
}


