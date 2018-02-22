package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/11/20.
 */

public class checkout extends AppCompatActivity {

    private EditText editText;
    private String pay="ATM轉帳",distribution="郵寄80元",invoice="捐贈發票做公益",email,total,date,count,order_id,password,name;
    private TextView textView;
    private int total2;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest,getRequest2,getRequest3,getRequest4;  //在宣告變數
    private RequestQueue mQueue;
    private String[]arr,arr2,arr3,arr4;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];

        //取得跳頁傳直
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        total = intent.getStringExtra("total");
        count = intent.getStringExtra("count");
        name = intent.getStringExtra("name");

        total2=Integer.parseInt(total)+80;
        textView=(TextView) findViewById(R.id.textView7);
        textView.setText("$"+ Integer.toString(total2));

        editText=(EditText)findViewById( R.id.editText);
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        RadioGroup rg2 = (RadioGroup) findViewById(R.id.radioGroup2);
        RadioGroup rg3 = (RadioGroup) findViewById(R.id.radioGroup3);

        rg.setOnCheckedChangeListener(listener);
        rg2.setOnCheckedChangeListener(listener);
        rg3.setOnCheckedChangeListener(listener);

        getshop();

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

                Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

                date = formatter.format(curDate);
                // TODO Auto-generated method stub

                insert();
                progressDialog = ProgressDialog.show(checkout.this, "", "請稍後", true);
                TimerTask task2 = new TimerTask(){
                    public void run(){
                      getid();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task2, 500);

                TimerTask task = new TimerTask(){
                   public void run(){
                       //execute the task

                       mQueue = Volley.newRequestQueue(checkout.this);
                       getRequest = new StringRequest(Request.Method.POST, mUrl,
                               new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String s) {

                                       Intent intent = new Intent(checkout.this, checkout2.class);
                                       intent.putExtra("email", email);
                                       intent.putExtra("name", name);
                                       intent.putExtra("password", password);
                                       intent.putExtra("count", count);
                                       intent.putExtra("commodity_id", arr);
                                       intent.putExtra("num", arr2);
                                       intent.putExtra("price", arr3);
                                       intent.putExtra("fodder_id", arr4);
                                       intent.putExtra("order_id", order_id);
                                       intent.putExtra("pay", pay);
                                       startActivity(intent);

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
                       }
                   };
                timer = new Timer();
                timer.schedule(task, 1000);



            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }


    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub

            switch (checkedId) {
                case R.id.radio1:
                    pay="ATM轉帳";
                    break;
                case R.id.radio2:
                    pay="貨到付款";
                    break;
                case R.id.radio3:
                    distribution="郵寄80元";
                    break;
                case R.id.radio4:
                    distribution="黑貓宅急便80元";
                    break;
                case R.id.radio5:
                    invoice="捐贈發票做公益";
                    break;
                case R.id.radio6:
                    invoice="開立二聯式紙本發票";
                    break;
            }

        }

    };

    public void getshop(){
        //抓取資料
        mQueue = Volley.newRequestQueue(checkout.this);
        getRequest4 = new StringRequest(Request.Method.POST, mUrl,
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
                                String id = new JSONArray(s).getJSONObject(i).getString("commodity_id");
                                String id2 = new JSONArray(s).getJSONObject(i).getString("fodder_id");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String num = new JSONArray(s).getJSONObject(i).getString("quantity");

                                arr[i]=id;
                                arr2[i]=num;
                                arr3[i]=price;
                                arr4[i]=id2;

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

        mQueue.add(getRequest4);
        //---
    }

    public void insert() {
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
                map.put("query_string", "INSERT INTO `order2`(`pay`,`distribution`,`invoice`,`remark`,`date`,`email`,`price`,`schedule`) VALUES ( '" + pay + "'  ,  '" + distribution + "'  , '" + invoice + "'  , '" + editText.getText() + "' , '" + date + "', '" + email + "', '" + Integer.toString(total2) + "','確認中');");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---



    }

    public void getid(){
        //
        mQueue = Volley.newRequestQueue(this);
        getRequest2 = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                            for (int i = 0; i < 30; i++) {
                                order_id = new JSONArray(s).getJSONObject(i).getString("order_id");
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
                map.put("query_string", "SELECT * FROM order2 where email='" + email + "'");
                return map;
            }

        };

        mQueue.add(getRequest2);
        //---
    }


}
