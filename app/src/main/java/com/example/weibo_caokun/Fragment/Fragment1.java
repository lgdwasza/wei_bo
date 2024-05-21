package com.example.weibo_caokun.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.LoadMoreListenerImp;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.example.weibo_caokun.Activity2;
import com.example.weibo_caokun.Activity3;
import com.example.weibo_caokun.MainActivity;
import com.example.weibo_caokun.NineGridView;
import com.example.weibo_caokun.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;


public class Fragment1 extends Fragment {
    private NewAdapter newAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private int currentPage = 1;
    ArrayList<MultiItemEntity> list = new ArrayList<>();

    public Fragment1() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//View
        View view = inflater.inflate(R.layout.fragment_1, container, false);


        //RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.ry);
        //LayoutManager
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //Adapter
        newAdapter = new NewAdapter(list);
        recyclerView.setAdapter(newAdapter);
        mRefreshLayout = view.findViewById(R.id.refesh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //下拉刷新换页
                currentPage = 1;

                netRes(currentPage);

                mRefreshLayout.setRefreshing(false);
            }
        });
        mRefreshLayout.setOnRefreshListener(this::refreshData);

        newAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //上拉加载
                if (currentPage < 4) {
                    currentPage++;
                    netRes(currentPage);
                } else {
                    newAdapter.getLoadMoreModule().loadMoreEnd();
                    Toast.makeText(getContext(), "无更多内容", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //item点击事件
        //  newAdapter.setOnItemClickListener((adapter, view1, position) -> Toast.makeText(getContext(), "喵", Toast.LENGTH_SHORT).show());
        newAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                if (view.getId() == R.id.p1bt_1 | view.getId() == R.id.p3bt_1 | view.getId() == R.id.p4bt_1) {
                    list.remove(position);
                    newAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.p1iv_4 | view.getId() == R.id.p3bt_3 | view.getId() == R.id.p4bt_3) {
                    Toast.makeText(getContext(), "这是第" + position + "条数据的评论", Toast.LENGTH_SHORT).show();
                }




            /*    if (adapter.getItemViewType(position)==0){
                    if(view.getId()==R.id.p1iv_3){
                        view.findViewById(R.id.p1iv_3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.setBackgroundResource(R.drawable.h );
                            }
                        });
                    }
                    else if (view.getId()==R.id.p1bt_1){
                        list.remove(position);
                    }else if(view.getId()==R.id.p1iv_4){
                        view.findViewById(R.id.p1iv_4).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "这是第"+position+"条数据的评论", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if(view.getId()==R.id.p1iv_2){
                        view.findViewById(R.id.p1iv_2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "这是第1张图，共一张图片", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                }
                else if(adapter.getItemViewType(position)==2) {
                    if(view.getId()==R.id.p3bt_2){
                        view.findViewById(R.id.p3bt_2).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.setBackgroundResource(R.drawable.h );
                            }
                        });
                    }
                    else if (view.getId()==R.id.p3bt_1){
                        list.remove(position);
                    }else if(view.getId()==R.id.p3bt_3){
                        view.findViewById(R.id.p3bt_3).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "这是第"+position+"条数据的评论", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else if(view.getId()==R.id.angv){
                     *//* NineGridView nineGridView= *//*
                 *//*  nineGridView.setOnItemClickListener(new NineGridView.NineOnItemClickListener() {
                          @Override
                          public void onItemClick(View view, int position) {
                              Toast.makeText(getContext(), "这是第"+ position+"张图，共张图片", Toast.LENGTH_SHORT).show();
                          }
                      });*//*

                        NineGridView nineGridView=view.findViewById(R.id.angv);
                              nineGridView.setOnItemClickListener(new NineGridView.NineOnItemClickListener() {

                          @Override
                          public void onItemClick(View view, int position) {
                              Toast.makeText(getContext(), "这是第"+ position+"张图，共张图片", Toast.LENGTH_SHORT).show();
                          }
                      });

                    }


            }
                else if(adapter.getItemViewType(position)==3) {
                    list.remove(position);

                }*/
            }
        });



      /*  newAdapter.setOnItemClickListener((adapter, view1, position) -> Toast.makeText(getContext(), "喵", Toast.LENGTH_SHORT).show());
        newAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (adapter.getItemViewType(position)==0){
                    Intent intent =new Intent(getContext(), Activity3.class);
                    startActivity(intent);
                }
            }
        });*/
        //(item子view点击)点赞
        /*newAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            if (view.getId()==R.id.button_2_1|view.getId()==R.id.button_1_1){
                Toast.makeText(getContext(), "喵", Toast.LENGTH_SHORT).show();
                view.setBackgroundResource(R.drawable.zaned);

            }
            }
        });*/


        //Log.i("msg", "msg= "+itembean.msg);
        /* EventBus.getDefault().register(this);*/

