package com.onezao.zao.zaov;

import java.util.Set;

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

}
