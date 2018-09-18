package com.onezao.zao.javamail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.zaov.R;
import com.onezao.zao.utils.SpUtils;

import java.io.File;

public class JavaMailActivity extends AppCompatActivity {

    private EditText editText;
    private EditText et_send_file;
    private int REQUEST_CODE_SELECT_FILE = 0;
    File file = new File(Environment.getExternalStorageDirectory()+File.separator+"ame/beau.jpg");;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javamail_view);

        editText = (EditText) findViewById(R.id.toAddEt);
        et_send_file = (EditText) findViewById(R.id.et_send_file);
    }


    public void senTextMail(View view) {
        SendMailUtil.send(this,editText.getText().toString());
        //发送html邮件
//        SendMailUtil.send(this,editText.getText().toString(),true);
    }

    public void sendFileMail(View view) {

/*        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"A Happy Excursion.txt");
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            String str = MailUtils.xyywords;
            byte[] data = str.getBytes();
            os.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (os != null)os.close();
            } catch (IOException e) {
            }
        }*/
        SendMailUtil.send(this,file,editText.getText().toString());
    }


    public void toSetup(View view) {
        SpUtils.putBoolean(this, ConstantValue.Zaov_File,ConstantValue.JAVA_MAIL_SETUP,false);
        Intent intent = new Intent(this,JavaMailSetupActivity.class);
        startActivity(intent);
        finish();
    }

    public void pickFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), REQUEST_CODE_SELECT_FILE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case 0 :
                if (resultCode == RESULT_OK) {
                    Uri selectedMediaUri = data.getData();
                    String path = FileUtils.getPath(this, selectedMediaUri);
                    file = new File(path);
                    Toast.makeText(this, "文件路径："+path, Toast.LENGTH_SHORT).show();
                    et_send_file.setText("文件路径："+path);
                }
                break;
            default:
                break;
        }
    }
}

