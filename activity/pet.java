package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.materialtabs.R;

/**
 * Created by 柯柏任 on 2016/11/8.
 */

public class pet extends AppCompatActivity {

    private Toolbar toolbar;
    ListView listview;
    private SimpleAdapter adapter;
    private String email,name,password;
    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
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
    private Bitmap[] bit;
    private int z=0;

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
        setContentView(R.layout.activity_pet);

        arr = new String[30];
        arr2 = new String[30];
        arr3 = new String[30];
        arr4 = new String[30];
        arr5 = new String[30];
        arr6 = new String[30];
        arr7 = new String[30];
        arr8 = new String[30];
        bit = new Bitmap[30];

        Button button=(Button)findViewById(R.id.button2);
        button.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub

                Intent intent=new Intent(pet.this, petInsert.class);
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
                finish();

            }

        });

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
        arr = intent.getStringArrayExtra("petname");
        arr2 = intent.getStringArrayExtra("birthday");
        arr3 = intent.getStringArrayExtra("sex");
        arr4 = intent.getStringArrayExtra("subspecies");
        arr5 = intent.getStringArrayExtra("haircolor");
        arr6 = intent.getStringArrayExtra("pet_id");
        arr7 = intent.getStringArrayExtra("img");
        arr8 = intent.getStringArrayExtra("weight");


        if(!arr[0].equals("0")) {

            TextView textView=(TextView)findViewById(R.id.textView13);
            textView.setVisibility(View.INVISIBLE);

            listview = (ListView)findViewById(R.id.list);
            listview.setVisibility(View.VISIBLE);

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

                }.execute(arr7[i]);
            }
        }

    }

    //ListView內容
    private List getData() {
        list = new ArrayList();
        for(int i=0;i<30;i++) {
            Map map;
            map = new HashMap();
            map.put("title", arr[i]);
            if(bit[i]!=null) {
                map.put("img", bit[i]);
            }
            else{
                map.put("img", R.drawable.dog2);
            }
            if(!arr[i].equals("0")) {
                list.add(map);
            }
        }
        return list;

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    public void setlist(){

        // 清單面版
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content5,
                new String[] { "title","img" },
                new int[] {	R.id.title, R.id.img });

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
                Intent intent=new Intent(pet.this, petDetail.class);
                intent.putExtra("email", email);
                intent.putExtra("name", name);
                intent.putExtra("password", password);
                intent.putExtra("petname", arr[(int)arg3]);
                intent.putExtra("birthday", arr2[(int)arg3]);
                intent.putExtra("sex", arr3[(int)arg3]);
                intent.putExtra("subspecies", arr4[(int)arg3]);
                intent.putExtra("haircolor", arr5[(int)arg3]);
                intent.putExtra("pet_id", arr6[(int)arg3]);
                intent.putExtra("img", arr7[(int)arg3]);
                intent.putExtra("weight", arr8[(int)arg3]);
                startActivity(intent);
                finish();

            }
        });


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
