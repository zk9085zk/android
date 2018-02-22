package info.androidhive.materialtabs.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.TextView;

import info.androidhive.materialtabs.R;

public class Shop1 extends AppCompatActivity {

    private View v1;
    private View v2;
    private View v3;
    private View v4;
    private View v5;
    private View v6;
    private View v7;
    private View v8;
    private View v9;
    private View v10;
    private View v11;
    private View v12;
    private View v13;
    private View v14;
    private View v15;
    private View v16;
    private View v17;
    private View v18;
    private View v19;
    private View v20;
    private View v21;
    private View v22;
    private View v23;
    private View v24;
    private CheckBox scale;
    private CheckBox reverse;
    private int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0,l=0;
    String pet;
    private String email,password,name,pet_id,pet_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop1);
        v1 = findViewById(R.id.a);
        v2 = findViewById(R.id.a1);
        v3 = findViewById(R.id.b);
        v4 = findViewById(R.id.b1);
        v5 = findViewById(R.id.c);
        v6 = findViewById(R.id.c1);
        v7 = findViewById(R.id.d);
        v8 = findViewById(R.id.d1);
        v9 = findViewById(R.id.e);
        v10 = findViewById(R.id.e1);
        v11 = findViewById(R.id.f);
        v12 = findViewById(R.id.f1);
        v13 = findViewById(R.id.g);
        v14 = findViewById(R.id.g1);
        v15 = findViewById(R.id.h);
        v16 = findViewById(R.id.h1);
        v17 = findViewById(R.id.i);
        v18 = findViewById(R.id.i1);
        v19 = findViewById(R.id.j);
        v20 = findViewById(R.id.j1);
        v21 = findViewById(R.id.k);
        v22 = findViewById(R.id.k1);
        v23 = findViewById(R.id.l);
        v24 = findViewById(R.id.l1);
        scale = (CheckBox) findViewById(R.id.scale);
        reverse = (CheckBox) findViewById(R.id.reverse);

        //標題列
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        //取得傳遞過來的資料
        pet= intent.getStringExtra("pet_name");
        email= intent.getStringExtra("email");
        password= intent.getStringExtra("password");
        name= intent.getStringExtra("name");
        pet_id= intent.getStringExtra("pet_id");
        pet_weight= intent.getStringExtra("pet_weight");

        TextView textView37 =( TextView) this.findViewById(R.id.textView37);
        textView37.setText("");

        //增重
        findViewById(R.id.a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v1, v2, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v1;
                v1 = v2;
                v2 = v;
                a=1;
                regray2();
                regray3();
            }
        });
        findViewById(R.id.a1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v1, v2, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v1;
                v1 = v2;
                v2 = v;
                a=0;

            }
        });
        

        //肥胖代謝
        findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v3, v4, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v3;
                v3 = v4;
                v4 = v;
                b=1;
                regray2();
                regray4();
            }
        });
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v3, v4, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v3;
                v3 = v4;
                v4 = v;
                b=0;
            }
        });

        //血糖管理
        findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v5, v6, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v5;
                v5 = v6;
                v6 = v;
                c=1;
                regray2();
                regray5();
            }
        });
        findViewById(R.id.c1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v5, v6, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v5;
                v5 = v6;
                v6 = v;
                c=0;
            }
        });

        //腎臟病護理
        findViewById(R.id.d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v7, v8, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v7;
                v7 = v8;
                v8 = v;
                d=1;
                regray2();
                regray6();
            }
        });
        findViewById(R.id.d1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v7, v8, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v7;
                v7 = v8;
                v8 = v;
                d=0;
            }
        });

        //消化系統護理
        findViewById(R.id.e).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v9, v10, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v9;
                v9 = v10;
                v10 = v;
                e=1;
                regray();
            }
        });
        findViewById(R.id.e1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v9, v10, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v9;
                v9 = v10;
                v10 = v;
                e=0;
                regray();
            }
        });

        //心臟護理
        findViewById(R.id.f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v11, v12, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v11;
                v11 = v12;
                v12 = v;
                f=1;
                regray();
            }
        });
        findViewById(R.id.f1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v11, v12, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v11;
                v11 = v12;
                v12 = v;
                f=0;
                regray();
            }
        });

        //環境除臭
        findViewById(R.id.g).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v13, v14, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v13;
                v13 = v14;
                v14 = v;
                g=1;
                regray();
            }
        });
        findViewById(R.id.g1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v13, v14, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v13;
                v13 = v14;
                v14 = v;
                g=0;
                regray();
            }
        });

        //泌尿道
        findViewById(R.id.h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v15, v16, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v15;
                v15 = v16;
                v16 = v;
                h=1;
                regray();
            }
        });
        findViewById(R.id.h1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v15, v16, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v15;
                v15 = v16;
                v16 = v;
                h=0;
                regray();
            }
        });

        //關節護理
        findViewById(R.id.i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v17, v18, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v17;
                v17 = v18;
                v18 = v;
                i=1;
                regray();
            }
        });
        findViewById(R.id.i1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v17, v18, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v17;
                v17 = v18;
                v18 = v;
                i=0;
                regray();
            }
        });

        //視力保健
        findViewById(R.id.j).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v19, v20, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v19;
                v19 = v20;
                v20 = v;
                j=1;
                regray();
            }
        });
        findViewById(R.id.j1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v19, v20, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v19;
                v19 = v20;
                v20 = v;
                j=0;
                regray();
            }
        });

        //皮膚護理
        findViewById(R.id.k).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v21, v22, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v21;
                v21 = v22;
                v22 = v;
                k=1;
                regray();
            }
        });
        findViewById(R.id.k1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v21, v22, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v21;
                v21 = v22;
                v22 = v;
                k=0;
                regray();
            }
        });

        //毛髮亮澤
        findViewById(R.id.l).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v23, v24, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v23;
                v23 = v24;
                v24 = v;
                l=1;
                regray();
            }
        });
        findViewById(R.id.l1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flip(v23, v24, scale.isChecked(), reverse.isChecked());
                // 交换v1，v2
                View v = v23;
                v23 = v24;
                v24 = v;
                l=0;
                regray();
            }
        });

    }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    private void flip(final View v1, final View v2, final boolean scale, boolean reverse) {
        final int duration = 300;
        final int degree = reverse ? 90 : -90;
        final int degree2 = -degree;

        final ObjectAnimator a, b;

            final float scaleX = 0.5f;
            final float scaleY = 0.5f;
            a = ObjectAnimator.ofPropertyValuesHolder(v1,
                    PropertyValuesHolder.ofFloat("rotationY", 0, degree),
                    PropertyValuesHolder.ofFloat("scaleX", 1, scaleX),
                    PropertyValuesHolder.ofFloat("scaleY", 1, scaleY));
            b = ObjectAnimator.ofPropertyValuesHolder(v2,
                    PropertyValuesHolder.ofFloat("rotationY", degree2, 0),
                    PropertyValuesHolder.ofFloat("scaleX", scaleX, 1),
                    PropertyValuesHolder.ofFloat("scaleY", scaleY, 1));


        a.setInterpolator(new LinearInterpolator());
        b.setInterpolator(new LinearInterpolator());
        a.setDuration(duration);
        b.setDuration(duration);

        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                if (scale) { // 恢复scale
                    v1.setScaleX(1);
                    v1.setScaleY(1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);

        AnimatorSet set = new AnimatorSet();
        set.play(a).before(b);
        set.start();
    }

    //翻下面的上面變灰
    public void regray(){

        // 交换v1，v2
        if(a==1) {
            flip(v1, v2, scale.isChecked(), reverse.isChecked());
            View v = v1;
            v1 = v2;
            v2 = v;
            a=0;
        }
       if(b==1) {
            flip(v3, v4, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v3;
            v3 = v4;
            v4 = v;
            b=0;
        }
        if(c==1) {
            flip(v5, v6, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v5;
            v5 = v6;
            v6 = v;
            c=0;
        }
           if(d==1) {
            flip(v7, v8, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v7;
            v7 = v8;
            v8 = v;
            d=0;
        }
    }

    //翻上面的下面變灰
    public void regray2(){

        // 交换v1，v2
        if(f==1) {
            flip(v11, v12, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v11;
            v11 = v12;
            v12 = v;
            f=0;
        }
        if(g==1) {
            flip(v13, v14, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v13;
            v13 = v14;
            v14 = v;
            g=0;
        }
        if(h==1) {
            flip(v15, v16, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v15;
            v15 = v16;
            v16 = v;
            h=0;
        }
        if(i==1) {
            flip(v17, v18, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v17;
            v17 = v18;
            v18 = v;
            i=0;
        }
        if(j==1) {
            flip(v19, v20, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v19;
            v19 = v20;
            v20 = v;
           j=0;
        }
        if(k==1) {
            flip(v21, v22, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v21;
            v21 = v22;
            v22 = v;
            k=0;
        }
        if(l==1) {
            flip(v23, v24, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v23;
            v23 = v24;
            v24 = v;
            l=0;
        }
        if(e==1) {
            flip(v9, v10, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v9;
            v9 = v10;
            v10 = v;
            e=0;
        }
    }

    public void regray3() {
        if(b==1) {
            flip(v3, v4, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v3;
            v3 = v4;
            v4 = v;
            b=0;
        }
        if(c==1) {
            flip(v5, v6, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v5;
            v5 = v6;
            v6 = v;
            c=0;
        }
        if(d==1) {
            flip(v7, v8, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v7;
            v7 = v8;
            v8 = v;
            d=0;
        }
    }
    public void regray4() {
        // 交换v1，v2
        if(a==1) {
            flip(v1, v2, scale.isChecked(), reverse.isChecked());
            View v = v1;
            v1 = v2;
            v2 = v;
            a=0;
        }

        if(c==1) {
            flip(v5, v6, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v5;
            v5 = v6;
            v6 = v;
            c=0;
        }
        if(d==1) {
            flip(v7, v8, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v7;
            v7 = v8;
            v8 = v;
            d=0;
        }
    }
    public void regray5() {
// 交换v1，v2
        if(a==1) {
            flip(v1, v2, scale.isChecked(), reverse.isChecked());
            View v = v1;
            v1 = v2;
            v2 = v;
            a=0;
        }
        if(b==1) {
            flip(v3, v4, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v3;
            v3 = v4;
            v4 = v;
            b=0;
        }

        if(d==1) {
            flip(v7, v8, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v7;
            v7 = v8;
            v8 = v;
            d=0;
        }
    }
    public void regray6() {
// 交换v1，v2
        if(a==1) {
            flip(v1, v2, scale.isChecked(), reverse.isChecked());
            View v = v1;
            v1 = v2;
            v2 = v;
            a=0;
        }
        if(b==1) {
            flip(v3, v4, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v3;
            v3 = v4;
            v4 = v;
            b=0;
        }
        if(c==1) {
            flip(v5, v6, scale.isChecked(), reverse.isChecked());
            // 交换v1，v2
            View v = v5;
            v5 = v6;
            v6 = v;
            c=0;
        }

    }


    public void onClick(View view) {

        String at="增重",bt="肥胖代謝",ct="血糖管理",dt="腎臟病護理";
        String et="消化系統護理",ft="心臟護理",gt="環境除臭",ht="泌尿道",it="關節護理",jt="視力保健",kt="皮膚護理",lt="毛髮亮澤";



        //new一個intent物件，並指定Activity切換的class
        Intent intent = new Intent();
        intent.setClass(Shop1.this,Shop3.class);

        //單選
        if(a==1) {
            intent.putExtra("needs", at);//可放所有基本類別
        }
        else if (b==1){
            intent.putExtra("needs", bt);//可放所有基本類別
        }
        else if (c==1){
            intent.putExtra("needs", ct);//可放所有基本類別
        }
        else if (d==1){
            intent.putExtra("needs", dt);//可放所有基本類別
        }

        //複選
        if (e==1){
            intent.putExtra("needs1", et);//可放所有基本類別
        }else if(e==0){
            intent.putExtra("needs1", "0");
        }

        if (f==1){
            intent.putExtra("needs2", ft);
        }else if(f==0){
            intent.putExtra("needs2", "0");
        }

        if (g==1){
            intent.putExtra("needs3", gt);
        }else if(g==0){
            intent.putExtra("needs3", "0");
        }

        if (h==1){
            intent.putExtra("needs4", ht);
        }else if(h==0){
            intent.putExtra("needs4", "0");
        }

        if (i==1){
            intent.putExtra("needs5", it);
        }else if(i==0){
            intent.putExtra("needs5", "0");
        }

        if (j==1){
            intent.putExtra("needs6", jt);
        }else if(j==0){
            intent.putExtra("needs6", "0");
        }

        if (k==1){
            intent.putExtra("needs7", kt);
        }else if(k==0){
            intent.putExtra("needs7", "0");
        }

        if (l==1){
            intent.putExtra("needs8", lt);
        }else if(l==0){
            intent.putExtra("needs8", "0");
        }

        //一句話
/*
        if(et1.getText().toString().equals("")) {
            intent.putExtra("et1", pet + "的美味菜單");
        }else {
            intent.putExtra("et1", et1.getText().toString());
        }
*/
        intent.putExtra("pet", pet);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("name", name);
        intent.putExtra("pet_id",pet_id);
        intent.putExtra("pet_weight",  pet_weight);

        //切換Activity
        startActivity(intent);


    }


}
