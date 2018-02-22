package info.androidhive.materialtabs.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/11/1.
 */

public class shoppingCart extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listview;
    private  SimpleAdapter adapter;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private StringRequest getRequest4;  //在宣告變數
    private RequestQueue mQueue;
    private int total=0;
    private ArrayList list;
    private String email;
    private String name;
    private String count;
    private String password;
    private String delete="false";
    public String[] arr;
    public String[] arr2;
    public String[] arr3;
    public String[] arr4;
    public String[] arr5,arr6,arr7;
    private String x;
    private Bitmap[] bit;
    private int z=0,y=0;
    private Button button2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];
        bit=new Bitmap[30];

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
        arr = intent.getStringArrayExtra("commodityname");
        arr2 = intent.getStringArrayExtra("price");
        arr3 = intent.getStringArrayExtra("num");
        arr4 = intent.getStringArrayExtra("img");
        x=intent.getStringExtra("x");

        button2=(Button)findViewById(R.id.button11);
        button2.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent(shoppingCart.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("password", password);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }

        });

        getshop();

        if(!arr[0].equals("0")) {

            final TextView textView=(TextView)findViewById(R.id.textView14);
            textView.setVisibility(View.INVISIBLE);


            TextView textView1=(TextView)findViewById(R.id.textView17);
            textView1.setVisibility(View.VISIBLE);

            TextView textView2=(TextView)findViewById(R.id.textView16);
            textView2.setVisibility(View.VISIBLE);

            TextView textView3=(TextView)findViewById(R.id.total2);
            textView3.setVisibility(View.VISIBLE);

            TextView textView4=(TextView)findViewById(R.id.total);
            textView4.setVisibility(View.VISIBLE);

            Button button=(Button)findViewById(R.id.button2);
            button.setVisibility(View.VISIBLE);

            button2.setVisibility(View.INVISIBLE);

            listview = (ListView)findViewById(R.id.list);
            listview.setVisibility(View.VISIBLE);

            button=(Button)findViewById(R.id.button2);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {


                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    //抓取資料
                                    mQueue = Volley.newRequestQueue(shoppingCart.this);
                                    getRequest = new StringRequest(Request.Method.POST, mUrl,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String s) {
                                                    Intent intent = new Intent(shoppingCart.this, checkout.class);
                                                    intent.putExtra("email", email);
                                                    intent.putExtra("password", password);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("total", Integer.toString(total));
                                                    intent.putExtra("count", count);

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
                                            map.put("query_string", "SELECT * FROM order2 where email='"+email+"'");
                                            return map;
                                        }

                                    };

                                    mQueue.add(getRequest);
                                    //---

                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(shoppingCart.this);
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

                    LoginRequest loginRequest = new LoginRequest(email,password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(shoppingCart.this);
                    queue.add(loginRequest);

                    //---

                }});

            setlist();

            for (int i = 0; i < 30; i++) {
                new AsyncTask<String, Void, Bitmap>() {
                    @Override
                    protected Bitmap doInBackground(String... params) {
                        String url = params[0];
                        return getBitmapFromURL(url);
                    }

                    @Override
                    protected void onPostExecute(Bitmap result) {

                        bit[z] = result;
                        super.onPostExecute(result);
                        setlist();
                        z++;
                    }

                }.execute(arr4[i]);
            }
        }

    }

    //ListView內容
    private void getData2(final int p) {

        //刪除資料

        if (delete.equals("sucess")) {


            mQueue = Volley.newRequestQueue(this);

            getRequest4 = new StringRequest(Request.Method.POST, mUrl,    //然後複製從這裡
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
                    map.put("query_string", x);
                    return map;
                }                                                           //到這裡

            };
            mQueue.add(getRequest4);
            //---
            delete="false";
        }

        //抓取資料
        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {
                    TextView textView = (TextView) findViewById(R.id.total);

                    @Override

                    public void onResponse(String s) {

                        try {

                            for (int i = 0; i < 30; i++) {
                                arr5[i] = "0";
                            }
                            for (int i = 0; i < 30; i++) {
                                String id = new JSONArray(s).getJSONObject(i).getString("shopping_id");
                                arr5[i] = id;

                                x = "DELETE FROM shopping WHERE  shopping_id = '" + arr5[p]+"';" ;

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
                map.put("query_string", "SELECT * FROM shopping where email='" + email + "'");
                return map;
            }

        };

        mQueue.add(getRequest);
        //---

    }

    private void setlist(){

        // 清單面版
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content4,
                new String[] { "title", "price", "num" ,"img" },
                new int[] {	R.id.title, R.id.price,R.id.num, R.id.img })
        {

            //在这个重写的函数里设置 每个 item 中按钮的响应事件
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final int p=position;
                final View view=super.getView(position, convertView, parent);

                Button button=(Button)view.findViewById(R.id.deletebtn);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //警告框的写法
                        new android.app.AlertDialog.Builder(shoppingCart.this)
                                .setTitle("刪除")
                                .setMessage("確定要刪除嗎?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //按下確定
                                        delete="sucess";
                                        getData2(Integer.parseInt(String.valueOf(p)));

                                        progressDialog = ProgressDialog.show(shoppingCart.this, "", "請稍後", true);
                                        TimerTask task2 = new TimerTask(){
                                            public void run(){
                                                getshop();
                                            }
                                        };
                                        Timer timer = new Timer();
                                        timer.schedule(task2, 500);
                                        // Response received from the server
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");

                                                    if (success) {

                                                        //抓取資料
                                                        mQueue = Volley.newRequestQueue(shoppingCart.this);
                                                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String s) {

                                                                        TimerTask task = new TimerTask(){
                                                                            public void run(){
                                                                        Intent intent=new Intent(shoppingCart.this, shoppingCart.class);
                                                                        intent.putExtra("email", email);
                                                                        intent.putExtra("name", name);
                                                                        intent.putExtra("password", password);
                                                                        intent.putExtra("commodityname", arr);
                                                                        intent.putExtra("price", arr2);
                                                                        intent.putExtra("num", arr3);
                                                                        intent.putExtra("img", arr4);
                                                                        intent.putExtra("x",x);
                                                                        startActivity(intent);
                                                                        finish();
                                                                            }
                                                                        };
                                                                        Timer timer = new Timer();
                                                                        timer.schedule(task, 1000);

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

                                                    } else {
                                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(shoppingCart.this);
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

                                        LoginRequest loginRequest = new LoginRequest(email,password, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(shoppingCart.this);
                                        queue.add(loginRequest);

                                        //---

                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                                .create()
                                .show();

                        getData2(Integer.parseInt(String.valueOf(p)));

                    }
                });

                return view;
            }
        };

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

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;

                //Toast.makeText(shoppingCart.this, "ID：" + arg3 + "   選單文字："+ listView.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    //ListView內容
    private List getData() {
        TextView textView = (TextView) findViewById(R.id.total);
        TextView textView2 = (TextView) findViewById(R.id.textView17);
        int j=0;
        list = new ArrayList();
        for (int i = 0; i < 30; i++) {
            Map map = new HashMap();
            map.put("title", arr[i]);
            map.put("price", arr2[i]);
            map.put("num", arr3[i]);

            if(bit[i]!=null) {
                map.put("img", bit[i]);
            }
            else{
                map.put("img", R.drawable.naurto);
            }

            if(y==0) {
                total = total + Integer.valueOf(arr2[i]).intValue();
                textView.setText("$" + Integer.toString(total));
            }

            if (!arr[i].equals("0")) {
                j++;
                count=Integer.toString(j);
                textView2.setText(count);
                list.add(map);
            }

        }
        y++;
        return list;
    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }
    public void getshop(){

        //抓取資料
        mQueue = Volley.newRequestQueue(shoppingCart.this);
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
                                arr6[i]="0";
                                arr7[i]="0";
                            }

                            for(int i=0;i<30;i++){
                                String id = new JSONArray(s).getJSONObject(i).getString("commodity_id");
                                String id2 = new JSONArray(s).getJSONObject(i).getString("fodder_id");
                                String name = new JSONArray(s).getJSONObject(i).getString("name");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String num = new JSONArray(s).getJSONObject(i).getString("quantity");
                                String img = new JSONArray(s).getJSONObject(i).getString("photourl");

                                arr[i]=name;
                                arr2[i]=price;
                                arr3[i]=num;
                                arr4[i]=img;
                                arr6[i]=id;
                                arr7[i]=id2;
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
