package com.example.newsAPP.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.channelmanager.ProjectChannelBean;

import java.util.ArrayList;
import java.util.List;

import com.example.newsAPP.R;
import com.example.newsAPP.Utils.CategoryDataUtils;
import com.example.newsAPP.adapter.FixedPagerAdapter;
import com.example.newsAPP.common.DefineView;
import com.example.newsAPP.fragment.trend.TrendListFragment;

import static com.example.newsAPP.R.id.tab_layout;

public class TrendFragment extends BaseFragment implements DefineView {

    private final String TAG = TrendFragment.class.getSimpleName();

    private TabLayout mTabLayout;
    private ViewPager mTrendViewpager;
    private View mView;
    private static  List<ProjectChannelBean> channelBeanList;
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

    public void initView() {
        mTabLayout = (TabLayout) mView.findViewById(tab_layout);
        mTrendViewpager = (ViewPager) mView.findViewById(R.id.news_viewpager);
        mView.findViewById(R.id.change_channel).setVisibility(View.GONE);
        channelBeanList = CategoryDataUtils.getTrendCategoryBeans();
        Toolbar myToolbar = initToolbar(mView, R.id.my_toolbar, R.id.toolbar_title, R.string.comment_home);
        initValidata();
        initListener();
    }

    @Override
    public void initValidata() {
        List<BaseFragment> fragments = new ArrayList<>();
        FixedPagerAdapter fixedPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        //根据预先设置好的目录初始化tabLayout
        for (int i = 0; i < channelBeanList.size(); i++){
            ProjectChannelBean channelBean = channelBeanList.get(i);
            BaseFragment fragment = TrendListFragment.newInstance(channelBean.getTname());
            fragments.add(fragment);
            fixedPagerAdapter.setChannelBean(channelBeanList);
            fixedPagerAdapter.setFragments(fragments);
            mTabLayout.setupWithViewPager(mTrendViewpager);
            mTrendViewpager.setAdapter(fixedPagerAdapter);
        }
    }

    @Override
    public void initListener() {
        mTrendViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void bindData() {

    }
}
