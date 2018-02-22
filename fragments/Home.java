package info.androidhive.materialtabs.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.MainActivity;
import info.androidhive.materialtabs.activity.ProductActivity;


public class Home extends Fragment implements ViewPager.OnPageChangeListener {

    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private Map<String, String> params;
    private StringRequest getRequest;
    private RequestQueue mQueue;
    private RelativeLayout rl;
    public  String[] arr1,arr2,arr3,arr4;
    private String email="null",name,password,kind="0";
    private TextView textView;
    private int count=0;
    private Button button1,button2,button3,button4,button5,button6;
    private TextView textView1,textView2;
    ProgressDialog progressDialog;

    private ViewPager viewPager;
    private ArrayList<View> list;
    private ImageView imageView;
    private ImageView[] imageViews;
    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem = 0;//当前页面

    public Home() {
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
        //View view = inflater.inflate(R.layout.layout_home, container, false);
        //Fragment回传值给Activity
        return inflater.inflate(R.layout.layout_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        list = new ArrayList<View>();
        list.add(inflater.inflate(R.layout.item01, null));
        list.add(inflater.inflate(R.layout.item02, null));
        list.add(inflater.inflate(R.layout.item03, null));
        list.add(inflater.inflate(R.layout.item04, null));
        list.add(inflater.inflate(R.layout.item05, null));
        list.add(inflater.inflate(R.layout.item06, null));

        imageViews = new ImageView[list.size()];

        ViewGroup group = (ViewGroup)this.getView().findViewById(R.id.viewGroup);
        viewPager = (ViewPager)this.getView().findViewById(R.id.viewPager);

        for(int i=0; i<list.size(); i++){
            imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(40,40));

            imageViews[i] = imageView;

            if(i == 0){
                imageView.setBackgroundResource(R.drawable.dot_seleted);
            }else{
                imageView.setBackgroundResource(R.drawable.dot_unselected);
            }

            group.addView(imageView);
        }

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);


