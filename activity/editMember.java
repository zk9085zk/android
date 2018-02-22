package info.androidhive.materialtabs.activity;


import android.app.DatePickerDialog;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.materialtabs.R;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Xuan on 2016/10/26.
 */

public class editMember extends AppCompatActivity {



    private SimpleAdapter adapter;
    private ListView listView3;
    private ListView listView2;
    private ListView listView1;
    private String email;
    private String password ;
    private String name;
    private String nickname;
    private String cellphone;
    private String birthday;
    private String sex;
    private String address;
    private long i;
    private List<String> lunch;
    private Calendar mCalendar;
    private Toolbar toolbar;
    private String showSplittedStr0 = "";
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private String img;
    private Bitmap bit;

    final static int PICTURE_REQUEST_CODE = 1;
    final static int REQUEST_EXTERNAL_STORAGE = 2;
    String filePath = "";
    ProgressDialog progressDialog;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent(editMember.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("password", password);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        //抓取個人資料
        Intent intent = getIntent();
        email=intent.getStringExtra("email");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");
        nickname=intent.getStringExtra("nickname");
        cellphone= intent.getStringExtra("phone");
        birthday= intent.getStringExtra("birthday");
        sex=intent.getStringExtra("sex");
        address= intent.getStringExtra("address");

        //設定性別選項
        lunch = new ArrayList<>();
        lunch.add("男生");
        lunch.add("女生");

        //標題列
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        LayoutInflater inflater = LayoutInflater.from(editMember.this);
        final View v = inflater.inflate(R.layout.alert1, null);

        setlist();
        getPhoto();

        listView1 = (ListView) findViewById(R.id.listView);

        // 清單面版-1
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content2,
                new String[] { "title"},
                new int[] {	R.id.title});
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;
                if(arg3==0) {
                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(editMember.this);
                    editDialog.setTitle("請輸入你的密碼");

                    final EditText editText = new EditText(editMember.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            password=editText.getText().toString();
                            UpdatePassword(i,password);
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();
                }

            }
        });

        listView2 = (ListView) findViewById(R.id.listView2);

        // 清單面版-2
        adapter = new SimpleAdapter(this, getData2(),
                R.layout.view2content3,
                new String[] { "title", "info" },
                new int[] {	R.id.title, R.id.info});
        listView2.setAdapter(adapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;
                if(arg3==1) {
                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(editMember.this);
                    editDialog.setTitle("請輸入你的名字");

                    final EditText editText = new EditText(editMember.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            name=editText.getText().toString();
                            UpdateList(i,name);
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();


                }

                else if(arg3==2){
                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(editMember.this);
                    editDialog.setTitle("請輸入你的暱稱");

                    final EditText editText = new EditText(editMember.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            nickname=editText.getText().toString();
                            UpdateList(i,nickname);
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();
                }
                else if(arg3==3){
                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(editMember.this);
                    editDialog.setTitle("請輸入你的電話");

                    final EditText editText = new EditText(editMember.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            cellphone=editText.getText().toString();
                            UpdateList(i,cellphone);
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();
                }
                else if(arg3==4){
                    i=arg3;

                    mCalendar = Calendar.getInstance();

                    DatePickerDialog pickerDialog = new DatePickerDialog(editMember .this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker arg0, int year, int month, int day) {
                            mCalendar.set(year, month, day);//将点击获得的年月日获取到calendar中。
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//转型
                            birthday=format.format(mCalendar.getTime());
                            UpdateList(i,birthday);


                        }
                    }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                    pickerDialog.show();
                }
                else if(arg3==5){
                    i=arg3;

                    new AlertDialog.Builder(editMember .this)
                            .setTitle("請選擇你的性別")
                            .setItems(lunch.toArray(new String[lunch.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sex = lunch.get(which);
                                    UpdateList(i,sex);
                                }
                            })
                            .show();
                }
                else if(arg3==6){
                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(editMember .this);
                    editDialog.setTitle("請輸入你的地址");

                    final EditText editText = new EditText(editMember .this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            address=editText.getText().toString();
                            UpdateList(i,address);
                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();
                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(editMember.this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);
    }


    public void UpdatePassword(long selectedItem,String text)
    {
        ListAdapter la = listView1.getAdapter();
        int itemNum = listView1.getCount();
        for(int i=0; i<itemNum; i++)
        {
            HashMap<String, Object> map = (HashMap<String, Object>)la.getItem(i);
            if ( i == selectedItem) {
                edit();
            }
        }

        ((SimpleAdapter)la).notifyDataSetChanged();
    }

    //更新選單
    public void UpdateList(long selectedItem,String text)
    {
        ListAdapter la = listView2.getAdapter();
        int itemNum = listView2.getCount();
        for(int i=0; i<itemNum; i++)
        {
            HashMap<String, Object> map = (HashMap<String, Object>)la.getItem(i);
            if ( i == selectedItem) {
                map.put("info", text);
                edit();
            }
        }

        ((SimpleAdapter)la).notifyDataSetChanged();
    }

    //ListView內容
    private List getData() {
        ArrayList list = new ArrayList();
        Map map = new HashMap(); map.put("title", "變更密碼");  list.add(map);
        return list;
    }
    //ListView內容
    private List getData2() {
        ArrayList list = new ArrayList();

        Map map = new HashMap(); map.put("title", "電子郵件"); map.put("info", email); list.add(map);
        map = new HashMap(); map.put("title", "姓名"); map.put("info", name); list.add(map);
        map = new HashMap(); map.put("title", "暱稱"); map.put("info", nickname);  list.add(map);
        map = new HashMap(); map.put("title", "電話"); map.put("info", cellphone); list.add(map);
        map = new HashMap(); map.put("title", "生日"); map.put("info", birthday);  list.add(map);
        map = new HashMap(); map.put("title", "性別"); map.put("info", sex);  list.add(map);
        map = new HashMap(); map.put("title", "地址"); map.put("info", address);  list.add(map);

        return list;
    }
    //ListView內容
    private List getData3() {
        ArrayList list = new ArrayList();

        if(bit!=null) {
            Map map = new HashMap();
            map.put("img", bit);
            map.put("image", R.drawable.listing);
            list.add(map);
        }
        else {
            Map map = new HashMap();
            map.put("img", R.drawable.user);
            map.put("image", R.drawable.listing);
            list.add(map);
        }

        return list;
    }


    //修改資料
    private void edit(){



        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(editMember .this);
                        builder.setMessage("Register Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        editmemberRequest registerRequest = new editmemberRequest(name, email,  password,  nickname,cellphone,birthday,sex,address,responseListener);
        RequestQueue queue = Volley.newRequestQueue(editMember .this);
        queue.add(registerRequest);
    }

    //修改照片
    private void editphoto(){

        showSplittedStr0="http://140.127.22.42/uploads/"+showSplittedStr0;
        //抓取資料
        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {
                    TextView textView=(TextView)findViewById(R.id.total);
                    @Override

                    public void onResponse(String s) {


                    }


                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> map = new HashMap<String, String>();
                map.put("query_string", "UPDATE `member` SET `img` = '"+showSplittedStr0+"'   WHERE   `email` = '"+email+"' ;" );
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    //標題列
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }

    //修改照片
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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(editMember.this);
                builder.setMessage("確定要上傳照片嗎?");
                builder.setTitle("上傳照片");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(editMember.this, "", "檔案上傳中", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uploadImageToServer();
                                editphoto();
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
                URL url = new URL("http://140.127.22.42/UploadToServer.php");

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
                            Toast.makeText(editMember.this,"上傳成功",Toast.LENGTH_SHORT).show();
                            getPhoto();
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
                    Toast.makeText(editMember.this,"找不到該圖片 所以無法上傳",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //取得照片
    private void getPhoto(){
        if(!email.equals("null")) {
            mQueue = Volley.newRequestQueue(this);
            getRequest = new StringRequest(Request.Method.POST, mUrl,
                    new Response.Listener<String>() {

                        @Override

                        public void onResponse(String s) {
                            try {
                                img = new JSONArray(s).getJSONObject(0).getString("img");
                                setPhoto();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    Map<String, String> map = new HashMap<String, String>();
                    map.put("query_string", "SELECT * FROM member where email='"+email+"' ");
                    return map;
                }

            };

            mQueue.add(getRequest);
            //---
        }
    }

    private void setPhoto(){
        //設定照片


        //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                bit=result;
                setlist();
                super.onPostExecute(result);
            }
        }.execute(img);


    }

    private void setlist(){

        listView3 = (ListView) findViewById(R.id.listView3);

        // 清單面版-3
        adapter = new SimpleAdapter(this, getData3(),
                R.layout.view2content9,
                new String[] { "img","image"},
                new int[] {R.id.img,R.id.image});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView image = (ImageView) view;
                    image.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        listView3.setAdapter(adapter);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;

                filePath = "";

                if (Build.VERSION.SDK_INT >= 23) {
                    int permission = ActivityCompat.checkSelfPermission(editMember.this, WRITE_EXTERNAL_STORAGE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // 無權限，向使用者請求
                        ActivityCompat.requestPermissions(editMember.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                    } else {
                        //已有權限，執行儲存程式
                        requireSelectImage();

                    }
                } else {
                    requireSelectImage();

                }

            }
        });


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