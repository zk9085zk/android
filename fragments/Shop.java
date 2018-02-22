package info.androidhive.materialtabs.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.Historymenu;
import info.androidhive.materialtabs.activity.LoginActivity;
import info.androidhive.materialtabs.activity.LoginRequest;
import info.androidhive.materialtabs.activity.MainActivity;
import info.androidhive.materialtabs.activity.Shop1;


public class Shop extends Fragment{

    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private Map<String, String> params;
    private StringRequest getRequest;
    private RequestQueue mQueue;
    private RelativeLayout rl;
    public  String[] arr1,arr2,arr3,arr4,arr5;
    private TextView textView;
    private int count=0;

    private String email="null";
    private String password="null" ;
    String name;
    private Button button,button8;
    private TextView textView5;
    private TextView textView6;
    private TextView textView12;

    private String petname,subspecies,pet_id,img,pet_weight;


    public Shop() {
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

        return inflater.inflate(R.layout.layout_shop, container, false);
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



        button=(Button) this.getView().findViewById(R.id.button);
        textView5=(TextView) this.getView().findViewById(R.id.textView5);
        textView6=(TextView) this.getView().findViewById(R.id.textView6);
        textView12=(TextView) this.getView().findViewById(R.id.textView12);
        button8=(Button) this.getView().findViewById(R.id.button8);



        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), Historymenu.class);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                startActivity(intent);


            }
        });






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判斷是否有登入
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {


                                Intent intent=new Intent(getActivity(), Shop1.class);
                                startActivity(intent);



                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("尚未登入")
                                        .setMessage( "親愛的主人!請您先登入唷(*´∀`)~♥")
                                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(getActivity(), LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })

                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(email,password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(loginRequest);

                //---
            }
        });


        arr1 = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];


        rl = (RelativeLayout) this.getView().findViewById(R.id.rl);
        textView=(TextView)this.getView().findViewById(R.id.textView2);




        //抓取資料開始
        mQueue = Volley.newRequestQueue(getActivity());
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
                                arr5[i]="0";
                            }
                            int z = 0;
                            //最外層迴圈...把資料放進陣列裡面
                            for(int i=0;i<30;i++) {

                                petname = new JSONArray(s).getJSONObject(i).getString("petname");
                                subspecies = new JSONArray(s).getJSONObject(i).getString("subspecies");
                                pet_id = new JSONArray(s).getJSONObject(i).getString("pet_id");
                                img = new JSONArray(s).getJSONObject(i).getString("photourl");
                                pet_weight= new JSONArray(s).getJSONObject(i).getString("weight");

                                arr1[i] = petname;
                                arr2[i] = subspecies;
                                arr3[i] = img;
                                arr4[i] = pet_id;
                                arr5[i] = pet_weight;

                                //計算總共有幾筆資料
                                if (!arr1[i].equals("0")) {
                                    count = i + 1;
                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增圖片按鈕
                                for (int j = 0; j < count; j++) {
                                    int x = j / 2;
                                    int y = j % 2;
                                    if(j==z) {
                                        final ImageView imgV = new ImageView(getActivity());
                                        imgV.setBackgroundColor(Color.parseColor("#CCEEFF"));
                                        imgV.setId(j);
                                        //給按鈕增加監聽事件
                                        imgV.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {
                                                Intent intent = new Intent(getActivity(), Shop1.class);
                                                intent.putExtra("pet_name", arr1[imgV.getId()]);
                                                intent.putExtra("num", imgV.getId());
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);
                                                intent.putExtra("name", name);
                                                intent.putExtra("pet_id", arr4[imgV.getId()]);
                                                intent.putExtra("pet_weight", arr5[imgV.getId()]);
                                                startActivity(intent);
                                            }
                                        });
                                        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(500, 500); // 物件大小
                                        layoutParams1.topMargin = x * 510 + 20;          //與上下左右間隔
                                        layoutParams1.leftMargin = 10 + y * 550;
                                        rl.addView(imgV, layoutParams1);  //新增物件

                                        //創造一個新的Button
                                        final ImageButton btnLesson = new ImageButton(getActivity());
                                        //設置對象id
                                        btnLesson.setId(j);
                                        //給按鈕增加監聽事件
                                        btnLesson.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View arg0) {
                                                Intent intent = new Intent(getActivity(), Shop1.class);
                                                intent.putExtra("pet_name", arr1[btnLesson.getId()]);
                                                intent.putExtra("num", btnLesson.getId());
                                                intent.putExtra("email", email);
                                                intent.putExtra("password", password);
                                                intent.putExtra("name", name);
                                                intent.putExtra("pet_id", arr4[btnLesson.getId()]);
                                                intent.putExtra("pet_weight", arr5[imgV.getId()]);
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

                                        btnLesson.setScaleType(ImageButton.ScaleType.CENTER_INSIDE); //等比例居中顯示圖片
                                        layoutParams.topMargin = x * 510 + 20;          //與上下左右間隔
                                        layoutParams.leftMargin = 10 + y * 550;
                                        rl.addView(btnLesson, layoutParams);  //新增物件
                                    }
                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增名稱欄位
                                for (int j = 0; j < count; j++) {

                                    int x = j / 2;
                                    int y = j % 2;
                                    if(j==z) {
                                        TextView textView1 = new TextView(getActivity());
                                        textView1.setText(arr1[j]);
                                        textView1.setTextSize(18);
                                        textView1.setTextColor(Color.parseColor("#000000"));
                                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(470, 70);
                                        layoutParams.topMargin = x * 510 + 380;          //與上下左右間隔
                                        layoutParams.leftMargin = 30 + y * 550;
                                        rl.addView(textView1, layoutParams);
                                    }
                                }

                                //第二層迴圈...把陣列放到動態新增裡面--新增價格欄位
                                for (int j = 0; j < count; j++) {

                                    int x = j / 2;
                                    int y = j % 2;
                                    if(j==z) {
                                        TextView textView1 = new TextView(getActivity());
                                        textView1.setText(arr2[j]);
                                        textView1.setTextSize(18);
                                        textView1.setTextColor(Color.parseColor("#000000"));
                                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 150);
                                        layoutParams.topMargin = x * 510 + 450;          //與上下左右間隔
                                        layoutParams.leftMargin = 30 + y * 550;
                                        rl.addView(textView1, layoutParams);
                                    }
                                }
                            button.setVisibility(View.INVISIBLE); // 隱藏按鈕
                            textView5.setVisibility(View.VISIBLE); // 顯示文字
                            textView6.setVisibility(View.VISIBLE); // 顯示文字
                            textView12.setVisibility(View.INVISIBLE); // 隱藏文字
                            button8.setVisibility(View.VISIBLE);
                                z++;
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
