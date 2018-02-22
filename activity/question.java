package info.androidhive.materialtabs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.materialtabs.R;

/**
 * Created by Xuan on 2016/10/30.
 */

public class question extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView2;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //標題列
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_left_black_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView2 = (ListView)findViewById(R.id.listView);
        // 清單面版
        adapter = new SimpleAdapter(this, getData(),
                R.layout.view2content3,
                new String[] { "title", "info"},
                new int[] {	R.id.title, R.id.info});

        listView2.setAdapter(adapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;

                Toast.makeText(question.this, "ID：" + arg3 + "   選單文字："+ listView.getItemAtPosition(arg2).toString(), Toast.LENGTH_LONG).show();

            }
        });
        }

    //標題列
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return super.onOptionsItemSelected(item);
    }

    //ListView內容
    private List getData() {
        ArrayList list = new ArrayList();
        Map map = new HashMap(); map.put("title", "Q：全台灣24H到貨週末假日照常出貨"); map.put("info", "A：下列除外！\n" +
                "1. 非北北基地區，晚上10：00～早上10：00下單者 \n" +
                "2. 安裝商品\n" +
                "3. ATM 付款");  list.add(map);
        map = new HashMap(); map.put("title", "Q：付款方式說明："); map.put("info", "A：ATM轉帳 \n" +
                "‧如何付款?\n" +
                "只要將購買款項直接匯入「系統指定帳號」即可完成付款。每個指定帳號只能用在該筆訂單。 \n" +
                "‧帳號何時提供?\n" +
                "購買完成後，我們會提供您該筆訂單的專用「轉帳帳號」，請您在隔日23:59前轉入款項(繳款期限是不受例假日所影響)。 \n" +
                "‧帳號格式? \n" +
                "帳號為3碼銀行代碼+16碼轉帳帳號。 \n" +
                "‧匯款金額有限制嗎? \n" +
                "本店不受轉帳每日3萬元之限制，請您安心轉帳繳款。 \n" +
                "‧可以合併訂單付款嗎? \n" +
                "如您有二筆以上的訂單，請依各別的專用匯款帳號轉帳，無法合併付款。");  list.add(map);
        map = new HashMap(); map.put("title", "Q：退貨退款"); map.put("info", "A：關於退貨： \n" +
                "- 依照消費者保護法規定，商店街之消費者均享有商品到貨七天猶豫期之權益。 \n" +
                "- 消費者辦理退貨時請先至【我的帳戶】點選退貨申請並於退貨申請網頁填妥相關資料，並將原商品備妥，\n" +
                "本公司將於接獲申請之次日起1個工作天內檢視您的退貨要求，檢視完畢後將委託指定之宅配公司，在5個工作天內透過電話與您連絡前往取回退貨商品。 \n" +
                "(到貨七天期限內申請，逾期未辦理，除因商品原始瑕疵外將無法辦理銷退。) \n" +
                "- 請您保持電話暢通，並備妥原商品及所有包裝及附件，以便於交付予本公司指定之宅配公司取回（宅配公司僅負責收件，退貨商品仍由PChome24h購物進行驗收），宅配公司取件後會提供簽收單據給您，請注意留存。 ");  list.add(map);

        return list;
    }
}

