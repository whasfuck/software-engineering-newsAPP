package com.example.newsAPP.common;

/**
 * 当前类注释:所有的Activity,Fragment可以实现这个接口，来进行一些公共的操作
 */
public interface DefineView {
	
   public void initView();  //初始化界面元素
   public void initValidata();  //初始化变量
   public void initListener();  //初始化监听器
   public void bindData();       //绑定数据
   
}
