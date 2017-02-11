package app.dicky.meetthearrogance.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.utils.beanCommon.Data;
import app.dicky.meetthearrogance.customComponent.LoadingButton;

import java.util.ArrayList;

/**
 * copyright© www.pemt.com.cn
 * Created by eng005 on 2016/7/15
 */
public class MyBaseAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private ArrayList<Data> mScanAppInfos;
    public MyBaseAdapter(Context context, ArrayList<Data> mScanAppInfos){
        this.mInflater = LayoutInflater.from(context);
        this.mScanAppInfos = mScanAppInfos;
    }
    @Override
    public int getCount() {
        return mScanAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanAppInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("111",""+ "执行了");
        ViewHolder mHolder = new ViewHolder();
        if(convertView == null){
            //由于程序锁的条目与病毒扫描内容基本一致，因此重用程序锁的布局
            convertView = mInflater.inflate(R.layout.listview_item, null);
            mHolder.mAppNameTV = (TextView) convertView.findViewById(R.id.tv_content);
            mHolder.loadingButton = (LoadingButton)convertView.findViewById(R.id.btn_loading) ;
            convertView.setTag(mHolder);
        }else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        Data scanAppInfo = mScanAppInfos.get(position);
        Log.e("111",""+ mScanAppInfos.get(position));
            mHolder.mAppNameTV.setText(scanAppInfo.getName());
        return convertView;
    }

    private static class ViewHolder{
        TextView mAppNameTV;
        LoadingButton loadingButton;
    }
}
