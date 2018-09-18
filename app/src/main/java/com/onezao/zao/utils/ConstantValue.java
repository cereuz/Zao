package com.onezao.zao.utils;

public class ConstantValue {

    /**
     * 打log使用的tag
     */
    public static final String TAG = "ZAO";
    /**
     *  设置更新的SharedPreferences存储在本地的 文件 名称
     */
    public static final String Zaov_File = "file0815";
    /**
     * 是否开启更新的 key
     */
    public static final String OPEN_UPDATE = "open_update";
    /**
     * 进入界面之前的，设置密码
     */
    public static final String MOBLE_SAFE_PWD = "moble_safe_pwd";
    /**
     * 提示用户密码不能为空
     */
    public static final String NOTICE_PWD = "密码不能为空！";
    /**
     * 密码输入不一致
     */
    public static final String NOTICE_PWD_SAME = "两次密码输入不一致";
    /**
     * 是否设置完成导航界面的key
     */
    public static final String SETUP_OVER = "setup_over";
    /**
     * SIM 卡 绑定序列号的key
     */
    public static final String SIM_NUMBER = "sim_number";
    /**
     * 发送报警短信手机号码的key
     */
    public static final String CONTACT_PHONE = "contact_phone";
    /**
     * 是否开启安全保护的key
     */
    public static final String OPEN_SECURITY = "open_security";
    /**
     * 配置发送邮件的基本信息
     */
    public static final String JAVA_MAIL_SETUP = "java_mail_setup";
    /**
     * 配置发送邮件的信息
     */
    public static final String TV_HOST = "tv_host";
    public static final String TV_PORT = "tv_port";
    public static final String TV_FROM_ADD = "tv_from_add";
    public static final String TV_FROM_PWD = "tv_from_pwd";
    /**
     *  时间常量，4秒钟  即 4000 毫秒
     */
    public static int FOUR_SECOND = 4000;
    public static int SIX_SECOND = 6000;
    public static int ONE_SECOND = 1000;

    /**
     * 日历的数据库
     */
    public static final String DADABASE_DAILYNOTES = "zao0915";
    /**
     * 每日笔记的数据库
     */
    public static final String DATABASE_DAILYNOTES_TABLE_NAME = "dailynotes";
    /**
     * 每日新闻 表
     */
    public static final String DATABASE_CREATE_TABLE_DAILYNOTES = "create table " + DATABASE_DAILYNOTES_TABLE_NAME +
            " (_id integer primary key autoincrement , title varchar(20),author varchar(20),content varchar(10000000),picpath varchar(200),email varchar(30),time varchar(60));";

    public static final String[]  PIC_PATH = {"https://img3.doubanio.com/view/photo/l/public/p2510110513.webp",
            "https://img3.doubanio.com/view/photo/l/public/p2507147220.webp",
            "https://img3.doubanio.com/view/photo/l/public/p1902051195.webp",
            "https://img3.doubanio.com/view/photo/l/public/p1902051371.webp",
            "https://img3.doubanio.com/view/photo/l/public/p1902051511.webp",
            "https://img3.doubanio.com/view/photo/l/public/p1763500663.webp"};

    /**
     * 请求的权限标识
     */
    public static String AUTHORIZATION = "Bearer+eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzI4MjM4MDAzOSIsInVpZCI6IjRlZDcyNGNkMjczNjFmN2ZhNWFlODFhMWFhY2ExZDFmIiwiaWQiOjE2NjMsImV4cCI6MTUzMDU4MjY3MywiY3JlYXRlZCI6MTUyOTk3Nzg3MzA4MH0.PJ8_zIHqRHTLgbHQC8SSNRd8i07fOY5rhNEsgwSbp4ld50BJ6XnATWfxRSO5GFC-AGihHWcw5gAPx4LQfpTxRQ";

}
