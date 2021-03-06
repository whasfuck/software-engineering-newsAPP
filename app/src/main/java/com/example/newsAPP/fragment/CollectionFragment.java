package com.example.newsAPP.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.example.newsAPP.MyApplication;
import com.example.newsAPP.R;
import com.example.newsAPP.Utils.DensityUtils;
import com.example.newsAPP.Utils.HttpUtils;
import com.example.newsAPP.Utils.SharedPreferenceUtils;
import com.example.newsAPP.activity.NewsDetailActivity;
import com.example.newsAPP.adapter.NewsListAdapter;
import com.example.newsAPP.bean.NewsBean;
import com.example.newsAPP.common.DatabaseApi;
import com.example.newsAPP.common.GetnewsApi;
import com.example.newsAPP.http.DataParse;
import com.example.newsAPP.http.OkHttp;
import com.example.newsAPP.widget.ClassicRefreshHeaderView;
import com.example.newsAPP.widget.DividerGridItemDecoration;
import com.example.newsAPP.widget.LoadMoreFooterView;

import java.util.ArrayList;

/**
 * 收藏列表
 */
public class CollectionFragment extends BaseFragment {
    private final String TAG = MineFragment.class.getSimpleName();
    private View mView;
    private IRecyclerView mIRecyclerView;
    private LoadMoreFooterView mLoadMoreFooterView;
    private NewsListAdapter mNewsListAdapter;
    private ArrayList<NewsBean.DataBean> mNewsBeanList = new ArrayList<>();
    private String userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_collection_list, container,false);
        userID = SharedPreferenceUtils.getInstance().getString(getActivity(),"USERID",null);
        initView();
        initValidata();
        initListener();
        return mView;
    }

    @Override
    public void initView() {
        mIRecyclerView = mView.findViewById(R.id.iRecyclerView);
        mIRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIRecyclerView.addItemDecoration(new DividerGridItemDecoration(this.getActivity()));
        mLoadMoreFooterView = (LoadMoreFooterView) mIRecyclerView.getLoadMoreFooterView();
        ClassicRefreshHeaderView classicRefreshHeaderView = new ClassicRefreshHeaderView(getActivity());
        classicRefreshHeaderView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(getActivity(), 80)));
        mIRecyclerView.setRefreshHeaderView(classicRefreshHeaderView);
    }

    @Override
    public void initValidata() {
        new CollectionAsyncTask().execute("头条");
    }

    @Override
    public void initListener() {
        mIRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        mIRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mLoadMoreFooterView.canLoadMore() && mNewsListAdapter.getItemCount() > 0) {
                }
            }
        });
    }

    @Override
    public void bindData() {
        mNewsListAdapter = new NewsListAdapter(MyApplication.getContext(), mNewsBeanList);
        mIRecyclerView.setIAdapter(mNewsListAdapter);
        // 设置Item点击跳转事件
        mNewsListAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NewsBean.DataBean bean = mNewsBeanList.get(position);
                Intent intent;
                intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("URL", bean.getUrl());
                intent.putExtra("NEWSID", bean.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 获取收藏列表异步
     */
    class CollectionAsyncTask extends AsyncTask<String,Integer,ArrayList<NewsBean.DataBean>> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected ArrayList<NewsBean.DataBean> doInBackground(String... strings) {
            ArrayList<NewsBean.DataBean> list = new HttpUtils().getCollect(userID);
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsBean.DataBean> list) {
            super.onPostExecute(list);
            mNewsBeanList = list;
            bindData();
        }
    }
}
