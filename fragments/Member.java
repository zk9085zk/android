package info.androidhive.materialtabs.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.LoginActivity;
import info.androidhive.materialtabs.activity.LoginRequest;
import info.androidhive.materialtabs.activity.MainActivity;
import info.androidhive.materialtabs.activity.RegisterActivity;
import info.androidhive.materialtabs.activity.editMember;
import info.androidhive.materialtabs.activity.order;
import info.androidhive.materialtabs.activity.pet;
import info.androidhive.materialtabs.activity.question;
import info.androidhive.materialtabs.activity.shoppingCart;


public class Member extends Fragment{

    private ImageView imageView;
    private ImageView editmember;
    private SimpleAdapter adapter;
    private ListView listView2;
    private TextView textView;
    private String email="null";
    private String password="null" ;
    private String name="請登入";
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private String img="null";
    public  String[] arr;
    public  String[] arr2;
    public  String[] arr3;
    public  String[] arr4;
    public  String[] arr5;
    public  String[] arr6;
    public  String[] arr7;
    public  String[] arr8;
    public  String[] arr9;
    private Bitmap bit;

    public Member() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_member, container, false);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //取得值
        if(((MainActivity)getActivity()).getName()!=null) {
            name = ((MainActivity) getActivity()).getName();
            email = ((MainActivity) getActivity()).getEmail();
            password = ((MainActivity) getActivity()).getPassword();
        }

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];
        arr8 = new String[30];
        arr9 = new String[30];


        ImageView imageView = (ImageView) this.getView().findViewById(R.id.imageView);
        imageView.setAlpha(75);

        getPhoto();

        textView =(TextView) this.getView().findViewById(R.id.textView);
        textView.setText(name);

        Button button1=(Button)this.getView().findViewById(R.id.loginbtn);
        final Button button2=(Button)this.getView().findViewById(R.id.registerbtn);

        if(email!="null"){
            button1.setVisibility(View.INVISIBLE);
            button2.setText("登出");
        }

        //跳到登入頁

        button1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);


            }

        });
        //跳到註冊頁

        button2.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(button2.getText().toString().equals("註冊")){
                    // TODO Auto-generated method stub
                    Intent intent=new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);

                }
                else{
                    // TODO Auto-generated method stub

                    AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                      builder.setMessage("你是帥哥或正妹嗎?");
                      builder.setTitle("你好");
                      builder.setPositiveButton("不是", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Intent intent=new Intent(getActivity(), MainActivity.class);
                               startActivity(intent);
                               getActivity().finish();
                               }
                          });
                      builder.setNegativeButton("是", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               }
                          });
                      builder.create().show();



                }

            }

        });

        //按下頭像跳頁
        editmember = (ImageView)this.getView().findViewById(R.id.roundImageView);
        editmember.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    // 判斷是否有登入
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {

                                    mQueue = Volley.newRequestQueue(getActivity());
                                    getRequest = new StringRequest(Request.Method.POST, mUrl,
                                            new Response.Listener<String>() {

                                                @Override

                                                public void onResponse(String s) {

                                                    try {

                                                        String name = new JSONArray(s).getJSONObject(0).getString("name");
                                                        String nickname = new JSONArray(s).getJSONObject(0).getString("nickname");
                                                        String phone = new JSONArray(s).getJSONObject(0).getString("cellphone1");
                                                        String birthday = new JSONArray(s).getJSONObject(0).getString("birthday");
                                                        String sex = new JSONArray(s).getJSONObject(0).getString("sex");
                                                        String address = new JSONArray(s).getJSONObject(0).getString("address");

                                                        Intent intent = new Intent(getActivity(), editMember.class);
                                                        intent.putExtra("name", name);
                                                        intent.putExtra("email", email);
                                                        intent.putExtra("password", password);
                                                        intent.putExtra("nickname", nickname);
                                                        intent.putExtra("phone", phone);
                                                        intent.putExtra("birthday", birthday);
                                                        intent.putExtra("sex", sex);
                                                        intent.putExtra("address", address);

                                                        startActivity(intent);
                                                        getActivity().finish();

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
                                            map.put("query_string", "SELECT * FROM member where email='" + email + "'");
                                            return map;
                                        }

                                    };

                                    mQueue.add(getRequest);


                                } else {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(loginRequest);

                    //---
            }});



        if(!email.equals("null")) {
            TextView textView=(TextView)this.getView().findViewById(R.id.textView11);
            listView2 = (ListView) this.getView().findViewById(R.id.listView);
            listView2.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
            // 清單面版
            adapter = new SimpleAdapter(getActivity(), getData(),
                    R.layout.view2content,
                    new String[]{"title", "info", "img"},
                    new int[]{R.id.title, R.id.info, R.id.img});
            listView2.setAdapter(adapter);
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    ListView listView = (ListView) arg0;
                    if (arg3 == 0) {
                        //--

                        // 判斷是否有登入
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {

                                        mQueue = Volley.newRequestQueue(getActivity());
                                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                                new Response.Listener<String>() {

                                                    @Override

                                                    public void onResponse(String s) {

                                                        try {

                                                            String name = new JSONArray(s).getJSONObject(0).getString("name");
                                                            String nickname = new JSONArray(s).getJSONObject(0).getString("nickname");
                                                            String phone = new JSONArray(s).getJSONObject(0).getString("cellphone1");
                                                            String birthday = new JSONArray(s).getJSONObject(0).getString("birthday");
                                                            String sex = new JSONArray(s).getJSONObject(0).getString("sex");
                                                            String address = new JSONArray(s).getJSONObject(0).getString("address");

                                                            Intent intent = new Intent(getActivity(), editMember.class);
                                                            intent.putExtra("name", name);
                                                            intent.putExtra("email", email);
                                                            intent.putExtra("password", password);
                                                            intent.putExtra("nickname", nickname);
                                                            intent.putExtra("phone", phone);
                                                            intent.putExtra("birthday", birthday);
                                                            intent.putExtra("sex", sex);
                                                            intent.putExtra("address", address);

                                                            startActivity(intent);
                                                            getActivity().finish();

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
                                                map.put("query_string", "SELECT * FROM member where email='" + email + "'");
                                                return map;
                                            }

                                        };

                                        mQueue.add(getRequest);


                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);

                        //---


                    } else if (arg3 == 1) {
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
                                        mQueue = Volley.newRequestQueue(getActivity());
                                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {

                                                        Intent intent = new Intent(getActivity(), pet.class);
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
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);

                        //---

                    } else if (arg3 == 2) {
                        //--

                        getshop();
                        // Response received from the server
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {

                                        //抓取資料
                                        mQueue = Volley.newRequestQueue(getActivity());
                                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {

                                                        Intent intent = new Intent(getActivity(), shoppingCart.class);
                                                        intent.putExtra("email", email);
                                                        intent.putExtra("name", name);
                                                        intent.putExtra("password", password);
                                                        intent.putExtra("commodityname", arr);
                                                        intent.putExtra("price", arr2);
                                                        intent.putExtra("num", arr3);
                                                        intent.putExtra("img", arr4);

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
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);

                        //---

                    } else if (arg3 == 3) {
                        //--

                        getorder();
                        // Response received from the server
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {


                                        //抓取資料
                                        mQueue = Volley.newRequestQueue(getActivity());
                                        getRequest = new StringRequest(Request.Method.POST, mUrl,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String s) {

                                                        Intent intent = new Intent(getActivity(), order.class);
                                                        intent.putExtra("name", name);
                                                        intent.putExtra("email", email);
                                                        intent.putExtra("password", password);
                                                        intent.putExtra("order_id", arr);
                                                        intent.putExtra("date", arr2);
                                                        intent.putExtra("schedule", arr3);
                                                        intent.putExtra("price", arr4);
                                                        intent.putExtra("pay", arr5);
                                                        intent.putExtra("invoice", arr6);
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
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);

                        //---


                    } else if (arg3 == 4) {
                        //--


                        // Response received from the server
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        String password = jsonResponse.getString("password");
                                        String name = jsonResponse.getString("name");
                                        String email = jsonResponse.getString("email");

                                        Intent intent = new Intent(getActivity(), question.class);
                                        intent.putExtra("name", name);
                                        intent.putExtra("email", email);
                                        intent.putExtra("password", password);
                                        startActivity(intent);

                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
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
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(loginRequest);

                        //---

                    }
                }
            });

        }
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

    //取得照片
    private void getPhoto(){
    if(!email.equals("null")) {

        mQueue = Volley.newRequestQueue(getActivity());
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String s) {
                        try {
                            img = new JSONArray(s).getJSONObject(0).getString("img");
                            if(!img.equals("")) {

                                setPhoto();
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

        final ImageView imageView1 = (ImageView) this.getView().findViewById(R.id.roundImageView);
        //建立一個AsyncTask執行緒進行圖片讀取動作，並帶入圖片連結網址路徑

        new AsyncTask<String, Void, Bitmap>() {
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            return getBitmapFromURL(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView1.setImageBitmap(result);
            bit=result;
            super.onPostExecute(result);
        }
        }.execute(img);

    }

    //ListView內容
    private List getData() {
        ArrayList list = new ArrayList();
        Map map = new HashMap(); map.put("title", "個人資料"); map.put("info", "修改個人資料"); map.put("img", R.drawable.settings); list.add(map);
        map = new HashMap(); map.put("title", "毛小孩"); map.put("info", "查看毛小孩"); map.put("img", R.drawable.pet); list.add(map);
        map = new HashMap(); map.put("title", "購物車"); map.put("info", "查看購物車"); map.put("img", R.drawable.shop); list.add(map);
        map = new HashMap(); map.put("title", "我的訂單"); map.put("info", "查詢訂單紀錄"); map.put("img", R.drawable.order); list.add(map);
        map = new HashMap(); map.put("title", "幫助中心"); map.put("info", "常見問題"); map.put("img", R.drawable.question); list.add(map);
        return list;
    }

    private void getorder(){
        //抓取資料
        mQueue = Volley.newRequestQueue(getActivity());
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

    public void getshop(){

        //抓取資料
        mQueue = Volley.newRequestQueue(getActivity());
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

    public void getpet() {

        //抓取資料
        mQueue = Volley.newRequestQueue(getActivity());
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

}
