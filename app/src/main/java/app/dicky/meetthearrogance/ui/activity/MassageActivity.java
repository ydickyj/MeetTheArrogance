package app.dicky.meetthearrogance.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.adapter.MyExpandableListViewAdapter;
import app.dicky.meetthearrogance.customComponent.AnimatedExpandableListView;

/**
 *
 * Created by dicky on 2016/6/30.
 */
public class MassageActivity extends Activity {

    private AnimatedExpandableListView expandableListView;

    private List<String> group_list;

    private List<String> item_lt;

    private List<List<String>> item_list;

    private List<List<Integer>> item_list2;

    private List<List<Integer>> gr_list2;

    private MyExpandableListViewAdapter adapter;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_massage);
            // 随便一堆测试数据
            group_list = new ArrayList<String>();
            group_list.add("最近匹配");
            group_list.add("自定义");
            group_list.add("自定义2");
            group_list.add("自定义3");

            item_lt = new ArrayList<String>();
            item_lt.add("张三丰");
            item_lt.add("董存瑞");
            item_lt.add("李大钊");

            item_list = new ArrayList<List<String>>();
            item_list.add(item_lt);
            item_list.add(item_lt);
            item_list.add(item_lt);
            item_list.add(item_lt);

            List<Integer> tmp_list = new ArrayList<Integer>();
            tmp_list.add(R.drawable.login_background);
            tmp_list.add(R.drawable.login_background);
            tmp_list.add(R.drawable.login_background);
            tmp_list.add(R.drawable.login_background);

            item_list2 = new ArrayList<List<Integer>>();
            item_list2.add(tmp_list);
            item_list2.add(tmp_list);
            item_list2.add(tmp_list);
            item_list2.add(tmp_list);

            List<Integer> gr_list = new ArrayList<Integer>();
            gr_list.add(R.drawable.yw_logo_btn);
            gr_list.add(R.drawable.yw_logo_btn);
            gr_list.add(R.drawable.yw_logo_btn);
            gr_list.add(R.drawable.yw_logo_btn);

            gr_list2 = new ArrayList<List<Integer>>();
            gr_list2.add(gr_list);
            gr_list2.add(gr_list);
            gr_list2.add(gr_list);
            gr_list2.add(gr_list);

            expandableListView = (AnimatedExpandableListView) findViewById(R.id.listView);
            expandableListView.setGroupIndicator(null);//将控件默认的左边箭头去掉，

            // 监听组点击
            expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    // We call collapseGroupWithAnimation(int) and
                    // expandGroupWithAnimation(int) to animate group
                    // expansion/collapse.

                    if (expandableListView.isGroupExpanded(groupPosition)) {
                        expandableListView.collapseGroup(groupPosition);
                        Log.e("123:","我要回去咯！！");
                    } else {
                        expandableListView.expandGroup(groupPosition,true);
                        Log.e("123:","我要展开咯！！");
                    }
                    return true;
                }

            });

            // 监听每个分组里子控件的点击事件
            expandableListView.setOnChildClickListener(new OnChildClickListener()
            {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
                {
                    Toast.makeText(MassageActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + item_list.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            adapter = new MyExpandableListViewAdapter(this);
//            adapter.setItem_lt(item_lt);
            adapter.setGroup_list(group_list);
            adapter.setItem_list(item_list);
            adapter.setGr_list2(gr_list2);
            adapter.setItem_list2(item_list2);
            expandableListView.setAdapter(adapter);
        }

    @Override
    protected void onResume() {
        Button btn = (Button)findViewById(R.id.testBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MassageActivity.this,TestActivity.class));
            }
        });
        super.onResume();
    }
}
