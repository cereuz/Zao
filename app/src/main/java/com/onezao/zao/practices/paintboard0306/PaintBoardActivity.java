package com.onezao.zao.practices.paintboard0306;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.onezao.zao.practices.viewanim0306.ViewAnimUtils;
import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;
import com.onezao.zao.utils.ZaoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintBoardActivity extends Activity  implements View.OnClickListener{
    private ImageView iv_paintboard_0108;
    private EditText et_paintboard_0108;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap2;
    private EditText et_paintsize_0108;
    private EditText et_paintcolor_0108;
    private String picPath;
    private Button btn_paintboard_0108;
    private Button btn_paintsave_0108;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintboard_0108);
        iv_paintboard_0108 = (ImageView) findViewById(R.id.iv_paintboard_0108);
        et_paintboard_0108 = (EditText) findViewById(R.id.et_paintboard_0108);

        et_paintsize_0108 = (EditText) findViewById(R.id.et_paintsize_0108);
        et_paintsize_0108.setFocusable(false);
        et_paintsize_0108.setOnClickListener(this);

        et_paintcolor_0108 = (EditText) findViewById(R.id.et_paintcolor_0108);
        et_paintcolor_0108.setFocusable(false);
        et_paintcolor_0108.setOnClickListener(this);

        btn_paintboard_0108 = (Button) findViewById(R.id.btn_paintboard_0108);
        btn_paintboard_0108.setOnClickListener(this);

        btn_paintsave_0108 = (Button) findViewById(R.id.btn_paintsave_0108);
        btn_paintsave_0108.setOnClickListener(this);
    }

    //选择相片
    public   void  gallery(View view){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, 0);
    }

    // 得到返回值，即选中的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photoBmp = null;
        if (data != null) {
            Uri uri = data.getData();
            iv_paintboard_0108.setImageURI(uri);
            try {
                photoBmp = ViewAnimUtils.getBitmapFormUri(this, uri);
                loadpaint(photoBmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //判断并处理点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 加载图片，控制图片画笔点击事件。
            case R.id.btn_paintboard_0108:
                String et_processpicPath = et_paintboard_0108.getText().toString().trim();
                picPath = ZaoUtils.pathSD + et_processpicPath;
                // 0.加载图片资源。把背景图加载进来
                Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                loadpaint(bitmap);
                break;
            //设置画笔尺寸
            case R.id.et_paintsize_0108:
                chooseSize();
                break;
            //设置画笔颜色
            case R.id.et_paintcolor_0108:
                chooseColor();
                break;
            //保存画板编辑后的图片
            case R.id.btn_paintsave_0108:
//			File file = new File(Environment.getExternalStorageDirectory().getPath(),"/ame/"+SystemClock.uptimeMillis()+".png");
                File file = new File(Environment.getExternalStorageDirectory().getPath(),"/ame/"+ ZaoUtils.getSystemTime()+".png");
                FileOutputStream fos;
                try {
                    fos = new FileOutputStream(file);
                    // 通过这个方法把文件保存到SD卡上
                    // 1.保存的格式 2.保存的质量，0代表质量差体积小，100代表质量好体积大 3.输出流，确认保存的位置
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    //发送SD卡挂载的广播，系统收到广播之后，会扫描SD卡，把新保存的文件de路径添加到数据库中
/*				Intent  intent = new  Intent();
				intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
				intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
				sendBroadcast(intent);*/
                    //安卓4.0之后，必须通过这样的方式才能行。因为系统限制了权限，不能发上边的广播。
                    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "保存成功！" , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //加载图片的方法
    private void loadpaint(Bitmap bitmap) {
        // 1. 创建空的图片。如果需要操作图片，需要mutable
        bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                bitmap.getConfig());
        // 2.使用空的图片创建一个canvas
        canvas = new Canvas(bitmap2);
        // 3. 画图片 , 将图片资源画到画布上。
        paint = new Paint();
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        // 4. 将画好的图片设置到控件上
        iv_paintboard_0108.setImageBitmap(bitmap2);

        iv_paintboard_0108.setOnTouchListener(new View.OnTouchListener() {
            private float startx;
            private float starty;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("触摸View");
                        Toast.makeText(PaintBoardActivity.this, "触摸View",
                                Toast.LENGTH_SHORT).show();
                        // 记录按下位置
                        startx = event.getX();
                        starty = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        System.out.println("移动");
                        Toast.makeText(PaintBoardActivity.this, "移动",
                                Toast.LENGTH_SHORT).show();

                        float x = event.getX();
                        float y = event.getY();
                        // 画线，将点击移动的路径画出来
                        canvas.drawLine(startx, starty, x, y, paint);
                        // 将图片显示到控件上
                        iv_paintboard_0108.setImageBitmap(bitmap2);
                        // 画线结束之后，把开始坐标修改为最新的位置。
                        startx = x;
                        starty = y;
                        break;

                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        Toast.makeText(PaintBoardActivity.this, "抬起",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                // 返回true说明当前控件消费了这一些类的touch事件
                // touch事件由一个ACTION_DOWN 和ACTION_MOVE（0个或多个）和一个ACTION_UP组成
                // 如果ACTION_DOWN的时候没有返回true，则ACTION_MOVE和ACTION_UP就交给父控件来处理
                return true; // 必须返回true，才能实现点击画笔功能。
            }
        });
    }


    //加载图片的方法
    private void loadpaint2(Bitmap bitmap) {
        // 1. 创建空的图片。如果需要操作图片，需要mutable
        bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                bitmap.getConfig());
        // 2.使用空的图片创建一个canvas
        canvas = new Canvas(bitmap2);
        // 3. 画图片 , 将图片资源画到画布上。
        paint = new Paint();
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        // 4. 将画好的图片设置到控件上
        iv_paintboard_0108.setImageBitmap(bitmap2);

        iv_paintboard_0108.setOnTouchListener(new View.OnTouchListener() {
            private float startx;
            private float starty;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("触摸View");
                        Toast.makeText(PaintBoardActivity.this, "触摸View",
                                Toast.LENGTH_SHORT).show();
                        // 记录按下位置
                        startx = event.getRawX();
                        starty = event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        System.out.println("移动");
                        Toast.makeText(PaintBoardActivity.this, "移动",
                                Toast.LENGTH_SHORT).show();

                        float x = event.getRawX();
                        float y = event.getRawY();
                        // 画线，将点击移动的路径画出来
                        canvas.drawLine(startx, starty, x, y, paint);
                        // 将图片显示到控件上
                        iv_paintboard_0108.setImageBitmap(bitmap2);
                        // 画线结束之后，把开始坐标修改为最新的位置。
                        startx = x;
                        starty = y;
                        break;

                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        Toast.makeText(PaintBoardActivity.this, "抬起",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                // 返回true说明当前控件消费了这一些类的touch事件
                // touch事件由一个ACTION_DOWN 和ACTION_MOVE（0个或多个）和一个ACTION_UP组成
                // 如果ACTION_DOWN的时候没有返回true，则ACTION_MOVE和ACTION_UP就交给父控件来处理
                return true; // 必须返回true，才能实现点击画笔功能。
            }
        });
    }

    // 选择设置画笔颜色
    private void chooseColor() {
        // 通过builder 构建器来构造
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择你需要的颜色。");
        final String items[] = { "Color.BLACK", "Color.BLUE", "Color.CYAN",
                "Color.DKGRAY", "Color.GRAY", "Color.GREEN", "Color.LTGRAY",
                "Color.MAGENTA", "Color.RED", "Color.TRANSPARENT",
                "Color.WHITE", "Color.YELLOW" };
        // 单选是设置单选按钮选项 . -1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // [1] 把选择的条目给取出来
                final String item = items[which];
                Toast.makeText(PaintBoardActivity.this, item,
                        Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    public void run() {
                        et_paintcolor_0108.setText(item);
                    }
                });
                switch (which) {
                    case 0:
                        paint.setColor(Color.BLACK);
                        break;
                    case 1:
                        paint.setColor(Color.BLUE);
                        break;
                    case 2:
                        paint.setColor(Color.CYAN);
                        break;
                    case 3:
                        paint.setColor(Color.DKGRAY);
                        break;
                    case 4:
                        paint.setColor(Color.GRAY);
                        break;
                    case 5:
                        paint.setColor(Color.GREEN);
                        break;
                    case 6:
                        paint.setColor(Color.LTGRAY);
                        break;
                    case 7:
                        paint.setColor(Color.MAGENTA);
                        break;
                    case 8:
                        paint.setColor(Color.RED);
                        break;
                    case 9:
                        paint.setColor(Color.TRANSPARENT);
                        break;
                    case 10:
                        paint.setColor(Color.WHITE);
                        break;
                    case 11:
                        paint.setColor(Color.YELLOW);
                        break;
                }
                // [2] 把对话框关闭
                dialog.dismiss();
            }
        });
        // 最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }

    // 选择并设置画笔大小
    private void chooseSize() {
        // 通过builder 构建器来构造
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择你需要的画笔大小。");
        final String items[] = { "6", "9", "12", "16", "20", "24", "30", "36",
                "50", "60", "80", "100", "200", "500", "1000", "2000" };
        // 单选是设置单选按钮选项 . -1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // [1] 把选择的条目给取出来
                final String item = items[which];
                Toast.makeText(PaintBoardActivity.this, item,
                        Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    public void run() {
                        et_paintsize_0108.setText(item);
                        paint.setStrokeWidth(Integer.valueOf(item));
                    }
                });
                // [2] 把对话框关闭
                dialog.dismiss();
            }
        });
        // 最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }

    // 加载顶部菜单，添加菜单的点击事件。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置左上角的图标的点击事件 ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AdminUtils.AdminMenu(PaintBoardActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}
