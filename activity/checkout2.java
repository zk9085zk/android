package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/11/21.
 */

public class checkout2 extends AppCompatActivity {

    private String email,count,order_id,password,name,pay;
    private String[]arr,arr2,arr3,arr4;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest,getRequest2,getRequest3,getRequest4;  //在宣告變數
    private RequestQueue mQueue;
    private String id,num,price;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent intent=new Intent(checkout2.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        count = intent.getStringExtra("count");
        order_id= intent.getStringExtra("order_id");
        pay= intent.getStringExtra("pay");
        arr= intent.getStringArrayExtra("commodity_id");
        arr2= intent.getStringArrayExtra("num");
        arr3= intent.getStringArrayExtra("price");
        arr4= intent.getStringArrayExtra("fodder_id");

        TextView textView=(TextView)findViewById(R.id.textView6);
        if(pay.equals("ATM轉帳")) {
            textView.setText("感謝您!!\n我們將在你轉帳完畢之後儘速出貨");
        }
        else{
            textView.setText("感謝您!!\n我們將會儘速出貨");
        }
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {
                Intent intent=new Intent(checkout2.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
                }});

        for(int i=0;i<Integer.parseInt(count);i++) {
            insert(i);
        }
        delete();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent=new Intent(checkout2.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
        intent.putExtra("email", email);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();

        return super.onOptionsItemSelected(item);
    }


    public void insert(final int i) {

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
                    if(!arr[i].equals("null")) {
                        map.put("query_string", "INSERT INTO `orderdetial_i`(`order_id`,`commodity_id`,`quantity`,`price`) VALUES ( '" + order_id + "'  ,  '" + arr[i] + "'  , '" + arr2[i] + "'  , '" + arr3[i] + "' );");
                    }
                    if(!arr4[i].equals("null")) {
                        map.put("query_string", "INSERT INTO `orderdetial_i`(`order_id`,`fodder_id`,`quantity`,`price`) VALUES ( '" + order_id + "'  ,   '" + arr4[i] + "' , '" + arr2[i] + "'  , '" + arr3[i] + "' );");
                    }

                    return map;
                }

            };

            mQueue.add(getRequest);
            //---

    }
    public void delete() {

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
                map.put("query_string", "DELETE FROM shopping where email='"+email+"'  ");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---


    }


}

