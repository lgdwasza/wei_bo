package com.example.weibo_caokun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.weibo_caokun.Fragment.Fragment1;
import com.example.weibo_caokun.Fragment.Fragment2;

public class Activity2 extends AppCompatActivity {
private View av2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        av2=findViewById(R.id.activity2);

        Fragment1 fragment1  = new Fragment1();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_1, fragment1,"fragment1")
                .commit();

        ImageView im = (ImageView) findViewById(R.id.i_v_2);
        ImageView im2 = (ImageView) findViewById(R.id.i_v_3);


            Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            av2.startAnimation(fadeInAnimation);
            av2.setVisibility(View.VISIBLE);


        //主页
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                im2.setImageResource(R.drawable.p3);

                im.setImageResource(R.drawable.p2);
                Fragment1 fragment1  = new Fragment1();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout_1, fragment1,"fragment1")
                        .commit();
            }
        });


        //我的页面

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                im2.setImageResource(R.drawable.p6);
                im.setImageResource(R.drawable.p5);


                Fragment2 fragment2  = new Fragment2();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout_1, fragment2,"fragment2")
                        .commit();

            }
        });
    }
}