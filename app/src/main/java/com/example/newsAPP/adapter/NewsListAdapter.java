package com.example.newsAPP.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.irecyclerview.IViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.example.newsAPP.R;
import com.example.newsAPP.bean.NewsBean;

import org.jetbrains.annotations.NotNull;

/**
 * 新闻列表适配器
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private final String TAG = NewsListAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<NewsBean.DataBean> mNewsBeanList;
    private OnItemClickListener mOnItemClickListener;


    public NewsListAdapter(Context context, ArrayList<NewsBean.DataBean> newsBeanList) {
        mContext = context;
        mNewsBeanList = newsBeanList;
    }

    @NotNull
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(mContext, R.layout.item_news, null);
        return new ViewHolder(view);
    }


    //
    @Override
    public void onBindViewHolder(@NotNull final NewsListAdapter.ViewHolder holder, int position) {
        NewsBean.DataBean bean = mNewsBeanList.get(position);
        String imageSrc = bean.getPicture();
        String title = bean.getTitle();
        String source = bean.getAuthor();
        String postTime = bean.getTime();
        //刨去默认新闻
        if (!title.equals("null")){
            // 设置图片
            setNetPicture(imageSrc, holder.item_news_tv_img);
            holder.item_news_tv_title.setText(title);
            holder.item_news_tv_time.setText(postTime);
            holder.item_news_tv_source.setText(source);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IRecyclerView的Adapter会默认多出两个头部View，需要减去2个position
                int pos = holder.getIAdapterPosition();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if(mNewsBeanList!=null) {
            return mNewsBeanList.size();
        }
        return -1;
    }

    /**
     * 设置图片
     * @param url 图片url
     * @param img imageView
     */
    private void setNetPicture(String url, ImageView img) {
        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.defaultbg)
                .into(img);
    }

    /**
     * 设置Item点击监听
     * @param listener 监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View v, int position);
    }

    class ViewHolder extends IViewHolder {
        TextView item_news_tv_title;
        TextView item_news_tv_time;
        TextView item_news_tv_arrow;
        TextView item_news_tv_source;
        ImageView item_news_tv_img;

        ViewHolder(View itemView) {
            super(itemView);
            item_news_tv_title = (TextView) itemView.findViewById(R.id.item_news_tv_title);
            item_news_tv_time = (TextView) itemView.findViewById(R.id.item_news_tv_time);
            item_news_tv_arrow = (TextView) itemView.findViewById(R.id.item_news_tv_arrow);
            item_news_tv_source = (TextView) itemView.findViewById(R.id.item_news_tv_source);
            item_news_tv_img = (ImageView) itemView.findViewById(R.id.item_news_tv_img);
        }
    }
}
