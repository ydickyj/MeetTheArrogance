package app.dicky.meetthearrogance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.widget.AnimatedExpandableListView;

import java.util.List;

/**
 * Created by eng005 on 2016/6/30.
 */
// 用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
public class MyExpandableListViewAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Context context;
    private LayoutInflater inflater;

    private List<String> group_list;

    private List<String> item_lt;

    private List<List<String>> item_list;

    private List<List<Integer>> item_list2;

    private List<List<Integer>> gr_list2;

    public MyExpandableListViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public List<String> getGroup_list() {
        return group_list;
    }

    public void setGroup_list(List<String> group_list) {
        this.group_list = group_list;
    }

    public List<String> getItem_lt() {
        return item_lt;
    }

    public void setItem_lt(List<String> item_lt) {
        this.item_lt = item_lt;
    }

    public List<List<String>> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<List<String>> item_list) {
        this.item_list = item_list;
    }

    public List<List<Integer>> getItem_list2() {
        return item_list2;
    }

    public void setItem_list2(List<List<Integer>> item_list2) {
        this.item_list2 = item_list2;
    }

    public List<List<Integer>> getGr_list2() {
        return gr_list2;
    }

    public void setGr_list2(List<List<Integer>> gr_list2) {
        this.gr_list2 = gr_list2;
    }

    /**
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        return group_list.size();
    }



    /**
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition) {
        return group_list.get(groupPosition);
    }

    /**
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return item_list.get(groupPosition).get(childPosition);
    }

    /**
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_massage_expand_list_group, null);
            groupHolder = new GroupHolder();
            groupHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            groupHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
    }

        if (!isExpanded) {
            groupHolder.img.setBackgroundResource(R.drawable.yw_logo_btn);
        } else {
            groupHolder.img.setBackgroundResource(R.drawable.yw_login_btn);
        }
        groupHolder.txt.setText(group_list.get(groupPosition));
        return convertView;
    }


    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_massage_expand_list_item, null);
            itemHolder = new ItemHolder();
            itemHolder.txt = (TextView) convertView.findViewById(R.id.txt);
            itemHolder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition));
        itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(childPosition));
        return convertView;
    }

    /**
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getRealChildrenCount(int groupPosition) {
        return item_list.get(groupPosition).size();
    }




    /**
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        public TextView txt;
        public ImageView img;
    }

    class ItemHolder {
        public ImageView img;
        public TextView txt;
    }


}

