package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import info.androidhive.materialtabs.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Shop3 extends AppCompatActivity {


    private int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0,l=0;
    String pet;
    String pet_id;
    String email;

    String needs;
    String needs1;
    String needs2;
    String needs3;
    String needs4;
    String needs5;
    String needs6;
    String needs7;
    String needs8;
    private String et1="",password,name,pet_weight;

    private Button button,button2;
    private ImageView imageView;

    private String showSplittedStr0 = "" , img="" ;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;

    final static int PICTURE_REQUEST_CODE = 1;
    final static int REQUEST_EXTERNAL_STORAGE = 2;
    String filePath = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop3);

        //標題列
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        needs = intent.getStringExtra("needs");
        needs1 = intent.getStringExtra("needs1");
        needs2 = intent.getStringExtra("needs2");
        needs3 = intent.getStringExtra("needs3");
        needs4 = intent.getStringExtra("needs4");
        needs5 = intent.getStringExtra("needs5");
        needs6 = intent.getStringExtra("needs6");
        needs7 = intent.getStringExtra("needs7");
        needs8 = intent.getStringExtra("needs8");
        email = intent.getStringExtra("email");
        pet = intent.getStringExtra("pet");
        //et1 = intent.getStringExtra("et1");
        email= intent.getStringExtra("email");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");
        pet_id= intent.getStringExtra("pet_id");
        pet_weight= intent.getStringExtra("pet_weight");



        imageView=(ImageView)findViewById(R.id.imageView5) ;
        button=(Button)findViewById(R.id.button10);
        button.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

//下面程式碼放到按鈕裡面
                //取得權限之後開啟相簿

                filePath = "";

                if (Build.VERSION.SDK_INT >= 23) {
                    int permission = ActivityCompat.checkSelfPermission(Shop3.this, WRITE_EXTERNAL_STORAGE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // 無權限，向使用者請求
                        ActivityCompat.requestPermissions(Shop3.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                    } else {
                        //已有權限，執行儲存程式
                        requireSelectImage();

                    }
                } else {
                    requireSelectImage();

                }

                //到這兒

            }

        });

        button2=(Button)findViewById(R.id.button9);
        button2.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(Shop3.this,Shop2.class);

                intent.putExtra("needs", needs);//可放所有基本類別
                intent.putExtra("needs1", needs1);//可放所有基本類別
                intent.putExtra("needs2", needs2);//可放所有基本類別
                intent.putExtra("needs3", needs3);//可放所有基本類別
                intent.putExtra("needs4", needs4);//可放所有基本類別
                intent.putExtra("needs5", needs5);//可放所有基本類別
                intent.putExtra("needs6", needs6);//可放所有基本類別
                intent.putExtra("needs7", needs7);//可放所有基本類別
                intent.putExtra("needs8", needs8);//可放所有基本類別

                //一句話

                //intent.putExtra("et1", et1);

                intent.putExtra("pet", pet);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("name", name);
                intent.putExtra("pet_id",pet_id);
                intent.putExtra("img",showSplittedStr0);
                intent.putExtra("pet_weight",pet_weight);

                //切換Activity
                startActivity(intent);



            }

        });

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    //以下漏漏長全部都是上傳照片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();

            if(uri != null)
            {

                String[] type = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(uri, type, null, null, null);

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(type[0]);
                filePath = cursor.getString(columnIndex);

                final String[] splittedStr0 = filePath.split("/"); // 將字串str0以逗號分割,其結果存在splittedStr0字串陣列中

                for (String s : splittedStr0) {
                    showSplittedStr0 = s ;
                }

                cursor.close();

                //確定上傳照片
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Shop3.this);
                builder.setMessage("確定要上傳照片嗎?");
                builder.setTitle("上傳照片");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(Shop3.this, "", "檔案上傳中", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uploadImageToServer();  //開始上傳
                                showSplittedStr0="http://140.127.22.42:82/uploads/"+showSplittedStr0; //圖片路徑
                                //editphoto();  //修改資料庫
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();


            }
            else
            {

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch(requestCode)
        {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //取得權限，進行檔案存取
                    requireSelectImage();
                }
                else
                {
                    //使用者拒絕權限，停用檔案存取功能
                }
                break;
        }
    }

    private void requireSelectImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        Intent destIntent = Intent.createChooser(intent, "選擇檔案");

        startActivityForResult(destIntent, PICTURE_REQUEST_CODE);


    }

    private void uploadImageToServer()
    {
        File file = new File(filePath);
        if(file.exists())
        {
            try
            {
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";

                FileInputStream fileInputStream = new FileInputStream(file);
                URL url = new URL("http://140.127.22.42:82/UploadToServer.php");

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection","Keep-Alive");
                httpURLConnection.setRequestProperty("ENCTYPE","multipart/form-data");
                httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
                httpURLConnection.setRequestProperty("upload_file", filePath);

                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + filePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                int length = 0;
                int maxSize = 1 * 1024 * 1024;

                while((length = fileInputStream.available()) > 0)
                {
                    byte[] buffer = new byte[Math.min(length, maxSize)];
                    fileInputStream.read(buffer, 0, buffer.length);
                    dataOutputStream.write(buffer, 0, buffer.length);
                    Log.e("T",length + "");
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                if(httpURLConnection.getResponseCode() == 200)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Toast.makeText(Shop3.this,"上傳成功",Toast.LENGTH_SHORT).show();

                            button.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.VISIBLE);
                            setPhoto();  //上傳成功之後開始設定圖片
                            //可以在這裡隱藏按鈕

                        }
                    });
                }

                fileInputStream.close();

                dataOutputStream.close();
            }
            catch(Exception error)
            {
                Log.e("E",error.toString());
            }
            finally
            {
                progressDialog.dismiss();
            }
        }
        else
        {
            progressDialog.dismiss();
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(Shop3.this,"找不到該圖片 所以無法上傳",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //設定照片
    private void setPhoto(){

        //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {

                imageView.setImageBitmap(result); //記得宣告imageView
                super.onPostExecute(result);
            }
        }.execute(showSplittedStr0);


    }

    //讀取網路圖片，型態為Bitmap
    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}