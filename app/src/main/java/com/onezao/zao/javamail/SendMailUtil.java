package com.onezao.zao.javamail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.SpUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/4/10.
 */

public class SendMailUtil {

/*    //qq
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "teprinciple@foxmail.com";
    private static final String FROM_PSW = "lfrlpganzjrwbeci";*/

/*    //163
    private static final String HOST = "smtp.163.com";
    private static final String PORT = "25"; //或者465  994
    private static final String FROM_ADD = "sunedo@163.com";
    private static final String FROM_PSW = "2008134122";
    private static final String TO_ADD = "sunedo@qq.com";*/


    public static void send(Context context,final File file, String toAdd){
        final MailInfo mailInfo = creatMail(context,toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }


    public static void send(Context context,String toAdd){
        final MailInfo mailInfo = creatMail(context,toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    public static void send(Context context,String toAdd,boolean b){
        final MailInfo mailInfo = creatMail(context,toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendHtmlMail(mailInfo);
            }
        }).start();
    }


        @NonNull
    private static MailInfo creatMail(Context context, String toAdd) {

        String HOST = SpUtils.getString(context,ConstantValue.Zaov_File,ConstantValue.TV_HOST,"");
        String PORT = SpUtils.getString(context,ConstantValue.Zaov_File,ConstantValue.TV_PORT,"");
        String FROM_ADD = SpUtils.getString(context,ConstantValue.Zaov_File,ConstantValue.TV_FROM_ADD,"");
        String FROM_PSW = SpUtils.getString(context,ConstantValue.Zaov_File,ConstantValue.TV_FROM_PWD,"");

        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject("逍遥游  庄子  " + MailUtils.getTime()); // 邮件主题
        mailInfo.setContent(MailUtils.xyywords + "\n 听风者无耳，千里眼无目，悲喜交加的时候是个真实的梦靥；\n"+ MailUtils.getTime()); // 邮件文本
        return mailInfo;
    }

/*    @NonNull
    private static MailInfo creatMail(String toAdd) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮件去
        mailInfo.setSubject("逍遥游  庄子  " + MailUtils.getTime()); // 邮件主题
        mailInfo.setContent(MailUtils.xyywords + "\n 听风者无耳，千里眼无目，悲喜交加的时候是个真实的梦靥；\n"+ MailUtils.getTime()); // 邮件文本
        return mailInfo;
    }*/
}
