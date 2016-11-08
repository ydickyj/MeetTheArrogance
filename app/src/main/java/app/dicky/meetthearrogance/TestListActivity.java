package app.dicky.meetthearrogance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *On home
 * Created by dicky on 2016/7/17.
 */
public class TestListActivity extends Activity {

    private ArrayList<String> mlist = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        init();
        ListView mLv = (ListView) findViewById(R.id.test_list);
        ArrayAdapter mAA = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mlist);
        mLv.setAdapter(mAA);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(TestListActivity.this, TestActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(TestListActivity.this, VisualInspectionActivity.class));
                        break;
                    case 2:
//                        startActivity(new Intent(TestListActivity.this, ScreenTestActivity.class));
                        break;
                    case 3:
//                        startActivity(new Intent(TestListActivity.this, PublishActivity.class));
                    case 4:
                        startActivity(new Intent(TestListActivity.this,TimeTestActivity.class));
                    case 5:
                        startActivity(new Intent(TestListActivity.this,TTSActivity.class));
                        break;
                }
            }
        });
    }

    void init() {
        mlist.add("参数检查界面");
        mlist.add("直观故障检查");
        mlist.add("屏幕测试");
        mlist.add("拍照测试");
        mlist.add("时间测试");
        mlist.add("播报测试");
    }
}
