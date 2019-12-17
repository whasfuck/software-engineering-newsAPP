package com.example.newsAPP.common;

public class DatabaseApi {
    public static final String host = "http://47.100.79.111:8080/";
    //新闻列表
    public static final String newsList = "getNews";
    public static final String getFollow = "getFriend";
    public static final String getAllTrend = "getAllTrend";
    public static final String getFriendTrend = "getFriendTrend";
    public static final String getMyTrend = "getMyTrend";
    public static final String getFans = "getFans";
    public static final String follow = "insertInterest";
    public static final String unfollow = "deleteFriend";
    public static final String ban = "cancelInterest";
    public static final String isFollow = "judgeFriend";
}