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
 * Created by 柯柏任 on 2016/11/9.
 */

public class petDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private String petname;
    private String email;
    private String haircolor;
    private String sex;
    private String birthday;
    private String subspecies;
    private String pet_id;
    private String name;
    private String password;
    private String img;
    private String weight;
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private SimpleAdapter adapter;
    private ListView listView2;
    private TextView textView;
    private ImageView imageView1;
    private long i;
    private Calendar mCalendar;
    private List<String> lunch;
    public  String[] arr;
    public  String[] arr2;
    public  String[] arr3;
    public  String[] arr4;
    public  String[] arr5;
    public  String[] arr6;
    public  String[] arr7;
    public  String[] arr8;
    private String showSplittedStr0 = "";

    final static int PICTURE_REQUEST_CODE = 1;
    final static int REQUEST_EXTERNAL_STORAGE = 2;
    String filePath = "";
    ProgressDialog progressDialog;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            //--

            getpet();
            // Response received from the server
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            //抓取資料
                            mQueue = Volley.newRequestQueue(petDetail.this);
                            getRequest = new StringRequest(Request.Method.POST, mUrl,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {


                                            Intent intent = new Intent(petDetail.this, pet.class);
                                            intent.putExtra("email", email);
                                            intent.putExtra("name", name);
                                            intent.putExtra("password", password);
                                            intent.putExtra("petname", arr);
                                            intent.putExtra("birthday", arr2);
                                            intent.putExtra("sex", arr3);
                                            intent.putExtra("subspecies", arr4);
                                            intent.putExtra("haircolor", arr5);
                                            intent.putExtra("pet_id", arr6);
                                            intent.putExtra("img", arr7);
                                            intent.putExtra("weight", arr8);

                                            startActivity(intent);
                                            finish();
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
                                    map.put("query_string", "SELECT * FROM order2 where email='" + email + "'");
                                    return map;
                                }

                            };

                            mQueue.add(getRequest);
                            //---

                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(petDetail.this);
                            builder.setMessage("不登入就不給你用")
                                    .setNegativeButton("確定", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(petDetail.this);
            queue.add(loginRequest);

            //---


        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petdetail);

        //設定性別選項
        lunch = new ArrayList<>();
        lunch.add("男生");
        lunch.add("女生");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setAlpha(75);

        getpet();

        //標題列
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];
        arr8 = new String[30];


        Intent intent = getIntent();
        petname = intent.getStringExtra("petname");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        birthday = intent.getStringExtra("birthday");
        sex = intent.getStringExtra("sex");
        haircolor = intent.getStringExtra("haircolor");
        subspecies = intent.getStringExtra("subspecies");
        pet_id = intent.getStringExtra("pet_id");
        img = intent.getStringExtra("img");
        weight = intent.getStringExtra("weight");

        if(!img.equals("")) {
           setPhoto();
        }

        imageView1=(ImageView)findViewById(R.id.roundImageView);
        imageView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                filePath = "";

                filePath = "";

                if (Build.VERSION.SDK_INT >= 23) {
                    int permission = ActivityCompat.checkSelfPermission(petDetail.this, WRITE_EXTERNAL_STORAGE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // 無權限，向使用者請求
                        ActivityCompat.requestPermissions(petDetail.this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                    } else {
                        //已有權限，執行儲存程式
                        requireSelectImage();

                    }
                } else {
                    requireSelectImage();

                }
            }});



        textView=(TextView)findViewById(R.id.textView17);

        textView.setText(petname);

        listView2 = (ListView) findViewById(R.id.list);

        // 清單面版
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content6,
                new String[] { "title", "info" },
                new int[] {	R.id.title, R.id.info});
        listView2.setAdapter(adapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;
                if(arg3==0) {

                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(petDetail.this);
                    editDialog.setTitle("請輸入它的暱稱");

                    final EditText editText = new EditText(petDetail.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {

                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            petname=editText.getText().toString();
                            textView.setText(petname);
                            UpdateList(i,petname);

                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    editDialog.show();


                }

                else if(arg3==1){

                    i=arg3;

                    mCalendar = Calendar.getInstance();

                    DatePickerDialog pickerDialog = new DatePickerDialog(petDetail .this, new DatePickerDialog.OnDateSetListener() {

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
                else if(arg3==2){
                    i=arg3;

                    new AlertDialog.Builder(petDetail .this)
                            .setTitle("請選擇它的性別")
                            .setItems(lunch.toArray(new String[lunch.size()]), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sex = lunch.get(which);
                                    UpdateList(i,sex);

                                }
                            })
                            .show();
                }
                else if(arg3==3){

                    i=arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(petDetail .this);
                    editDialog.setTitle("請輸入它的品種");

                    final EditText editText = new EditText(petDetail .this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            subspecies=editText.getText().toString();
                            UpdateList(i,subspecies);

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

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(petDetail .this);
                    editDialog.setTitle("請輸入它的毛色");

                    final EditText editText = new EditText(petDetail .this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            haircolor=editText.getText().toString();
                            UpdateList(i, haircolor);

                        }
                    });
                    editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

                    editDialog.show();

                }
                else if(arg3==5) {

                    i = arg3;

                    AlertDialog.Builder editDialog = new AlertDialog.Builder(petDetail.this);
                    editDialog.setTitle("請輸入它的體重");

                    final EditText editText = new EditText(petDetail.this);

                    editDialog.setView(editText);

                    editDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            weight = editText.getText().toString();
                            UpdateList(i, weight);

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

    //ListView內容
    private List getData() {

        ArrayList list = new ArrayList();
        Map map = new HashMap(); map.put("title", "暱稱"); map.put("info", petname); list.add(map);
        map = new HashMap(); map.put("title", "生日"); map.put("info", birthday);  list.add(map);
        map = new HashMap(); map.put("title", "性別"); map.put("info", sex);  list.add(map);
        map = new HashMap(); map.put("title", "品種"); map.put("info", subspecies);  list.add(map);
        map = new HashMap(); map.put("title", "毛色"); map.put("info", haircolor);  list.add(map);
        map = new HashMap(); map.put("title", "重量"); map.put("info", weight);  list.add(map);

        return list;
    }


    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //--

        getpet();
        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        //抓取資料
                        mQueue = Volley.newRequestQueue(petDetail.this);
                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {


                                        Intent intent = new Intent(petDetail.this, pet.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("name", name);
                                        intent.putExtra("password", password);
                                        intent.putExtra("petname", arr);
                                        intent.putExtra("birthday", arr2);
                                        intent.putExtra("sex", arr3);
                                        intent.putExtra("subspecies", arr4);
                                        intent.putExtra("haircolor", arr5);
                                        intent.putExtra("pet_id", arr6);
                                        intent.putExtra("img", arr7);
                                        intent.putExtra("weight", arr8);

                                        startActivity(intent);
                                        finish();
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
                                map.put("query_string", "SELECT * FROM order2 where email='" + email + "'");
                                return map;
                            }

                        };

                        mQueue.add(getRequest);
                        //---

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(petDetail.this);
                        builder.setMessage("不登入就不給你用")
                                .setNegativeButton("確定", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(petDetail.this);
        queue.add(loginRequest);

        //---

        return super.onOptionsItemSelected(item);
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

    //修改資料
    private void edit(){


        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

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
                map.put("query_string", "UPDATE `pet` SET `petname` = '"+petname+"' ,`birthday` = '"+birthday+"' ,`sex` = '"+sex+"' ,`subspecies` = '"+subspecies+"' ,`haircolor` = '"+haircolor+"', `weight` = '"+weight+"'  WHERE   `pet_id` = '"+pet_id+"' ;");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    public void getpet() {

        //抓取資料
        mQueue = Volley.newRequestQueue(petDetail.this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {
                            for(int i=0;i<30;i++){
                                arr[i]="0";
                                arr2[i]="0";
                                arr3[i]="0";
                                arr4[i]="0";
                                arr5[i]="0";
                                arr6[i]="0";
                                arr7[i]="0";
                                arr8[i]="0";
                            }

                            for(int i=0;i<30;i++){
                                String petname = new JSONArray(s).getJSONObject(i).getString("petname");
                                String birthday = new JSONArray(s).getJSONObject(i).getString("birthday");
                                String sex = new JSONArray(s).getJSONObject(i).getString("sex");
                                String subspecies = new JSONArray(s).getJSONObject(i).getString("subspecies");
                                String haircolor= new JSONArray(s).getJSONObject(i).getString("haircolor");
                                String pet_id= new JSONArray(s).getJSONObject(i).getString("pet_id");
                                String img= new JSONArray(s).getJSONObject(i).getString("photourl");
                                String weight= new JSONArray(s).getJSONObject(i).getString("weight");
                                arr[i]=petname;
                                arr2[i]=birthday;
                                arr3[i]=sex;
                                arr4[i]=subspecies;
                                arr5[i]=haircolor;
                                arr6[i]=pet_id;
                                arr7[i]=img;
                                arr8[i]=weight;
                            }

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
                map.put("query_string", "SELECT * FROM pet where email='"+email+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

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
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(petDetail.this);
                builder.setMessage("確定要上傳照片嗎?");
                builder.setTitle("上傳照片");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog = ProgressDialog.show(petDetail.this, "", "檔案上傳中", true);
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
                            Toast.makeText(petDetail.this,"上傳成功",Toast.LENGTH_SHORT).show();
                            img=showSplittedStr0;
                            setPhoto();
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
                    Toast.makeText(petDetail.this,"找不到該圖片 所以無法上傳",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setPhoto(){
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {


                imageView1.setImageBitmap(result);
                super.onPostExecute(result);
            }

        }.execute(img);
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
                map.put("query_string", "UPDATE `pet` SET `photourl` = '"+showSplittedStr0+"'   WHERE   `pet_id` = '"+pet_id+"';" );
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

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
