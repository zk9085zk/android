package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.materialtabs.Configuration;
import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/12/6.
 */

public class Historymenu2 extends AppCompatActivity {

    private Spinner spinner,spinner2;
    private ArrayAdapter<String> lunchList,lunchList2;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private double num=0;
    private long num2=0;
    private String email="null",name,password,kind="0",petname,fodderid,petweight,foddername,fodderprice,img,formula,price,pet_id;
    private String[] lunch = {"   2", " 2.5", "   3", "   5"};
    private String[] lunch2 = {"   1", "   2", "   3", "   4", "   5","   6","   7","   8","   9"," 10"};
    private int protein=60, heat=68,fiber=75,calcium=65,vitamins=70,total;
    private Button button1,button2;
    private ProgressDialog progressDialog;
    public  String[] arr,arr2,arr3,arr4,arr5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historymenu2);

        //標題列
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];

        //取得值
        Intent intent = this.getIntent();
        email = intent.getStringExtra("email");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");

        fodderid= intent.getStringExtra("fodderid");
        foddername = intent.getStringExtra("foddername");
        fodderprice= intent.getStringExtra("price");
        img= intent.getStringExtra("img");
        pet_id= intent.getStringExtra("petid");

        TextView textView2=(TextView)findViewById(R.id.textView47);
        textView2.setText(pet_id);
        final ImageView imageView=(ImageView)findViewById(R.id.imageView4);
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


        //抓取資料
        mQueue = Volley.newRequestQueue(Historymenu2.this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                                String name = new JSONArray(s).getJSONObject(0).getString("petname");
                                String weight = new JSONArray(s).getJSONObject(0).getString("weight");
                            TextView textView2=(TextView)findViewById(R.id.textView47);
                            textView2.setText(name);
                            TextView textView3=(TextView)findViewById(R.id.textView49);
                            textView3.setText(weight+"公斤");




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
                map.put("query_string", "SELECT * FROM pet where pet_id='"+pet_id+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---



        //抓取資料
        mQueue = Volley.newRequestQueue(Historymenu2.this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                            foddername = new JSONArray(s).getJSONObject(0).getString("foddername");
                            formula = new JSONArray(s).getJSONObject(0).getString("formula");
                            price = new JSONArray(s).getJSONObject(0).getString("price");
                            String x1 = new JSONArray(s).getJSONObject(0).getString("protein");
                            String x2 = new JSONArray(s).getJSONObject(0).getString("heat");
                            String x3 = new JSONArray(s).getJSONObject(0).getString("fiber");
                            String x4 = new JSONArray(s).getJSONObject(0).getString("calcium");
                            String x5 = new JSONArray(s).getJSONObject(0).getString("vitamins");

                            final String[] splittedStr0 =  formula.split(","); // 將字串str0以逗號分割,其結果存在splittedStr0字串陣列中
                            String showSplittedStr0 = "";
                            for (String x : splittedStr0) {
                                showSplittedStr0 = showSplittedStr0  + x + "\n";
                            }

                            protein = Integer.parseInt(x1);
                            heat = Integer.parseInt(x2);
                            fiber = Integer.parseInt(x3);
                            calcium = Integer.parseInt(x4);
                            vitamins = Integer.parseInt(x5);

                            TextView textView1=(TextView)findViewById(R.id.textView39);
                            textView1.setText(foddername);

                            TextView textView4=(TextView)findViewById(R.id.textView52);
                            textView4.setText(showSplittedStr0);
                            TextView textView5=(TextView)findViewById(R.id.textView55);
                            textView5.setText("$"+price+" / 公斤");

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
                map.put("query_string", "SELECT * FROM fodder where fodder_id='"+fodderid+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

        Button button01 = (Button)findViewById(R.id.button12);
        button01.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                Intent intent = new Intent();
                intent.setClass(Historymenu2.this,Configuration.class);
                intent.putExtra("protein", Integer.toString(protein));
                intent.putExtra("heat", Integer.toString(heat));
                intent.putExtra("fiber", Integer.toString(fiber));
                intent.putExtra("calcium", Integer.toString(calcium));
                intent.putExtra("vitamins", Integer.toString(vitamins));
                intent.putExtra("where", "history");

                startActivity(intent);

            }

        });


        //數量選單
        spinner = (Spinner)findViewById(R.id.spinner);
        lunchList = new ArrayAdapter<>(Historymenu2.this, android.R.layout.simple_spinner_item, lunch);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {

                if(arg3==0){
                    num=2;
                }
                if(arg3==1){
                    num=2.5;
                }
                if(arg3==2){
                    num=3;
                }
                if(arg3==3){
                    num=5;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //數量選單
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        lunchList2 = new ArrayAdapter<>(Historymenu2.this, android.R.layout.simple_spinner_item, lunch2);
        spinner2.setAdapter(lunchList2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                num2=arg3+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        button1=(Button)findViewById(R.id.button7);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                    total = (int) ((num * num2) * Integer.parseInt(price));

                    insert();
                    progressDialog = ProgressDialog.show(Historymenu2.this, "", "請稍後", true);

                                    TimerTask task2 = new TimerTask(){
                                        public void run(){
                                            getshop();
                                        }
                                    };
                                    Timer timer = new Timer();
                                    timer.schedule(task2, 500);

                                    TimerTask task = new TimerTask(){
                                        public void run(){
                                            Intent intent = new Intent(Historymenu2.this, shoppingCart.class);
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

                }

        });


        button2=(Button)findViewById(R.id.button8);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                    total = (int) ((num * num2) * Integer.parseInt(price));
                    insert();
                    Toast.makeText(Historymenu2.this, "成功加入購物車", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    public void getshop(){

        //抓取資料
        mQueue = Volley.newRequestQueue(Historymenu2.this);
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
                map.put("query_string", "INSERT INTO `shopping`(`email`,`name`,`quantity`,`price`,`photourl`,`fodder_id`) VALUES ( '"+email+"'  ,  '"+foddername+"',  '"+num*num2+"'  , '"+Integer.toString(total)+"',  '"+img+"' ,  '"+fodderid+"'   );");
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
