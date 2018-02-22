package info.androidhive.materialtabs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.Home;
import info.androidhive.materialtabs.fragments.Member;
import info.androidhive.materialtabs.fragments.QRcode;
import info.androidhive.materialtabs.fragments.Shop;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String email;
    private String name;
    private String password;
    private String kind;
    private static Boolean isExit = false;
    private static Boolean hasTask = false;

    public String getName()
    {
        return name;
    }
    public String getPassword()
    {
        return password;
    }
    public String getEmail()
    {
        return email;
    }
    public String getKind()
    {
        return kind;
    }


    //案2次返回關閉程式
    Timer tExit = new Timer();
    TimerTask task = new TimerTask() {
             @Override
             public void run() {
                     isExit = false;
                     hasTask = true;
                }
        };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
             System.out.println("TabHost_Index.java onKeyDown");
             if (keyCode == KeyEvent.KEYCODE_BACK) {
                     if(isExit == false ) {
                              isExit = true;
                             Toast.makeText(this, "再按一次返回退出"
                        , Toast.LENGTH_SHORT).show();
                               if(!hasTask) {
                                        tExit.schedule(task, 2000);
                                   }
                         } else {
                              finish();
                              System.exit(0);
                        }
                  }
             return false;
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //抓取個人資料
        Intent intent = getIntent();
        if(intent.getStringExtra("name")!=null) {
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            name = intent.getStringExtra("name");
        }
        if(intent.getStringExtra("kind")!=null) {
            kind = intent.getStringExtra("kind");
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_home,
                R.drawable.ic_shop,
                R.drawable.ic_member,
                R.drawable.ic_qr
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Home(), "首頁");
        adapter.addFrag(new Shop(), "購物");
        adapter.addFrag(new Member(), "會員");
        adapter.addFrag(new QRcode(), "掃描QR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;

        }


    }
}
