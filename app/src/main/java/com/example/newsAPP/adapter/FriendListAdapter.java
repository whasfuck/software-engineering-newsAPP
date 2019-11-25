package com.example.newsAPP.adapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.newsAPP.R;

@SuppressLint("UseSparseArrays")
public class FriendListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String> mContentsList;
    private Context mContext;
    private ContentsDeleteListener mContentsDeleteListener;
    //设置滑动删除按钮是否显示
    private Map<Integer, Integer> visibleDeleteTv;
    //CheckBox选择和未选择
    private Map<Integer, Boolean> selectCb;
    //滑动后的X坐标点
    private int mLastX = 0;
//	private int mLastY = 0;

    public FriendListAdapter(Context mContext, List<String> mContentsList, ContentsDeleteListener mContentsDeleteListener) {
        this.mContext = mContext;
        this.mContentsList = mContentsList;
        this.mContentsDeleteListener = mContentsDeleteListener;
        this.mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        visibleDeleteTv = new HashMap<Integer, Integer>();
        selectCb = new HashMap<Integer, Boolean>();

        // 更新界面时,记录为 未选中和滑动删除按钮不可见
        for (int i = 0; i < mContentsList.size(); i++) {
            visibleDeleteTv.put(i, View.GONE);
            selectCb.put(i, false);
        }
    }

    public void updateView(List<String> mContentsList) {
        this.mContentsList = mContentsList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mContentsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = mInflater.inflate(R.layout.activity_friend_list_view,
                    null);
            holderView.listSelectCb = (CheckBox) convertView
                    .findViewById(R.id.my_select_cb);
            holderView.listContentTv = (TextView) convertView
                    .findViewById(R.id.my_content_tv);
            holderView.listDeleteTv = (TextView) convertView
                    .findViewById(R.id.my_delete_tv);
            holderView.listRl = (RelativeLayout) convertView
                    .findViewById(R.id.my_rl);

            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
            if (holderView.listSelectCb.isChecked()) {
                holderView.listSelectCb.setChecked(false);
            }

        }
        // 显示内容
        holderView.listContentTv.setText(mContentsList.get(position));

        if (visibleDeleteTv != null) {
            holderView.listDeleteTv
                    .setVisibility(visibleDeleteTv.get(position));
        }
        if (selectCb != null) {
            holderView.listSelectCb.setChecked(selectCb.get(position));
            mContentsDeleteListener.contentsDeleteSelect(position,
                    selectCb.get(position));
        }

        // 处理选择事件
        holderView.listRl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleDeleteTv.containsValue(View.VISIBLE)) {//可见时，再次单击设置不可见，未选中
                    for (int i = 0; i < getCount(); i++) {
                        visibleDeleteTv.put(i, View.GONE);
                        selectCb.put(i, false);
                        mContentsDeleteListener.contentsDeleteSelect(i, false);
                    }
                    notifyDataSetChanged();
                } else {
                    boolean isChecked = holderView.listSelectCb.isChecked() ? false : true;
                    holderView.listSelectCb.setChecked(isChecked);

                    mContentsDeleteListener.contentsDeleteSelect(position, isChecked);
                }
            }
        });

        holderView.listSelectCb
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        mContentsDeleteListener.contentsDeleteSelect(position,
                                isChecked);
                    }
                });

        holderView.listSelectCb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (visibleDeleteTv.containsValue(View.VISIBLE)) {
                    for (int i = 0; i < getCount(); i++) {
                        visibleDeleteTv.put(i, View.GONE);
                        selectCb.put(i, false);
                        mContentsDeleteListener.contentsDeleteSelect(i,
                                false);
                    }
                    notifyDataSetChanged();
                }
            }
        });

        convertView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final Animation alpha = AnimationUtils.loadAnimation(mContext,
                        R.anim.rotate_left);
                int x = (int) event.getX();
//				int y = (int) event.getY();
                // Log.d(TAG, "x=" + x + "  y=" + y);
                // press down
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    alpha.cancel();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    alpha.cancel();
                    int deltaX = mLastX - x;
//					int deltaY = mLastY - y;
                    // Log.d(TAG, "deltaX=" + deltaX + ",deltaY=" + deltaY);
                    if (deltaX > 40) {//当滑动距离大于40时，显示该位置的删除按键
                        for (int i = 0; i < getCount(); i++) {
                            visibleDeleteTv.put(i, View.GONE);
                            selectCb.put(i, false);
                            mContentsDeleteListener.contentsDeleteSelect(i, false);
                            if (i == position) {
                                visibleDeleteTv.put(position, View.VISIBLE);
                                selectCb.put(i, true);
                                mContentsDeleteListener.contentsDeleteSelect(i, true);
                                if (visibleDeleteTv.get(position) == View.VISIBLE) {
                                    holderView.listDeleteTv
                                            .startAnimation(alpha);
                                }
                            }
                        }
                        notifyDataSetChanged();
                    }
                    return true;
                } else {// other
                    alpha.cancel();

                }
                mLastX = x;
//				mLastY = y;
                return false;
            }
        });
        holderView.listDeleteTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Log.d(TAG, "onClick:position=" + position);
                mContentsList.remove(position);
                mContentsDeleteListener.contentDelete(position);
                for (int i = 0; i < mContentsList.size(); i++) {
                    visibleDeleteTv.put(i, View.GONE);
                    selectCb.put(i, false);
                    mContentsDeleteListener.contentsDeleteSelect(i, false);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class HolderView {
        public TextView listContentTv, listDeleteTv;
        public CheckBox listSelectCb;
        public RelativeLayout listRl;
    }

    public void setContentsDeleteListener(
            ContentsDeleteListener mContentsDeleteListener) {
        this.mContentsDeleteListener = mContentsDeleteListener;
    }

    /**
     * 用于删除内容的接口
     *
     * @author liangming.deng
     *
     */
    public interface ContentsDeleteListener {
        /**
         * 根据isChecked 选择和取消选择的指定位置
         * @param position
         * @param isChecked
         */
        public void contentsDeleteSelect(int position, boolean isChecked);
        /**
         * 删除指定位置内容
         * @param position
         */
        public void contentDelete(int position);
    }

    public void setVisibleDeleteTv(Map<Integer, Integer> visibleDeleteTv) {
        this.visibleDeleteTv = visibleDeleteTv;
    }

    public void setSelectCb(Map<Integer, Boolean> selectCb) {
        this.selectCb = selectCb;
    }
}
