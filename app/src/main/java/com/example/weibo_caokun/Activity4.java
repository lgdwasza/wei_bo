package com.example.weibo_caokun;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.PathUtils;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Activity4 extends AppCompatActivity  {
    private static final String TAG = "BigImage";
    private ViewGroup big;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_4);
        big = findViewById(R.id.big);
        fadeIn();
        ImageView icon = findViewById(R.id.big_icon);

        TextView user = findViewById(R.id.big_name);
        TextView index = findViewById(R.id.big_count);
        TextView download = findViewById(R.id.big_download);
        String username = getIntent().getStringExtra("name");
        String imageUrl = getIntent().getStringExtra("image");
        String iconUrl = getIntent().getStringExtra("icon");
        int indexCount = getIntent().getIntExtra("index",0);
        int total = getIntent().getIntExtra("total",0);
        List<String> imageUrls = getIntent().getStringArrayListExtra("imagelist");
       ViewPager2 viewPager = findViewById(R.id.view_pager);

        ImagePagerAdapter adapter = new ImagePagerAdapter(imageUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(indexCount-1);
        index.setText(indexCount+"/"+total);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                index.setText((position+1)+"/"+total);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Glide.with(Activity4.this)
                                .asBitmap()
                                .load(imageUrls.get(position))
                                .into(new CustomTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        saveImage(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(Drawable placeholder) {
                                    }
                                });
                    }
                });
            }
        });


       /* Glide.with(this)
                .load(imageUrl)
                .into(image);*/
        Glide.with(this)
                .load(iconUrl)
                .into(icon);
        user.setText(username);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(Activity4.this)
                        .asBitmap()
                        .load(imageUrls)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                saveImage(resource);
                            }

                            @Override
                            public void onLoadCleared(Drawable placeholder) {
                            }
                        });
            }
        });




    }

    // 将Bitmap保存到相册
    private void saveImage(Bitmap bitmap) {
        String fileName = "my_image.jpg";
        String folderName = "MyImages";
        String savePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName;
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imageFile = new File(file, fileName);
        try {
            OutputStream fOut = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.close();
            // 通知系统相册更新
            MediaStore.Images.Media.insertImage(getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), imageFile.getName());
            Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void fadeIn() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        big.startAnimation(fadeInAnimation);
        big.setVisibility(View.VISIBLE);
    }




}