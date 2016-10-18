package app.dicky.meetthearrogance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * On home
 * Created by dicky on 2016/7/17.
 */
public class VisualInspectionActivity extends Activity {
    private ArrayList<Map<String, ?>> mlist = new ArrayList<>();
    private String[] strContent = {"标题1","内容1","内容2"};
    private int[] intContent = {R.id.tv_title,R.id.tv_content_first,R.id.tv_content_second};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_inspection);
        init();
        ListView mLv = (ListView) findViewById(R.id.visual_inspection_lv);
        SimpleAdapter mSA = new SimpleAdapter(this,mlist,R.layout.visual_inspection_item_list,strContent,intContent);
        mLv.setAdapter(mSA);
    }

    void init() {
        HashMap mHM = new HashMap();
        mHM.put("标题1","集中器接线问题");
        mHM.put("内容1","\t\t"+"1.现场设备安装的不规范导致的漏抄现象，包括集中器零线虚接、缺相、三相表零线未接、单相表进线未接等。");
        mHM.put("内容2","\t\t"+"2.集中器零线虚接会导致台区抄表不稳定或整个台区抄表失败，缺相会导致没上电的一相上所接的电表难抄到或抄不到；接线错误可能对抄表成功率造成影响。");
        mlist.add(mHM);
        mHM = new HashMap();
        mHM.put("标题1","集中器零线虚接");
        mHM.put("内容1","\t\t"+"1.在现场可以通过在一相上加负荷，用万用表测量三相电压是否失去平衡，通常会出现加负荷的一相电压明显下降，其他两相有明显升高的现象，这样就可以确定零线虚接，对各个接点进行重接或加固，直至问题解决。");
        mHM.put("内容2","\t\t"+"2.或者可以通过将集中器零线直接接到变压器接地线上，通过观察抄表情况是否改善，确定是否存在零线虚接问题，确定后进行对各个接点进行重接或加固解决。 ");
        mlist.add(mHM);
        mHM = new HashMap();
        mHM.put("标题1","集中器零线虚接");
        mHM.put("内容1","\t\t"+"1.在现场可以通过在一相上加负荷，用万用表测量三相电压是否失去平衡，通常会出现加负荷的一相电压明显下降，其他两相有明显升高的现象，这样就可以确定零线虚接，对各个接点进行重接或加固，直至问题解决。");
        mHM.put("内容2","\t\t"+"2.或者可以通过将集中器零线直接接到变压器接地线上，通过观察抄表情况是否改善，确定是否存在零线虚接问题，确定后进行对各个接点进行重接或加固解决。 ");
        mlist.add(mHM);
        mHM = new HashMap();
        mHM.put("标题1","集中器零线虚接");
        mHM.put("内容1","\t\t"+"1.在现场可以通过在一相上加负荷，用万用表测量三相电压是否失去平衡，通常会出现加负荷的一相电压明显下降，其他两相有明显升高的现象，这样就可以确定零线虚接，对各个接点进行重接或加固，直至问题解决。");
        mHM.put("内容2","\t\t"+"2.或者可以通过将集中器零线直接接到变压器接地线上，通过观察抄表情况是否改善，确定是否存在零线虚接问题，确定后进行对各个接点进行重接或加固解决。 ");
        mlist.add(mHM);
        mHM = new HashMap();
        mHM.put("标题1","集中器零线虚接");
        mHM.put("内容1","\t\t"+"1.在现场可以通过在一相上加负荷，用万用表测量三相电压是否失去平衡，通常会出现加负荷的一相电压明显下降，其他两相有明显升高的现象，这样就可以确定零线虚接，对各个接点进行重接或加固，直至问题解决。");
        mHM.put("内容2","\t\t"+"2.或者可以通过将集中器零线直接接到变压器接地线上，通过观察抄表情况是否改善，确定是否存在零线虚接问题，确定后进行对各个接点进行重接或加固解决。 ");
        mlist.add(mHM);

    }
}
