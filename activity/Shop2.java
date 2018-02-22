package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.materialtabs.Configuration;
import info.androidhive.materialtabs.R;

public class Shop2 extends AppCompatActivity {

    private final static String mUrl = "http://140.127.22.42:82/shoppingcart.php";  //沒有刪東西的php
    private StringRequest getRequest;  //在宣告變數
    private RequestQueue mQueue;
    private String img,ingredient,recommend,feature1,feature2,feature3,email,name,password;
    String pet;
    String formula="";
    String et1,pet_id,pet_weight;
    static int protein=60, heat=68,fiber=75,calcium=65,vitamins=70;
    int price=0,sum=0,sum2=0;
    double sum3=0,sum4=0,ctr=0;

    public static int getProtein()
    {
        return protein;
    }
    public static int getHeat()
    {
        return heat;
    }
    public static int getFiber()
    {
        return fiber;
    }
    public static int getCalcium()
    {
        return calcium;
    }
    public static int getVitamins()
    {
        return vitamins;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop2);

        //標題列
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textView33 =(TextView)findViewById(R.id.textView33);
        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        String needs = intent.getStringExtra("needs");
        String text="";
        String needs1 = intent.getStringExtra("needs1");
        String needs2 = intent.getStringExtra("needs2");
        String needs3 = intent.getStringExtra("needs3");
        String needs4 = intent.getStringExtra("needs4");
        String needs5 = intent.getStringExtra("needs5");
        String needs6 = intent.getStringExtra("needs6");
        String needs7 = intent.getStringExtra("needs7");
        String needs8 = intent.getStringExtra("needs8");

        //取得跳頁傳直
        email = intent.getStringExtra("email");
        //et1 = intent.getStringExtra("et1");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");
        pet_id= intent.getStringExtra("pet_id");
        img=intent.getStringExtra("img");
        pet_weight= intent.getStringExtra("pet_weight");
        pet = intent.getStringExtra("pet");



        //單選
        if(needs!=null){
            text=text+needs+"\n";
            formula=formula+needs+",";

            if(formula.substring(0, formula.length() - 1).equals("增重"))
            {
                sum2=100;
                sum3=1.1;

            }
            if(formula.substring(0, formula.length() - 1).equals("肥胖代謝"))
            {
                sum2=125;
                sum3=1.2;
            }
            if(formula.substring(0, formula.length() - 1).equals("血糖管理"))
            {
                sum2=130;
                sum3=1.1;
            }
            if(formula.substring(0, formula.length() - 1).equals("腎臟病護理"))
            {
                sum2=150;
                sum3=1.3;
            }

        }

        //複選
        if(!needs1.equals("0")){
            text=text+needs1+"\n";
            formula=formula+needs1+",";
            sum=sum+135;
            sum4=sum4+1;
            ctr++;
        }
        if(!needs2.equals("0")){
            text=text+needs2+"\n";
            formula=formula+needs2+",";
            sum=sum+90;
            sum4=sum4+1.3;
            ctr++;
        }
        if(!needs3.equals("0")){
            text=text+needs3+"\n";
            formula=formula+needs3+",";
            sum=sum+105;
            sum4=sum4+1.2;
            ctr++;
        }
        if(!needs4.equals("0")){
            text=text+needs4+"\n";
            formula=formula+needs4+",";
            sum=sum+100;
            sum4=sum4+0.9;
            ctr++;
        }
        if(!needs5.equals("0")){
            text=text+needs5+"\n";
            formula=formula+needs5+",";
            sum=sum+140;
            sum4=sum4+1.1;
            ctr++;
        }
        if(!needs6.equals("0")){
            text=text+needs6+"\n";
            formula=formula+needs6+",";
            sum=sum+120;
            sum4=sum4+1.3;
            ctr++;
        }
        if(!needs7.equals("0")){
            text=text+needs7+"\n";
            formula=formula+needs7+",";
           sum=sum+150;
           sum4=sum4+1;
            ctr++;
        }
        if(!needs8.equals("0")){
            text=text+needs8+"\n";
            formula=formula+needs8+",";
            sum=sum+110;
            sum4=sum4+1.2;
            ctr++;
        }

        if(!text.equals("")) {
            textView33.setText(text.substring(0, text.length() - 1)); //顯示text刪除最後一個字
        }

        if(!formula.equals("")) {
            formula.substring(0, formula.length() - 1); //顯示text刪除最後一個字
        }


        //計算價格
        if(sum2>0) {
            price = sum2 * (int) Math.ceil(((Double.parseDouble(pet_weight) / 3)));
        }


        if(sum>0)
        {
            price = (int)(( sum / 2) * (int) Math.ceil(((Double.parseDouble(pet_weight) / 3))));
        }

        TextView textView50 =(TextView) this.findViewById(R.id.textView58);
        textView50.setText("$"+Integer.toString(price)+ " / 公斤");

        //計算營養標示
        if(sum3>0)
        {
            protein =(int)(sum3 *60);
            heat =(int)(sum3 *68);
            fiber =(int)(sum3 *75);
            calcium =(int)(sum3 *65);
            vitamins =(int)(sum3 *70);
        }
        if(sum4>0)
        {
            protein =(int)(sum4 / ctr *60);
            heat =(int)(sum4 / ctr *68);
            fiber =(int)(sum4 / ctr *75);
            calcium =(int)(sum4 / ctr *65);
            vitamins =(int)(sum4 / ctr *70);
        }

        //寵物名
        TextView textView42 =(TextView)findViewById(R.id.textView42);


        textView42.setText(pet);


        Button button1 = (Button)findViewById(R.id.button8);

        button1.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                insert();
                Toast.makeText(Shop2.this, "成功新增菜單", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Shop2.this,MainActivity.class);
                intent.putExtra("pet", pet);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("name", name);
                //切換Activity
                startActivity(intent);
                finish();



            }

        });

        Button button2 = (Button)findViewById(R.id.button12);

        button2.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(Shop2.this,Configuration.class);
                intent.putExtra("where", "shop2");
                startActivity(intent);
            }

        });

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void insert(){
        EditText editText=(EditText)findViewById(R.id.textView44);
        //一句話
        if(editText.getText().toString().equals("")) {
            et1= pet + "的美味菜單";
        }else {
            et1=editText.getText().toString();
        }

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
               map.put("query_string", "INSERT INTO `fodder`(`pet_id`,`email`,`foddername`,`formula`,`fodderimg`,`fodder_weight`,`price`,`protein`,`heat`,`fiber`,`calcium`,`vitamins`) VALUES ( '"+pet_id+"'  , '"+email+"'  ,  '"+et1+"'  , '"+formula+"', '"+img+"' ,'1', '"+price+"', '"+protein+"', '"+heat+"', '"+fiber+"', '"+calcium+"', '"+vitamins+"');");
                return map;
            }
        };
        mQueue.add(getRequest);
        //---
    }

}