        arr1 = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];

        //取得值
        if(((MainActivity)getActivity()).getName()!=null) {
            name = ((MainActivity) getActivity()).getName();
            email = ((MainActivity) getActivity()).getEmail();
            password = ((MainActivity) getActivity()).getPassword();
        }
        if(((MainActivity)getActivity()).getKind()!=null) {
            kind = ((MainActivity) getActivity()).getKind();
        }

        rl = (RelativeLayout) this.getView().findViewById(R.id.rl);
        textView=(TextView)this.getView().findViewById(R.id.textView2);

        //以下漏漏長為分類按鈕
        button1=(Button)this.getView().findViewById(R.id.button1);
        button2=(Button)this.getView().findViewById(R.id.button2);
        button3=(Button)this.getView().findViewById(R.id.button3);


        if(kind.equals("0")){
            button1.setTextColor(0xFFFFFFFF);
            button1.getBackground().setColorFilter(0xFFFE9B00, PorterDuff.Mode.MULTIPLY );
            button2.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );
            button3.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );

        }
        if(kind.equals("狗狗")){
            button2.setTextColor(0xFFFFFFFF);
            button2.getBackground().setColorFilter(0xFFFE9B00, PorterDuff.Mode.MULTIPLY );
            button1.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );
            button3.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );

        }

        if(kind.equals("貓咪")){
            button3.setTextColor(0xFFFFFFFF);
            button3.getBackground().setColorFilter(0xFFFE9B00, PorterDuff.Mode.MULTIPLY );
            button1.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );
            button2.getBackground().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY );

        }




        button1.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                kind="0";
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("kind",kind);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                startActivity(intent);
                getActivity().finish();

            }

        });
        button2.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {


                // TODO Auto-generated method stub
                kind="狗狗";
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("kind",kind);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                startActivity(intent);
                getActivity().finish();

            }

        });
        button3.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                kind="貓咪";
                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("kind",kind);
                intent.putExtra("email",email);
                intent.putExtra("name",name);
                intent.putExtra("password",password);
                startActivity(intent);
                getActivity().finish();

            }

        });

        //漏漏長分類按鈕結束


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
                            }

                            //最外層迴圈...把資料放進陣列裡面
                            int z = 0;
                            for(int i=0;i<30;i++) {

                                String name2 = new JSONArray(s).getJSONObject(i).getString("commodity");
                                String price = new JSONArray(s).getJSONObject(i).getString("price");
                                String img = new JSONArray(s).getJSONObject(i).getString("photourl");
                                String id = new JSONArray(s).getJSONObject(i).getString("id");
                                arr1[i] = name2;
                                arr2[i] = price;
                                arr3[i] = img;
                                arr4[i] = id;

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
                                            imgV.setBackgroundColor(Color.parseColor("#FFDDAA"));
                                            //設置對象id
                                            imgV.setId(j);
                                            //給按鈕增加監聽事件
                                            imgV.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View arg0) {
                                                    Intent intent = new Intent(getActivity(), ProductActivity.class);
                                                    intent.putExtra("commodity", arr1[imgV.getId()]);
                                                    intent.putExtra("email", email);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("password", password);
                                                    startActivity(intent);
                                                }
                                            });
                                            RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(500, 500); // 物件大小
                                            layoutParams1.topMargin = x * 510 + 600;          //與上下左右間隔
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
                                                    Intent intent = new Intent(getActivity(), ProductActivity.class);
                                                    intent.putExtra("commodity", arr1[btnLesson.getId()]);
                                                    intent.putExtra("email", email);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("password", password);
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
                                            layoutParams.topMargin = x * 510 + 600;          //與上下左右間隔
                                            layoutParams.leftMargin = 10 + y * 550;
                                            rl.addView(btnLesson, layoutParams);  //新增物件
                                        }
                                    }

                                    //第二層迴圈...把陣列放到動態新增裡面--新增名稱欄位
                                    for (int j = 0; j < count; j++) {

                                        int x = j / 2;
                                        int y = j % 2;

                                        if(j==z) {

                                            textView1 = new TextView(getActivity());
                                            textView1.setText(arr1[j]);
                                            textView1.setTextSize(18);
                                            textView1.setTextColor(Color.parseColor("#000000"));
                                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(470, 70);
                                            layoutParams.topMargin = x * 510 + 960;          //與上下左右間隔
                                            layoutParams.leftMargin = 30 + y * 550;
                                            rl.addView(textView1, layoutParams);
                                        }
                                    }

                                    //第二層迴圈...把陣列放到動態新增裡面--新增價格欄位
                                    for (int j = 0; j < count; j++) {

                                        int x = j / 2;
                                        int y = j % 2;

                                        if(j==z) {

                                            textView2 = new TextView(getActivity());
                                            textView2.setText("$" + arr2[j]);
                                            textView2.setTextSize(18);
                                            textView2.setTextColor(Color.parseColor("#CC0000"));
                                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(500, 150);
                                            layoutParams.topMargin = x * 510 + 1020;          //與上下左右間隔
                                            layoutParams.leftMargin = 30 + y * 550;
                                            rl.addView(textView2, layoutParams);
                                        }
                                    }
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
                if(kind.equals("0")) {
                    map.put("query_string", "SELECT * FROM commodity");
                }
                else{
                    map.put("query_string", "SELECT * FROM commodity where class='"+kind+"'");
                }
                return map;
            }

        };

        mQueue.add(getRequest);

        //---抓取資料到這裡結束

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


    //以下為圖片滑動
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

        setImageBackground(arg0%list.size());
    }


    private void setImageBackground(int selectItems){
        currentItem = selectItems;

        for(int i=0; i<imageViews.length; i++){
            if(i == selectItems){
                imageViews[i].setBackgroundResource(R.drawable.dot_seleted);
            }else{
                imageViews[i].setBackgroundResource(R.drawable.dot_unselected);
            }
        }


    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // 为每页添加点击监听，初始化音乐并点击时播放，并保证每次点击都可以重新播放
            switch (position)
            {
                case 0:
                    list.get(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            initMediaPlayer(0);
                        }
                    });
                    break;
                case 1:
                    list.get(1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            initMediaPlayer(1);
                        }
                    });
                    break;
                case 2:
                    list.get(2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            initMediaPlayer(2);
                        }
                    });
                    break;
                case 3:
                    list.get(3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            initMediaPlayer(3);
                        }
                    });
                    break;
                case 4:
                    list.get(4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            initMediaPlayer(4);

                        }
                    });
                    break;
                default:
                    break;
            }
            ((ViewPager) container).addView(list.get(position%list.size()),0);
            return list.get(position%list.size());
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }


        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(list.get(position%list.size()));
        }


    }

    private void initMediaPlayer(int i){
        // 初始化对于页面的音乐
        try {
            switch (i)
            {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:
                    break;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}