//加载网络
        netRes(1);
        return view;
    }




/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ImageEvent message) {
        //TODO 接收事件后Do something
        Button button =getView().findViewById(R.id.button_2_1);
        button.setBackgroundResource(message.getImage());
    }
*/


    private void netRes(int current) {
        SharedPreferences sp = getContext().getSharedPreferences("get_token", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Log.i("fragment1", "token:" + token);//获取token

        //网络请求
        OkHttpClient client = new OkHttpClient();
        Request request;
        //判断
        if (token != null) {
            request = new Request.Builder()
                    .url("https://hotfix-service-prod.g.mi.com/weibo/homePage?current=" + current + "&size=5")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        } else {

            request = new Request.Builder()
                    .url("https://hotfix-service-prod.g.mi.com/weibo/homePage?current=" + current + "&size=5")
                    .build();
        }


        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Log.i("请求失败", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 成功
                if (response.isSuccessful()) {
                    Log.i("fragment1", "success! CODE=" + response.code());
                    ResponseBody body = response.body();
                    if (body != null) {
                        String content = body.string();
                        Gson gson = new Gson();
                        Log.i("fragment1", "SHUJU:" + content);
                        Itembean itembean = gson.fromJson(content, Itembean.class);
                        if (itembean.code == 403) {
                            Log.i("fragment1", "code ");
                            Intent mainIntent = new Intent(getContext(), Activity3.class);
                            startActivity(mainIntent);
                        } else {

                            //添加数据
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (current > 4) {

                                        if (current >= 4) {
                                            newAdapter.getLoadMoreModule().loadMoreEnd(true); // 标记没有更多内容并立即执行
                                        }
                                    } else {

                                        //list.clear();
                                        Log.i("clear", "run: " + list);
                                        newAdapter.notifyDataSetChanged();
                                        for (int i = 0; i < itembean.data.records.size(); i++) {
                                            if (itembean.data.records.get(i).videoUrl != null) {
                                                //视频
                                                list.add(new Item3(itembean.data.records.get(i).username
                                                        , itembean.data.records.get(i).title
                                                        , itembean.data.records.get(i).videoUrl
                                                        , itembean.data.records.get(i).poster
                                                        , itembean.data.records.get(i).avatar
                                                        , R.drawable.p1_2, R.drawable.p2_2, R.drawable.p3_2
                                                        ,itembean.data.records.get(i).likeCount,itembean.data.records.get(i).likeFlag,itembean.data.records.get(i).id
                                                ));

                                            } else {
                                                //九宫格
                                                if (itembean.data.records.get(i).images.size() > 1) {
                                                    list.add(new Item2(itembean.data.records.get(i).username
                                                            , itembean.data.records.get(i).title
                                                            , itembean.data.records.get(i).images
                                                            , itembean.data.records.get(i).avatar
                                                            , R.drawable.p1_2, R.drawable.p2_2, R.drawable.p3_2
                                                            ,itembean.data.records.get(i).likeCount,itembean.data.records.get(i).likeFlag,itembean.data.records.get(i).id
                                                            ));
                                                } else {
                                                    list.add(new Item(itembean.data.records.get(i).username
                                                            , itembean.data.records.get(i).title
                                                            , itembean.data.records.get(i).images.get(0)
                                                            , itembean.data.records.get(i).avatar
                                                            , R.drawable.p1_2, R.drawable.p2_2, R.drawable.p3_2
                                                            ,itembean.data.records.get(i).likeCount,itembean.data.records.get(i).likeFlag,itembean.data.records.get(i).id));


                                                }
                                            }

                                        }
                                        Collections.shuffle(list);//打乱

                                        newAdapter.notifyDataSetChanged();
                                        mRefreshLayout.setRefreshing(false);
                                        Log.i("page", "run: " + currentPage);
                                        newAdapter.getLoadMoreModule().loadMoreComplete();

                                    }

                                }

                            });

                            Log.i("fragment1", "SHUJU:" + content);
                            //Log.i("fragment1", "SHUJU:" + itembean.data.records.get(1).id);

                        }


                    }

                }


            }
        });


    }

    private void refreshData() {
        currentPage = 1;
        netRes(currentPage);
    }
}


