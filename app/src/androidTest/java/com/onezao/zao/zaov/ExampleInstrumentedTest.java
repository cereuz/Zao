package com.onezao.zao.zaov;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.onezao.zao.bean.DailyNotesInfo;
import com.onezao.zao.database.DailyNotesDao;
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.ZaoUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void useAppContext() {
        // Context of the app under test.


        assertEquals("com.onezao.zao.zaov", appContext.getPackageName());
    }

    /**
     * 添加测试数据
     */
    @Test
    public void testAddNotesData() {
            DailyNotesDao mDao = DailyNotesDao.getInstance(appContext);
            for(int i = 0 ; i < 100 ; i++){
                String title;
                String author ;
                String content;
                String picpath;
                String email ;
                String time;
                if(i < 10){
                    title = "1328238000" + i;
                    author = "蜗牛" + i;
                    content = ConstantValue.AUTHORIZATION;
                    picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                    email = "sune"+ i +"do@qq.com" ;
                    time = ZaoUtils.getSystemTimeMore(1);
                    //往数据库插入数据
                    mDao.insert(title, author,content,picpath,email,time);
                    ZaoUtils.sleep(20);
                }  else if(i < 100){
                    title = "132823800" + i;
                    author = "蜗牛" + i;
                    content = ConstantValue.AUTHORIZATION;
                    picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                    email = "sune"+ i +"do@qq.com" ;
                    time = ZaoUtils.getSystemTimeMore(1);
                    //往数据库插入数据
                    mDao.insert(title, author,content,picpath,email,time);
                    //睡眠，
                    ZaoUtils.sleep(20);
                }  else if (i < 1000){
                    title = "132823800" + i;
                    author = "蜗牛" + i;
                    content = ConstantValue.AUTHORIZATION;
                    picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                    email = "sune"+ i +"do@qq.com" ;
                    time = ZaoUtils.getSystemTimeMore(1);
                    //往数据库插入数据
                    mDao.insert(title, author,content,picpath,email,time);
                    ZaoUtils.sleep(10);
                }
            }

            //黑名单的数据库
            ZaoUtils.copyDBtoSD(appContext);
        }

    /**
     * 添加测试数据
     */
    @Test
    public void testDailyNotesDelete() {
        DailyNotesDao mDao = DailyNotesDao.getInstance(appContext);
        mDao.delete("13282380014");
        mDao.delete("13282380034");
        mDao.delete("13282380035");
        //黑名单的数据库
        ZaoUtils.copyDBtoSD(appContext);
    }

    /**
     * AppLock的数据库数据的查询操作
     */
    @Test
    public void testDailyNotesFindAll(){
        DailyNotesDao dao = DailyNotesDao.getInstance(appContext);
        List<DailyNotesInfo> list = dao.findAll();
        for(DailyNotesInfo info : list){
            Log.i("Zao","Title = " + info.getTitle() + " Author = " + info.getAuthor() + " Content = " + info.getContent() + " Email = " + info.getEmail() + " Picpath = " + info.getPicpath() + " Time = " + info.getTime() );
        }
    }
}
