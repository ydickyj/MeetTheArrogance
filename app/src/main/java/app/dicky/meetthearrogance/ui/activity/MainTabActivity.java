package app.dicky.meetthearrogance.ui.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import app.dicky.meetthearrogance.R;

public class MainTabActivity extends Activity {

    List<Intent> intentList;
    LocalActivityManager activityManager;

    String[] tabIds = {"home", "task", "record", "profile", "more"};

    private RadioGroup radioGroup;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        //activity页
        activityManager = new LocalActivityManager(this, true);
        activityManager.dispatchCreate(savedInstanceState);

        intentList = new LinkedList<Intent>();
        Intent homeIntent = new Intent(this, MassageActivity.class);
        intentList.add(homeIntent);

        Intent taskIntent = new Intent(this, LoginPasswordActivity.class);
        intentList.add(taskIntent);

        Intent recordIntent = new Intent(this, TestActivity.class);
        intentList.add(recordIntent);

        Intent profileIntent = new Intent(this, TimeTestActivity.class);
        intentList.add(profileIntent);

        Intent moreIntent = new Intent(this, TTSActivity.class);
        intentList.add(moreIntent);

        //设置radioGroup
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                // TODO 自动生成的方法存根
                switch (checkedId) {
                    case R.id.radio_home:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.radio_task:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.radio_record:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.radio_profile:
                        viewpager.setCurrentItem(3);
                        break;
                    case R.id.radio_more:
                        viewpager.setCurrentItem(4);
                        break;
                }
            }

        });

        //设置viewpager
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyPageAdapter());
        viewpager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onPageSelected(int position) {
                // TODO 自动生成的方法存根
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.radio_home);
                        break;
                    case 1:
                        radioGroup.check(R.id.radio_task);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_record);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio_profile);
                        break;
                    case 4:
                        radioGroup.check(R.id.radio_more);
                        break;
                }
            }

        });

    }

    protected void onResume() {
        super.onResume();
        activityManager.dispatchResume();
    }

    protected void onPause() {
        super.onPause();
        activityManager.dispatchPause(isFinishing());
    }

    protected void onStop() {
        super.onStop();
        activityManager.dispatchStop();
    }

    protected void onDestory() {
        super.onDestroy();
        activityManager.dispatchDestroy(isFinishing());
    }

    private class MyPageAdapter extends PagerAdapter {

        //临时保存view
        HashMap<String, View> idViewMap;

        public MyPageAdapter() {
            idViewMap = new HashMap<String, View>();
        }

        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View view, int position, Object arg2) {
            ViewPager viewPager = ((ViewPager) view);
            View tabView = idViewMap.get(tabIds[position]);
            viewPager.removeView(tabView);//移除viewPager中的view

            activityManager.destroyActivity(tabIds[position], true);//销毁activity
            idViewMap.remove(tabIds[position]);//移除idViewMap中的引用
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return intentList.size();
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ViewPager viewPager = ((ViewPager) view);

            View tabView = idViewMap.get(tabIds[position]);//先向idViewMap获取，看有没有
            if (tabView == null) {//没有就新创建一个
                tabView = activityManager.startActivity(tabIds[position], intentList.get(position)).getDecorView();
                idViewMap.put(tabIds[position], tabView);
            } else {//检查是否已加入到某个parent view中
                ViewGroup tabViewParent = (ViewGroup) tabView.getParent();
                if (tabViewParent != null) {
                    tabViewParent.removeAllViewsInLayout();
                }
            }
            viewPager.addView(tabView);

            return tabView;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
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

    }
}
