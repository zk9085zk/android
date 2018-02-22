package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.materialtabs.R;

public class ProductActivity extends AppCompatActivity {

    private String commodity;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private TextView textView,textView2,textView3;
    private Button button,button2;
    private Spinner spinner;
    private ImageView imageView;
    private ArrayAdapter<String> lunchList;
    private String[] lunch = {"   1", "   2", "   3", "   4", "   5","   6","   7","   8","   9"," 10"};
    private String img,ingredient,price,recommend,feature1,feature2,feature3,email,name,password,id;
    private int total;
    private long num;
    public  String[] arr,arr2,arr3,arr4,arr5;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];

        //取得跳頁傳直
        Intent intent = getIntent();
        commodity = intent.getStringExtra("commodity");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");

        textView=(TextView)findViewById(R.id.textView4);
        textView2=(TextView)findViewById(R.id.textView6);
        textView3=(TextView)findViewById(R.id.textView5);
        imageView=(ImageView)findViewById(R.id.imageButton3);

        //數量選單
        spinner = (Spinner)findViewById(R.id.spinner);
        lunchList = new ArrayAdapter<>(ProductActivity.this, android.R.layout.simple_spinner_item, lunch);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                num=arg3+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        button2=(Button)findViewById(R.id.button6);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (email.equals("null")) {
                    new AlertDialog.Builder(ProductActivity.this)
                            .setTitle("尚未登入")
                            .setMessage("親愛的主人!請您先登入唷(*´∀`)~♥")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ProductActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })

                            .show();
                } else {

                    total = (int) num * Integer.parseInt(price);

                    insert();
                    progressDialog = ProgressDialog.show(ProductActivity.this, "", "請稍後", true);
                        // Response received from the server
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        TimerTask task2 = new TimerTask(){
                                            public void run(){
                                              getshop();
                                            }
                                        };
                                        Timer timer = new Timer();
                                        timer.schedule(task2, 500);

                                        TimerTask task = new TimerTask(){
                                            public void run(){
                                                        Intent intent = new Intent(ProductActivity.this, shoppingCart.class);
                                                        intent.putExtra("email", email);
                                                        intent.putExtra("name", name);
                                                        intent.putExtra("password", password);
                                                        intent.putExtra("commodityname", arr);
                                                        intent.putExtra("price", arr2);
                                                        intent.putExtra("num", arr3);
                                                        intent.putExtra("img", arr4);
                                                        startActivity(intent);
                                                        progressDialog.dismiss();
                                            }
                                        };
                                        timer = new Timer();
                                        timer.schedule(task, 1000);
                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ProductActivity.this);
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
                        RequestQueue queue = Volley.newRequestQueue(ProductActivity.this);
                        queue.add(loginRequest);

                        //---





                }
            }
        });

        button=(Button)findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(email.equals("null")) {
                    new AlertDialog.Builder(ProductActivity.this)
                            .setTitle("尚未登入")
                            .setMessage( "親愛的主人!請您先登入唷(*´∀`)~♥")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent(ProductActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })

                            .show();
                }
                else{
                    total = (int) num * Integer.parseInt(price);
                    insert();
                    Toast.makeText(ProductActivity.this, "成功加入購物車", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getproduct();

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    public void getproduct() {

        //抓取資料
        mQueue = Volley.newRequestQueue(ProductActivity.this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                            commodity = new JSONArray(s).getJSONObject(0).getString("commodity");
                            price = new JSONArray(s).getJSONObject(0).getString("price");
                            ingredient = new JSONArray(s).getJSONObject(0).getString("ingredient");
                            recommend = new JSONArray(s).getJSONObject(0).getString("recommend");
                            feature1 = new JSONArray(s).getJSONObject(0).getString("feature1");
                            feature2 = new JSONArray(s).getJSONObject(0).getString("feature2");
                            feature3 = new JSONArray(s).getJSONObject(0).getString("feature3");
                            img = new JSONArray(s).getJSONObject(0).getString("photourl");
                            id = new JSONArray(s).getJSONObject(0).getString("id");
                            setphoto();



                            textView.setText(commodity);
                            textView2.setText("\n成分：\n"+ingredient+"\n\n適用對象：\n"+recommend+"\n\n特色1：\n"+feature1+"\n\n特色2：\n"+feature2+"\n\n特色3：\n"+feature3);
                            textView3.setText("NT$"+price);

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
                map.put("query_string", "SELECT * FROM commodity where commodity='"+commodity+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    public void getshop(){

        //抓取資料
        mQueue = Volley.newRequestQueue(ProductActivity.this);
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
                            }

                            for(int i=0;i<30;i++){
                                String name = new JSONArray(s).getJSONObject(i).getString("name");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String num = new JSONArray(s).getJSONObject(i).getString("quantity");
                                String img = new JSONArray(s).getJSONObject(i).getString("photourl");
                                arr[i]=name;
                                arr2[i]=price;
                                arr3[i]=num;
                                arr4[i]=img;
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
                map.put("query_string", "SELECT * FROM shopping where email='"+email+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    public void insert(){
        //
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
                map.put("query_string", "INSERT INTO `shopping`(`email`,`name`,`quantity`,`price`,`photourl`,`commodity_id`) VALUES ( '"+email+"'  ,  '"+commodity+"'  , '"+num+"'  , '"+Integer.toString(total)+"' , '"+img+"', '"+id+"');");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

        //抓取資料
        mQueue = Volley.newRequestQueue(ProductActivity.this);
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
                            }

                            for(int i=0;i<30;i++){
                                String name = new JSONArray(s).getJSONObject(i).getString("name");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String num = new JSONArray(s).getJSONObject(i).getString("quantity");
                                String img = new JSONArray(s).getJSONObject(i).getString("photourl");
                                arr[i]=name;
                                arr2[i]=price;
                                arr3[i]=num;
                                arr4[i]=img;
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
                map.put("query_string", "SELECT * FROM shopping where email='"+email+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }


    public void setphoto(){
        //設定網路圖片
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                imageView.setImageBitmap(result);
                super.onPostExecute(result);
            }

        }.execute(img);
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
