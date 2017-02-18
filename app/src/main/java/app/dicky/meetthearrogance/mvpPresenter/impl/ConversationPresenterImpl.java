package app.dicky.meetthearrogance.mvpPresenter.impl;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import app.dicky.meetthearrogance.database.DatabaseManager;
import app.dicky.meetthearrogance.database.bean.HeadEm;
import app.dicky.meetthearrogance.mvpModel.User;
import app.dicky.meetthearrogance.mvpPresenter.ConversationPresenter;
import app.dicky.meetthearrogance.mvpView.ConversationView;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/21 9:51
 * 描述：    TODO
 */
public class ConversationPresenterImpl implements ConversationPresenter {

    private ConversationView mConversationView;

    private List<EMConversation> mEMConversations;

    private List<HeadEm> mHeadEms;

    public ConversationPresenterImpl(ConversationView conversationView) {
        mConversationView = conversationView;
        mEMConversations = new ArrayList<EMConversation>();
        mHeadEms = new ArrayList<>();
    }


    @Override
    public void loadAllConversations() {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
                mEMConversations.clear();
                mEMConversations.addAll(conversations.values());
                Collections.sort(mEMConversations, new Comparator<EMConversation>() {
                    @Override
                    public int compare(EMConversation o1, EMConversation o2) {
                        return (int) (o2.getLastMessage().getMsgTime() - o1.getLastMessage().getMsgTime());
                    }
                });
                for (int i = 0; i < mEMConversations.size(); i++) {
                    HeadEm mHeadEm = new HeadEm();
                    mHeadEm.setmEMConversation(mEMConversations.get(i));
                    String path = DatabaseManager.getInstance().queryData(mEMConversations.get(i).getLastMessage().getUserName()).getHeadPortraitPath();
                    mHeadEm.setmHeadPortrait(path);
                    mHeadEms.add(mHeadEm);
                }
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConversationView.onAllConversationsLoaded();
                    }
                });
            }
        });
    }

    @Override
    public List<EMConversation> getConversations() {
        return mEMConversations;
    }

    @Override
    public List<HeadEm> getmConversations() {
        return mHeadEms;
    }

    private void downloadFile(BmobFile file) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        final String[] mSavePath = {null};
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void done(String savePath, BmobException e) {
                if (e == null) {
                    mSavePath[0] = savePath;
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
            }

        });

    }

}
