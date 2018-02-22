package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/11/14.
 */

public class orderDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private String name,email,date,schedule,total,pay,invoice,id,password;
    private String[] commodity,quantity,price,foddername,fodderweight,price2;
    private SimpleAdapter adapter,adapter2;
    ListView listview,listview2;
    private ArrayList list;
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    public  String[] arr;
    public  String[] arr2;
    public  String[] arr3;
    public  String[] arr4;
    public  String[] arr5;
    public  String[] arr6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);

        commodity = new String[30];
        quantity = new String[30];
        price = new String[30];
        foddername = new String[30];
        fodderweight = new String[30];
        price2 = new String[30];
        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];


        //標題列
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        date = intent.getStringExtra("date");
        schedule = intent.getStringExtra("schedule");
        total = intent.getStringExtra("total");
        commodity = intent.getStringArrayExtra("commodity");
        quantity = intent.getStringArrayExtra("quantity");
        price = intent.getStringArrayExtra("price");
        foddername = intent.getStringArrayExtra("foddername");
        fodderweight = intent.getStringArrayExtra("fodderweight");
        price2 = intent.getStringArrayExtra("price2");
        pay = intent.getStringExtra("pay");
        invoice = intent.getStringExtra("invoice");
        id = intent.getStringExtra("id");



        TextView textView1=(TextView)findViewById(R.id.textView23);
        textView1.setText(name);
        TextView textView2=(TextView)findViewById(R.id.textView22);
        textView2.setText(date);
        TextView textView3=(TextView)findViewById(R.id.textView21);
        textView3.setText(schedule);
        TextView textView4=(TextView)findViewById(R.id.textView29);
        textView4.setText(pay);
        TextView textView5=(TextView)findViewById(R.id.textView28);
        textView5.setText(total);
        if(invoice.equals("捐贈發票做公益")){
            invoice="捐贈發票";
        }
        if(invoice.equals("我要捐贈發票做公益")){
            invoice="捐贈發票";
        }
        if(invoice.equals("開立二聯式紙本發票")){
            invoice="紙本發票";
        }
        TextView textView6=(TextView)findViewById(R.id.textView27);
        textView6.setText(invoice);

        getorder();

        listview = (ListView)findViewById(R.id.listView);
        // 清單面版
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content8,
                new String[] { "id","schedule","price" },
                new int[] {	R.id.id, R.id.schedule, R.id.price });
        listview.setAdapter(adapter);

        listview2 = (ListView)findViewById(R.id.listView2);
        // 清單面版
        adapter2 = new SimpleAdapter(this, getData2(),
                R.layout.view2content10,
                new String[] { "id","schedule","price" },
                new int[] {	R.id.id, R.id.schedule, R.id.price });
        listview2.setAdapter(adapter2);
    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }


    private List getData() {

        list = new ArrayList();

        for(int i=0;i<30;i++){
                Map map;
                map = new HashMap();
                map.put("id", commodity[i]);
                map.put("schedule", quantity[i]);
                map.put("price", price[i]);
            if(!commodity[i].equals("0")) {
                list.add(map);
            }
        }

        return list;
    }
    private List getData2() {

        list = new ArrayList();

        for(int i=0;i<30;i++){
            Map map;
            map = new HashMap();
            map.put("id", foddername[i]);
            map.put("schedule", fodderweight[i]);
            map.put("price", price2[i]+"/公斤");
            if(!foddername[i].equals("0")) {
                list.add(map);
            }
        }

        return list;
    }

    private void getorder(){
        //抓取資料
        mQueue = Volley.newRequestQueue(orderDetail.this);
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
}
