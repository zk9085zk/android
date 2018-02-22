package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/11/1.
 */

public class order extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listview;
    private SimpleAdapter adapter;
    private String email;
    private String name;
    private String password;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private StringRequest getRequest2;  //在宣告變數
    private RequestQueue mQueue;
    private ArrayList list;
    public  String[] arr;
    public  String[] arr2;
    public  String[] arr3;
    public  String[] arr4;
    public  String[] arr5;
    public  String[] arr6;
    public  String[] arr7;
    public  String[] arr8;
    public  String[] arr9,arr10,arr11,arr12;
    private long i;
    private int x=0;
    private  ProgressDialog progressDialog;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];
        arr8 = new String[30];
        arr9 = new String[30];
        arr10 = new String[30];
        arr11 = new String[30];
        arr12 = new String[30];

        //標題列
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //取得跳頁傳直
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        arr= intent.getStringArrayExtra("order_id");
        arr2= intent.getStringArrayExtra("date");
        arr3= intent.getStringArrayExtra("schedule");
        arr4= intent.getStringArrayExtra("price");
        arr5= intent.getStringArrayExtra("pay");
        arr6= intent.getStringArrayExtra("invoice");


        if(!arr[0].equals("0")) {

            TextView textView=(TextView)findViewById(R.id.textView15);
            textView.setVisibility(View.INVISIBLE);

            listview = (ListView) findViewById(R.id.list);
            listview.setVisibility(View.VISIBLE);

            // 清單面版
            adapter = new SimpleAdapter(this, getData(),
                    R.layout.view2content7,
                    new String[]{"id", "date", "schedule", "price"},
                    new int[]{R.id.id, R.id.date, R.id.schedule, R.id.price});
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    ListView listView = (ListView) arg0;
                    i = arg3;
                    data(i);

                            data2(i);



                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {


                                    //抓取資料
                                    mQueue = Volley.newRequestQueue(order.this);
                                    getRequest = new StringRequest(Request.Method.POST, mUrl,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String s) {
                                                    Intent intent = new Intent(order.this, orderDetail.class);
                                                    intent.putExtra("email", email);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("password", password);
                                                    intent.putExtra("date", arr2[(int) i]);
                                                    intent.putExtra("schedule", arr3[(int) i]);
                                                    intent.putExtra("total", arr4[(int) i]);
                                                    intent.putExtra("commodity", arr7);
                                                    intent.putExtra("quantity", arr8);
                                                    intent.putExtra("price", arr9);
                                                    intent.putExtra("foddername", arr10);
                                                    intent.putExtra("fodderweight", arr11);
                                                    intent.putExtra("price2", arr12);
                                                    intent.putExtra("pay", arr5[(int) i]);
                                                    intent.putExtra("invoice", arr6[(int) i]);
                                                    intent.putExtra("id", arr[(int) i]);
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


                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(order.this);
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
                    RequestQueue queue = Volley.newRequestQueue(order.this);
                    queue.add(loginRequest);

                    //---

                    getorder();

                }
            });
        }
    }

    private void data(final long i){
        //抓取資料
        mQueue = Volley.newRequestQueue(order.this);
        getRequest2 = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                            for(int i=0;i<30;i++){
                                arr7[i]="0";
                                arr8[i]="0";
                                arr9[i]="0";
                            }
                            for(int i=0;i<30;i++){
                                String commodity = new JSONArray(s).getJSONObject(i).getString("commodity");
                                String quantity = new JSONArray(s).getJSONObject(i).getString("quantity");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                arr7[i]=commodity;
                                arr8[i]=quantity;
                                arr9[i]=price;

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

                    map.put("query_string", "SELECT * FROM  orderdetial_i AS t1 INNER JOIN order2 AS t2 ON t1.order_id = t2.order_id INNER JOIN commodity AS t3 ON t1.commodity_id = t3.id WHERE t1.order_id = '" + arr[(int) i] + "'");

                return map;
            }

        };

        mQueue.add(getRequest2);
    }

    private void data2(final long i){
        //抓取資料
        mQueue = Volley.newRequestQueue(order.this);
        getRequest2 = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {

                        try {

                            for(int i=0;i<30;i++){
                                arr10[i]="0";
                                arr11[i]="0";
                                arr12[i]="0";
                            }
                            for(int i=0;i<30;i++){
                                String commodity = new JSONArray(s).getJSONObject(i).getString("foddername");
                                String quantity = new JSONArray(s).getJSONObject(i).getString("quantity");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                arr10[i]=commodity;
                                arr11[i]=quantity;
                                arr12[i]=price;



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
                map.put("query_string", "SELECT * FROM  orderdetial_i AS t1 INNER JOIN order2 AS t2 ON t1.order_id = t2.order_id INNER JOIN fodder AS t3 ON t1.fodder_id = t3.fodder_id WHERE t1.order_id = '" + arr[(int) i] + "'");
                return map;
            }

        };

        mQueue.add(getRequest2);
    }

    //ListView內容
    private List getData() {

        list = new ArrayList();

        for(int i=0;i<30;i++){

                Map map;
                map = new HashMap();
                map.put("id", arr[i]);
                map.put("date", arr2[i]);
                map.put("schedule", arr3[i]);
                map.put("price", arr4[i]);
            if(!arr[i].equals("0")) {
                list.add(map);
            }
        }

        return list;
    }

    private void getorder(){
        //抓取資料
        mQueue = Volley.newRequestQueue(order.this);
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
                            }

                            for(int i=0;i<30;i++){
                                String order_id = new JSONArray(s).getJSONObject(i).getString("order_id");
                                String date = new JSONArray(s).getJSONObject(i).getString("date");
                                String schedule = new JSONArray(s).getJSONObject(i).getString("schedule");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String pay = new JSONArray(s).getJSONObject(i).getString("pay");
                                String invoice = new JSONArray(s).getJSONObject(i).getString("invoice");

                                final String[] splittedStr1 = date.split(" ");

                                arr[i]=order_id;
                                arr2[i]=splittedStr1[0];
                                arr3[i]=schedule;
                                arr4[i]=price;
                                arr5[i]=pay;
                                arr6[i]=invoice;

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
                map.put("query_string", "SELECT * FROM order2 where email='"+email+"'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

}

