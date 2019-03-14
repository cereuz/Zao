package com.onezao.zao.practices.iciba0306;

import com.onezao.zao.utils.LogZ;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/14 13:31
 */

public class Translation {
    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public String show() {
/*        System.out.println(status);

        System.out.println(content.from);
        System.out.println(content.to);
        System.out.println(content.vendor);
        System.out.println(content.out);
        System.out.println(content.errNo);*/
        String text = "status ：" + status + "\ncontent.from :" + content.from  + "\ncontent.to :" + content.to + "\ncontent.vendor :" + content.vendor + "\ncontent.out : "  + content.out + "\ncontent.errNo :" +content.errNo;
        LogZ.e(text);
        return text;
    }
}
