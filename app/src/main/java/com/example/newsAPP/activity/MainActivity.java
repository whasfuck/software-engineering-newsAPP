package com.example.newsAPP.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.newsAPP.R;
import com.example.newsAPP.bean.BottomTab;
import com.example.newsAPP.fragment.MineFragment;
import com.example.newsAPP.fragment.TrendFragment;
import com.example.newsAPP.fragment.SearchFragment;
import com.example.newsAPP.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<BottomTab> mBottomTabs = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();
    }

    /**
     * 初始化底部标签栏
     */
    private void initTab() {
        // 新闻标签
        BottomTab bottomTab_news = new BottomTab(NewsFragment.class,R.string.news_fragment,R.drawable.select_icon_news);
        // 搜索标签
        BottomTab bottomTab_find = new BottomTab(SearchFragment.class,R.string.find_fragment,R.drawable.select_icon_video);
        // 评论标签
        BottomTab bottomTab_comment = new BottomTab(TrendFragment.class,R.string.comment_fragment,R.drawable.select_icon_photo);
        // 我的标签
        BottomTab bottomTab_about = new BottomTab(MineFragment.class,R.string.about_fragment,R.drawable.select_icon_about);

        mBottomTabs.add(bottomTab_news);
        mBottomTabs.add(bottomTab_find);
        mBottomTabs.add(bottomTab_comment);
        mBottomTabs.add(bottomTab_about);

        // 设置FragmentTab
        mInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (BottomTab bottomTab : mBottomTabs){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(bottomTab.getTitle()));
            tabSpec.setIndicator(buildIndicator(bottomTab));
            mTabHost.addTab(tabSpec, bottomTab.getFragment(),null);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            }
        });

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    /**
     * 设置底部tab的图片和文字
     */
    private View buildIndicator(BottomTab bottomTab){
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);
        img.setBackgroundResource(bottomTab.getIcon());
        text.setText(bottomTab.getTitle());
        return view;
    }

    /**
     * 调用频道管理功能时使用
     * @param requestCode 请求码
     * @param resultCode 接收码
     * @param data 传递数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String tag =  mTabHost.getCurrentTabTag();
        if (resultCode == 789){
            Bundle bundle = data.getExtras();
            int tabPosition = bundle.getInt("NewTabPostion");
            NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag(tag);
            newsFragment.setCurrentChannel(tabPosition);
            newsFragment.notifyChannelChange();
        }
    }
}
