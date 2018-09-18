package com.onezao.zao.practices.myzxing0306;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.onezao.zao.practices.myzxing0306.zxing.encode.EncodingHandler;
import com.onezao.zao.zaov.R;
import com.onezao.zao.utils.ZaoUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CreateCodeActivity extends Activity implements View.OnClickListener{
    EditText etCodeKey;
    Button btnCreateCode;
    Button btnCreateCodeAndImg;
    ImageView iv2Code;
    ImageView ivBarCode;

    private Dialog dialog;

    String filePath  =  ZaoUtils.pathSD + "/ame";
    String picPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);

        etCodeKey = (EditText)findViewById(R.id.et_code_key);
        btnCreateCode = (Button)findViewById(R.id.btn_create_code);
        btnCreateCode.setOnClickListener(this);
        btnCreateCodeAndImg = (Button)findViewById(R.id.btn_create_code_and_img);
        btnCreateCodeAndImg.setOnClickListener(this);
        iv2Code = (ImageView)findViewById(R.id.iv_2_code);
        iv2Code.setOnClickListener(this);
        ivBarCode = (ImageView)findViewById(R.id.iv_bar_code);
        ivBarCode.setOnClickListener(this);
    }


    public void onClick(View view){
        String key=etCodeKey.getText().toString();
        switch (view.getId()){
            case  R.id.btn_create_code: //生成码
                if(TextUtils.isEmpty(key)){
                    Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
                }else{
                    create2Code(key);
                    createBarCode(key);
                }
                break;
            case  R.id.btn_create_code_and_img: //生成码, 加入头像
                Bitmap bitmap = create2Code(key);
                Bitmap headBitmap = getHeadBitmap(60);
                if(bitmap!=null&&headBitmap!=null){
                    createQRCodeBitmapWithPortrait(bitmap,headBitmap);
                }
                break;

            case R.id.iv_2_code :
/*                iv2Code.buildDrawingCache(true);
                iv2Code.buildDrawingCache();
                Bitmap bitmap2 = iv2Code.getDrawingCache();
                saveBitmapFile(bitmap2);
                iv2Code.setDrawingCacheEnabled(false);
                Toast.makeText(CreateCodeActivity.this, bitmap2.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"iv_2_code",Toast.LENGTH_SHORT).show();*/
                clickPic(iv2Code);
                break;

            case R.id.iv_bar_code :
                clickPic(ivBarCode);
                Toast.makeText(this,"iv_bar_code",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private Bitmap createBarCode(String key) {
        Bitmap qrCode = null;
        try {
            qrCode = EncodingHandler.createBarCode(key, 600, 300);
            ivBarCode.setImageBitmap(qrCode);
        } catch (Exception e) {
            Toast.makeText(this,"输入的内容条形码不支持！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return qrCode;
    }

    /**
     * 生成二维码
     * @param key
     */
    private Bitmap create2Code(String key) {
        Bitmap qrCode=null;
        try {
            qrCode= EncodingHandler.create2Code(key, 400);
            iv2Code.setImageBitmap(qrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return qrCode;
    }
    /**
     * 初始化头像图片
     */
    private Bitmap getHeadBitmap(int size) {
        try {
            // 这里采用从asset中加载图片abc.jpg
            Bitmap portrait = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            // 对原有图片压缩显示大小
            Matrix mMatrix = new Matrix();
            float width = portrait.getWidth();
            float height = portrait.getHeight();
            mMatrix.setScale(size / width, size / height);
            return Bitmap.createBitmap(portrait, 0, 0, (int) width,
                    (int) height, mMatrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 在二维码上绘制头像
     */
    private void createQRCodeBitmapWithPortrait(Bitmap qr, Bitmap portrait) {
        // 头像图片的大小
        int portrait_W = portrait.getWidth();
        int portrait_H = portrait.getHeight();

        // 设置头像要显示的位置，即居中显示
        int left = (qr.getWidth() - portrait_W) / 2;
        int top = (qr.getHeight() - portrait_H) / 2;
        int right = left + portrait_W;
        int bottom = top + portrait_H;
        Rect rect1 = new Rect(left, top, right, bottom);

        // 取得qr二维码图片上的画笔，即要在二维码图片上绘制我们的头像
        Canvas canvas = new Canvas(qr);

        // 设置我们要绘制的范围大小，也就是头像的大小范围
        Rect rect2 = new Rect(0, 0, portrait_W, portrait_H);
        // 开始绘制
        canvas.drawBitmap(portrait, rect2, rect1, null);
    }


    public  void clickPic(ImageView imageView) {
        // TODO 自动生成的方法存根
        imageView.buildDrawingCache(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        saveBitmapFile(bitmap);
        imageView.setDrawingCacheEnabled(false);
        Toast.makeText(CreateCodeActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    public void saveBitmapFile(Bitmap bitmap){
        picPath  =  filePath + "/qrcode"+ZaoUtils.getSystemTime()+".jpg";
        File temp = new File(filePath);//要保存文件先创建文件夹
        if (!temp.exists()) {
            temp.mkdir();
        }
        ////重复保存时，覆盖原同名图片
        File file=new File(picPath);//将要保存图片的路径和图片名称
        //    File file =  new File("/sdcard/1delete/1.png");/////延时较长
        try {
            BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            //通知手机更新相册显示内容
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(picPath))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(View v){////点击按钮删除这个文件
        File file = new File(picPath);
        if(file.exists()){
            file.delete();
        }

    }///deleteFile

}

