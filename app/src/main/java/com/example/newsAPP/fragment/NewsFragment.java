package com.example.newsAPP.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.channelmanager.APPConst;
import com.example.channelmanager.ProjectChannelBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.newsAPP.R;
import com.example.newsAPP.Utils.CategoryDataUtils;
import com.example.newsAPP.Utils.SharedPreferenceUtils;
import com.example.newsAPP.activity.ChannelManagerActivity;
import com.example.newsAPP.adapter.FixedPagerAdapter;
import com.example.newsAPP.common.DefineView;
import com.example.newsAPP.fragment.news.NewsListFragment;

import static com.example.newsAPP.R.id.tab_layout;

public class NewsFragment extends BaseFragment implements DefineView {

    private final String TAG = NewsFragment.class.getSimpleName();
    private TabLayout mTabLayout;
    private ViewPager mNewsViewpager;
    private View mView;
    private FixedPagerAdapter fixedPagerAdapter;
    private List<BaseFragment> fragments;
    private List<ProjectChannelBean> myChannelList;
    private List<ProjectChannelBean> moreChannelList;
    private ImageButton mChange_channel;
    // 当前新闻频道的位置
    private int tabPosition;
    private boolean isFirst;
    private BaseFragment baseFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.tablayout_pager, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initView() {
        mTabLayout = (TabLayout) mView.findViewById(tab_layout);
        mNewsViewpager = (ViewPager) mView.findViewById(R.id.news_viewpager);
        mChange_channel = (ImageButton) mView.findViewById(R.id.change_channel);
        Toolbar myToolbar = initToolbar(mView, R.id.my_toolbar, R.id.toolbar_title, R.string.news_home);
        initValidata();
        initListener();
    }

    @Override
    public void initValidata() {
        //listDataSave = new ListDataSave(getActivity(), "channel");
        fragments = new ArrayList<>();
        fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mTabLayout.setupWithViewPager(mNewsViewpager);
        bindData();
    }

    @Override
    public void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        mChange_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChannelManagerActivity.class);
                intent.putExtra("TABPOSITION", tabPosition);
                startActivityForResult(intent, 999);
            }
        });
    }

    @Override
    public void bindData() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);
        fixedPagerAdapter.setFragments(fragments);
        mNewsViewpager.setAdapter(fixedPagerAdapter);
    }

    /**
     * 判断是否第一次进入程序
     * 如果第一次进入，直接获取设置好的频道
     * 如果不是第一次进入，则从sharedPrefered中获取设置好的频道
     */
    private void getDataFromSharedPreference() {
        myChannelList = CategoryDataUtils.getChannelCategoryBeans();
        moreChannelList = CategoryDataUtils.getMoreCategoryBeans();
        myChannelList = setType(myChannelList);
        moreChannelList = setType(moreChannelList);
//        isFirst = (boolean)SharedPreferenceUtils.getInstance().get(getActivity(),"isFirst", true);
//        if (isFirst) {
//            myChannelList = CategoryDataUtils.getChannelCategoryBeans();
//            moreChannelList = CategoryDataUtils.getMoreCategoryBeans();
//            myChannelList = setType(myChannelList);
//            moreChannelList = setType(moreChannelList);
//            SharedPreferenceUtils.getInstance().setDataList(getContext(),"myChannel", myChannelList);
//            SharedPreferenceUtils.getInstance().setDataList(getContext(),"moreChannel", myChannelList);
//            SharedPreferenceUtils.getInstance().put(getActivity(),"isFirst", false);
//        } else {
//            SharedPreferenceUtils.getInstance().getDataList(getContext(),"moreChannel",ProjectChannelBean.class);
//        }
        fragments.clear();
        for (int i = 0; i < myChannelList.size(); i++) {
            baseFragment = NewsListFragment.newInstance(myChannelList.get(i).getTname());
            fragments.add(baseFragment);
        }
        if (myChannelList.size() <= 4) {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    /**
     * 在MainActivity中被调用，当从ChanelActivity返回时设置当前tab的位置
     * @param tabPosition
     */
    public void setCurrentChannel(int tabPosition) {
        mNewsViewpager.setCurrentItem(tabPosition);
        mTabLayout.setScrollPosition(tabPosition, 1, true);
    }

    /**
     * 在myChannelList发生改变的时候更新ui，在MainActivity调用
     */
    public void notifyChannelChange() {
        getDataFromSharedPreference();
        fixedPagerAdapter.setChannelBean(myChannelList);
        fixedPagerAdapter.setFragments(fragments);
        fixedPagerAdapter.notifyDataSetChanged();
    }

    private List<ProjectChannelBean> setType(List<ProjectChannelBean> list) {
        Iterator<ProjectChannelBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            ProjectChannelBean channelBean = iterator.next();
            channelBean.setTabType(APPConst.ITEM_EDIT);
        }
        return list;
    }
}
