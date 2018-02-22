package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.materialtabs.R;

public class Historymenu extends AppCompatActivity {

    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private Map<String, String> params;
    private StringRequest getRequest;
    private RequestQueue mQueue;
    private RelativeLayout rl;
    public  String[] arr1,arr2,arr3,arr4,arr5,arr6,arr7;
    private String email="null",name,password,kind="0";
    private TextView textView;
    private int count=0;
    private ImageView imgV;
    private TextView textView1,textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historymenu);

        arr1 = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];

        //標題列
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //取得值
        Intent intent = this.getIntent();
        email = intent.getStringExtra("email");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");


        rl = (RelativeLayout) this.findViewById(R.id.rl);
        textView=(TextView)this.findViewById(R.id.textView2);


        //抓取資料開始
        mQueue = Volley.newRequestQueue(this);
        getRequest = new StringRequest(Request.Method.POST, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {

                            for(int i=0;i<30;i++) {

                                arr1[i]="0";
                                arr2[i]="0";
                                arr3[i]="0";
                                arr4[i]="0";
                                arr7[i]="0";
                            }

                            //最外層迴圈...把資料放進陣列裡面
                            for(int i=0;i<30;i++){
                                String id2 = new JSONArray(s).getJSONObject(i).getString("pet_id");
                                String id = new JSONArray(s).getJSONObject(i).getString("fodder_id");
                                String name2 = new JSONArray(s).getJSONObject(i).getString("foddername");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String img = new JSONArray(s).getJSONObject(i).getString("fodderimg");

                                arr1[i]=name2;
                                arr2[i]=price;
                                arr3[i]=img;
                                arr4[i]=id;
                                arr7[i]=id2;

                                //計算總共有幾筆資料
                                if(!arr1[i].equals("0"))
                                {
                                    count=i+1;
                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增圖片按鈕

                                for(int j = 0 ;j<count;j++) {
                                    int x=j / 2;
                                    int y=j % 2;


                                    final ImageView imgV =new ImageView(Historymenu.this);
                                    imgV.setBackgroundColor(Color.parseColor("#CCEEFF"));
                                    //設置對象id
                                    imgV.setId(j);
                                    //給按鈕增加監聽事件
                                    imgV.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {

                                            Intent intent = new Intent();
                                            intent.setClass(Historymenu.this,Historymenu2.class);

                                            intent.putExtra("foddername", arr1[imgV.getId()]);
                                            intent.putExtra("price", arr2[imgV.getId()]);
                                            intent.putExtra("img", arr3[imgV.getId()]);
                                            intent.putExtra("fodderid", arr4[imgV.getId()]);
                                            intent.putExtra("petname", arr5[imgV.getId()]);
                                            intent.putExtra("petweight", arr6[imgV.getId()]);
                                            intent.putExtra("petid", arr7[imgV.getId()]);
                                            intent.putExtra("email",email);
                                            intent.putExtra("name",name);
                                            intent.putExtra("password",password);
                                            startActivity(intent);

                                        }
                                    });
                                    RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(500, 500); // 物件大小
                                    layoutParams1.topMargin = x*510+20;          //與上下左右間隔
                                    layoutParams1.leftMargin = 10+y*550;
                                    rl.addView(imgV, layoutParams1);  //新增物件

                                    //創造一個新的Button
                                    final ImageButton btnLesson =new ImageButton(Historymenu.this);
                                    //設置對象id
                                    btnLesson.setId(j);
                                    //給按鈕增加監聽事件
                                    btnLesson.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {

                                            Intent intent = new Intent();
                                            intent.setClass(Historymenu.this,Historymenu2.class);

                                            intent.putExtra("foddername", arr1[btnLesson.getId()]);
                                            intent.putExtra("price", arr2[btnLesson.getId()]);
                                            intent.putExtra("img", arr3[btnLesson.getId()]);
                                            intent.putExtra("fodderid", arr4[btnLesson.getId()]);
                                            intent.putExtra("petname", arr5[btnLesson.getId()]);
                                            intent.putExtra("petweight", arr6[btnLesson.getId()]);
                                            intent.putExtra("petid", arr7[btnLesson.getId()]);
                                            intent.putExtra("email",email);
                                            intent.putExtra("name",name);
                                            intent.putExtra("password",password);
                                            startActivity(intent);

                                        }
                                    });

                                    btnLesson.setBackgroundColor(Color.parseColor("#ffffff"));
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 350); // 物件大小

                                    //設定網路圖片
                                    new AsyncTask<String, Void, Bitmap>() {
                                        @Override
                                        protected Bitmap doInBackground(String... params) {
                                            String url = params[0];
                                            return getBitmapFromURL(url);
                                        }

                                        @Override
                                        protected void onPostExecute(Bitmap result) {
                                            btnLesson.setImageBitmap(result);
                                            super.onPostExecute(result);
                                        }

                                    }.execute(arr3[j]);

                                    btnLesson.setImageResource(R.drawable.p1); //設定圖片
                                    btnLesson.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); //等比例居中顯示圖片
                                    layoutParams.topMargin = x*510+20;          //與上下左右間隔
                                    layoutParams.leftMargin = 10+y*550;
                                    rl.addView(btnLesson, layoutParams);  //新增物件

                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增名稱欄位
                                for(int j = 0 ;j<count;j++) {

                                    int x=j / 2;
                                    int y=j % 2;

                                    textView1 = new TextView(Historymenu.this);
                                    textView1.setText(arr1[j]);
                                    textView1.setTextSize(18);
                                    textView1.setTextColor(Color.parseColor("#000000"));
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(470,70);
                                    layoutParams.topMargin = x*510+380;          //與上下左右間隔
                                    layoutParams.leftMargin = 30+y*550;
                                    rl.addView(textView1, layoutParams);

                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增價格欄位
                                for(int j = 0 ;j<count;j++) {

                                    int x=j / 2;
                                    int y=j % 2;

                                    textView2 = new TextView(Historymenu.this);
                                    textView2.setText("$"+arr2[j]+" / 公斤");
                                    textView2.setTextSize(18);
                                    textView2.setTextColor(Color.parseColor("#CC0000"));
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 150);
                                    layoutParams.topMargin = x*510+450;          //與上下左右間隔
                                    layoutParams.leftMargin = 30+y*550;
                                    rl.addView(textView2, layoutParams);

                                }

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
                map.put("query_string", "SELECT * FROM  `fodder` where email= '"+email+"'");
                return map;
            }

        };

        mQueue.add(getRequest);

        //---抓取資料到這裡結束


    }


    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
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
