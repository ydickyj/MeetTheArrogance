package app.dicky.meetthearrogance.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.adapter.AnimationAdapter;
import app.dicky.meetthearrogance.adapter.MyBaseAdapter;
import app.dicky.meetthearrogance.adapter.MyExpandableListViewAdapter;
import app.dicky.meetthearrogance.adapter.SwingBottomInAnimationAdapter;
import app.dicky.meetthearrogance.utils.beanCommon.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dicky on 2016/6/30.
 */
public class TestActivity extends Activity {

    private static final int SCAN_BENGIN = 100;
    private static final int SCANNING = 101;
    private static final int SCAN_FINISH = 102;
    private static Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SCAN_BENGIN:
//                    mScanAppTV.setText("开始修复中...");
                    break;
                case SCANNING:
//                    ScanAppInfo info = (ScanAppInfo) msg.obj;
//                    mScanAppTV.setText("修复名称: " + info.appName);
                    int speed = msg.arg1;
//                    mProcessTV.setText((speed * 100 / total) + "%");
//                    mScanAppInfos.add(info);
//                    adapter.notifyDataSetChanged();
//                    mScanListView.setSelection(mScanAppInfos.size());
                    break;
                case SCAN_FINISH:
//                    mScanAppTV.setText("扫描完成！");
//                    mScanningIcon.clearAnimation();
//                    mCancleBtn.setBackgroundResource(R.drawable.scan_complete);
//                    saveScanTime();
                    break;
            }
        }
    };
    private ArrayList<Map<String, String>> list;
    private ListView ListView;
    private ArrayList<Data> mList = new ArrayList<>();
    private List<Data> dataList;
    private int mCount = 0;
    private MyExpandableListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//            initData();
        Log.e("111", "" + mList);
        final MyBaseAdapter mBaseAdapter = new MyBaseAdapter(this, mList);
        Log.e("111", "" + mList);
        ListView = (ListView) findViewById(R.id.lv_test);

        Log.e("111", "" + mList);
//            new Thread() {
//
//                public void run() {
//                    Message msg = Message.obtain();
//                    msg.what = SCAN_BENGIN;
//                    mHandler.sendMessage(msg);
//                    List<PackageInfo> installedPackages = pm
//                            .getInstalledPackages(0);
//                    total = installedPackages.size();
//                    for (PackageInfo info : installedPackages) {
//                        if (!flag) {
//                            isStop = true;
//                            return;
//                        }
//                        String apkpath = info.applicationInfo.sourceDir;
//                        // 检查获取这个文件的 特征码
//                        String md5info = MD5Utils.getFileMd5(apkpath);
//                        String result = AntiVirusDao.checkVirus(md5info);
//                        msg = Message.obtain();
//                        msg.what = SCANNING;
//                        ScanAppInfo scanInfo = new ScanAppInfo();
//                        if (result == null) {
//                            scanInfo.description = "扫描安全";
//                            scanInfo.isVirus = false;
//                        } else {
//                            scanInfo.description = result;
//                            scanInfo.isVirus = true;
//                        }
//                        process++;
//                        scanInfo.packagename = info.packageName;
//                        scanInfo.appName = info.applicationInfo.loadLabel(pm)
//                                .toString();
//                        scanInfo.appicon = info.applicationInfo.loadIcon(pm);
//                        msg.obj = scanInfo;
//                        msg.arg1 = process;
//                        mHandler.sendMessage(msg);
//
//                        try {
//                            Thread.sleep(300);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    msg = Message.obtain();
//                    msg.what = SCAN_FINISH;
//                    mHandler.sendMessage(msg);
//                };
//            }.start();
        // 随便一堆测试数据
        list = new ArrayList<>();
        Map mMap = new HashMap();
        mMap.put("最近匹配", "自定义");
        mMap.put("最近匹配124", "自定义124");
        list.add(mMap);
        mMap = new HashMap();
        mMap.put("最近匹配", "自定义");
        mMap.put("最近匹配124", "自定义124");
        list.add(mMap);

        ListView.setLayoutAnimation(getAnimationController());
        final SimpleAdapter mAdapter = new SimpleAdapter(this, list, R.layout.listview_item, new String[]{"最近匹配", "最近匹配124"}, new int[]{R.id.tv_title, R.id.tv_content});
        SwingBottomInAnimationAdapter nMyAdapter = new SwingBottomInAnimationAdapter(mBaseAdapter);
            nMyAdapter.setListView(ListView);
        ListView.setAdapter(nMyAdapter);

        // 监听每个分组里子控件的点击事件
        Button mbtn = (Button) findViewById(R.id.add_btn);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map mMap = new HashMap();
                mMap = new HashMap();
                mMap.put("最近匹配", "自定义");
                mMap.put("最近匹配124", "自定义124");
                list.add(mMap);
                mAdapter.notifyDataSetChanged();


                Data mData = new Data();
                mData.setName(mCount++ + "");
                mData.setAppName("11");
                mData.setVirus(false);
                mData.setNameIsTrue(false);
                mList.add( 0,mData);
                mBaseAdapter.notifyDataSetChanged();
                ListView.setSelection( 0);

            }
        });
    }

    private void initData() {
        mList = new ArrayList<>();
        Data mData = new Data();
        mData.setName("11");
        mData.setAppName("11");
        mData.setVirus(false);
        mData.setNameIsTrue(false);
        mList.add(mData);

        mData = new Data();
        mData.setName("22");
        mData.setAppName("22");
        mData.setNameIsTrue(false);
        mList.add(mData);

        mData = new Data();
        mData.setName("33");
        mData.setAppName("33");
        mData.setNameIsTrue(false);
        mList.add(mData);

        mData = new Data();
        mData.setName("44");
        mData.setAppName("44");
        mData.setNameIsTrue(false);
        mList.add(mData);
    }

    /**
     * Layout动画
     *
     * @return
     */
    protected LayoutAnimationController getAnimationController() {
        int duration = 300;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    public class MyAnimationAdapter extends AnimationAdapter {

        public MyAnimationAdapter(BaseAdapter baseAdapter) {
            super(baseAdapter);
        }

        @Override
        public Animator[] getAnimators(ViewGroup parent, View view) {
            Animator bottomInAnimator = ObjectAnimator.ofFloat(view, "translationY", 500, 0);
            Animator rightInAnimator = ObjectAnimator.ofFloat(view, "translationX", parent.getWidth(), 0);
            return new Animator[]{bottomInAnimator, rightInAnimator};
        }

        @Override
        protected long getAnimationDelayMillis() {
            return DEFAULTANIMATIONDELAYMILLIS;
        }

        @Override
        protected long getAnimationDurationMillis() {
            return DEFAULTANIMATIONDURATIONMILLIS;
        }
    }

}

