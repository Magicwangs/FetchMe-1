package com.cgf.fetchme.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cgf.fetchme.R;
import com.cgf.fetchme.activity.web.DJServer;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

/**
 * Created by MagicWang on 2016/4/11.
 */
public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private String mFilePath;
    private ImageView mImageView;
    private static int REQ_1 = 1;
    private static int CROP_PICTURE = 2;
    private static int CHOOSE_PICTURE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mImageView = (ImageView) findViewById(R.id.testiv);
        Intent getIntent = getIntent();
        String flag = getIntent.getStringExtra("key");
        if (flag.equals("fromPic")) {
            // 创建拍照意图
            mFilePath = "/mnt/sdcard" + "/" + "fetchme";     //系统本地根目录下
            File myFile = new File(mFilePath);
            if (!myFile.exists()) {
                myFile.mkdirs();
            }
            Calendar t = Calendar.getInstance();
            mFilePath = mFilePath + "/" + "IMG" + t.get(Calendar.HOUR) + t.get(Calendar.MINUTE) + t.get(Calendar.SECOND) + ".png";
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photouri = Uri.fromFile(new File(mFilePath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
            startActivityForResult(intent, REQ_1);
        } else if (flag.equals("fromAlbum")) {
            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
            openAlbumIntent.setType("image/*");
            startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_1) {
                cropImage(getImageUri(), CROP_PICTURE);
            } else if (requestCode == CHOOSE_PICTURE) {
                Uri originalUri = data.getData();
                cropImage2(originalUri, CROP_PICTURE);
            } else if (requestCode == CROP_PICTURE) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "文件不存在!", Toast.LENGTH_LONG).show();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void goResult(View view) {
        DJServer.uploadImage("uploadImage/", null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String response, Throwable throwable) {
                Log.d(TAG, "uploadImage----onFailure----in----");
                Toast.makeText(getApplicationContext(), "test failure",
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "uploadImage----onFailure----out----");
            }

            @Override
            public void onSuccess(int i, Header[] headers, String response) {
                Log.d(TAG, "uploadImage----onSuccess----in----");
                Toast.makeText(getApplicationContext(), "test success result is" + response,
                        Toast.LENGTH_LONG).show();
                Log.d(TAG, "uploadImage----onSuccess----out----result=" + response);
            }
        }, mFilePath);

    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(mFilePath));
    }

    private void cropImage(Uri uri, int requestCode) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 制定待裁剪的 image 所在路径 uri
        intent.setDataAndType(uri, "image/*");
        // 意图的 为 crop(裁剪) true
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后，输出图片的尺寸
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        // 图片格式
        intent.putExtra("outputFormat", "png");
        // 取消人脸识别功能
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, requestCode);
    }

    private void cropImage2(Uri uri, int requestCode) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 制定待裁剪的 image 所在路径 uri
        intent.setDataAndType(uri, "image/*");
        // 意图的 为 crop(裁剪) true
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后，输出图片的尺寸
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        // 图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 取消人脸识别功能
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        //设置剪切的图片保存位置
        mFilePath = Environment.getExternalStorageDirectory().getPath() + "/crop.jpg";
        Uri cropUri = Uri.fromFile(new File(mFilePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        startActivityForResult(intent, requestCode);
    }

}