package com.example.newsAPP.Utils;

import com.example.channelmanager.ProjectChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 * 上方目录
 */

public class CategoryDataUtils {
    public static List<ProjectChannelBean> getChannelCategoryBeans(){
        List<ProjectChannelBean>  beans=new ArrayList<>();
        beans.add(new ProjectChannelBean("头条"));
        beans.add(new ProjectChannelBean("社会"));
        beans.add(new ProjectChannelBean("国内"));
        beans.add(new ProjectChannelBean("国际"));
        beans.add(new ProjectChannelBean("娱乐"));
        beans.add(new ProjectChannelBean("体育"));
        beans.add(new ProjectChannelBean("军事"));
        beans.add(new ProjectChannelBean("科技"));
        beans.add(new ProjectChannelBean("财经"));
        beans.add(new ProjectChannelBean("时尚"));
        return beans;
    }

    public static List<ProjectChannelBean> getComCategoryBeans(){
        List<ProjectChannelBean> beans = new ArrayList<>();
        beans.add(new ProjectChannelBean("所有"));
        beans.add(new ProjectChannelBean("好友"));
        beans.add(new ProjectChannelBean("我的"));
        return beans;
    }
}
