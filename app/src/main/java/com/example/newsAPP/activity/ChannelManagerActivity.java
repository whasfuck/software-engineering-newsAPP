package com.example.newsAPP.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.channelmanager.APPConst;
import com.example.channelmanager.ProjectChannelBean;
import com.example.channelmanager.adapter.ChannelAdapter;
import com.example.channelmanager.base.IChannelType;
import com.example.channelmanager.utils.GridItemDecoration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.newsAPP.R;
import com.example.newsAPP.Utils.SharedPreferenceUtils;


public class ChannelManagerActivity extends BaseActivity implements ChannelAdapter.ChannelItemClickListener{
    private static final String TAG="ChannelManagerActivity";
    private RecyclerView mRecyclerView;
    private ChannelAdapter mRecyclerAdapter;
    private List<ProjectChannelBean> mMyChannelList;
    private List<ProjectChannelBean> mRecChannelList;
    private Context context;
    private int tabposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_manager);
        getIntentData();
        context = this;
        initToolbar();
        mRecyclerView = (RecyclerView) findViewById(com.example.channelmanager.R.id.id_tab_recycler_view);
        GridLayoutManager gridLayout = new GridLayoutManager(context, 4);
        gridLayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                boolean isHeader = mRecyclerAdapter.getItemViewType(position) == IChannelType.TYPE_MY_CHANNEL_HEADER ||
                        mRecyclerAdapter.getItemViewType(position) == IChannelType.TYPE_REC_CHANNEL_HEADER;
                return isHeader ? 4 : 1;
            }
        });
        mRecyclerView.setLayoutManager(gridLayout);
        mRecyclerView.addItemDecoration(new GridItemDecoration(APPConst.ITEM_SPACE));
        initData();
        mRecyclerAdapter = new ChannelAdapter(context, mRecyclerView, mMyChannelList, mRecChannelList, 1, 1);
        mRecyclerAdapter.setChannelItemClickListener(this);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    /**
     * 获取当前tab位置，设置不可移动，并供返回使用
     */
    private void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tabposition = bundle.getInt("TABPOSITION");
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("");
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText("频道管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMyChannelList = new ArrayList<>();
        List<ProjectChannelBean> list = SharedPreferenceUtils.getInstance().getDataList(context,"myChannel",ProjectChannelBean.class);
        for (int i = 0; i < list.size(); i ++){
            ProjectChannelBean projectChannelBean = list.get(i);
            if (i == tabposition){
                projectChannelBean.setTabType(APPConst.ITEM_DEFAULT);
            } else {
                // 判断i是否为0或者1,如果为0设置标题为红色（当前浏览的tab标签），如果为1则设置type为1（不可编辑移动），不为1则type为2
                // type为2表示该标签可供编辑移动
                int type;
                if (i == 0  || i == 1){
                    type = 1;
                } else {
                    type = 2;
                }
                projectChannelBean.setTabType(type);
            }
            mMyChannelList.add(projectChannelBean);
        }
        mRecChannelList = new ArrayList<>();
        List<ProjectChannelBean> moreChannelList = SharedPreferenceUtils.getInstance().getDataList(context,"moreChannel",ProjectChannelBean.class);
        mRecChannelList.addAll(moreChannelList);
    }

    @Override
    protected void onPause() {
        for (ProjectChannelBean projectChannelBean : mMyChannelList) {
            // 将当前模式设置为不可编辑状态
            projectChannelBean.setEditStatus(0);
        }
        SharedPreferenceUtils.getInstance().setDataList(context,"myChannel", mMyChannelList);
        SharedPreferenceUtils.getInstance().setDataList(context,"moreChannel", mRecChannelList);
        super.onPause();
    }

    @Override
    public void finish() {
        mRecyclerAdapter.doCancelEditMode(mRecyclerView);

        for (int i = 0; i < mMyChannelList.size(); i ++) {
            ProjectChannelBean projectChannelBean = mMyChannelList.get(i);
            if (projectChannelBean.getTabType() == 0){
                tabposition = i;
            }
        }
        Intent intent = new Intent();
        intent.putExtra("NewTabPostion", tabposition);
        setResult(789, intent);
        super.finish();
    }

    @Override
    public void onChannelItemClick(List<ProjectChannelBean> list, int position) {

    }
}
