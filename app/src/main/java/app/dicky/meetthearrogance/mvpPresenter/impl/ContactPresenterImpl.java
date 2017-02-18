package app.dicky.meetthearrogance.mvpPresenter.impl;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import app.dicky.meetthearrogance.database.DatabaseManager;
import app.dicky.meetthearrogance.mvpModel.ContactListItem;
import app.dicky.meetthearrogance.mvpModel.User;
import app.dicky.meetthearrogance.mvpPresenter.ContactPresenter;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import app.dicky.meetthearrogance.mvpView.ContactView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/18 15:34
 * 描述：    TODO
 */
public class ContactPresenterImpl implements ContactPresenter {
    private static final String TAG = "ContactPresenterImpl";

    private ContactView mContactView;

    private List<ContactListItem> mContactListItems;

    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
        mContactListItems = new ArrayList<ContactListItem>();
    }


    @Override
    public List<ContactListItem> getContactList() {
        return mContactListItems;
    }

    @Override
    public void refreshContactList() {
        mContactListItems.clear();
        getContactsFromServer();
    }

    @Override
    public void deleteFriend(final String name) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(name);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteFriendSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteFriendFailed();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getContactsFromServer() {
        if (mContactListItems.size() > 0) {
            mContactView.onGetContactListSuccess();
            return;
        }
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    startGetContactList();
                    notifyGetContactListSuccess();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    notifyGetContactListFailed();
                }
            }
        });

    }

    private void notifyGetContactListFailed() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactView.onGetContactListFailed();
            }
        });
    }

    /**
     * 获取联系人列表数据
     *
     * @throws HyphenateException
     */
    private void startGetContactList() throws HyphenateException {
        List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
        Collections.sort(contacts, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);//ascending order
            }
        });
        syncData(contacts);
    }

    private void saveContactToDatabase(String userName, String headPath) {
        DatabaseManager.getInstance().saveContact(userName, headPath);
    }

    /**
     * 服务器数据同步至数据库
     */

    void syncData(final List<String> contacts) {
        int isDone = 1;
        if (!contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                final ContactListItem item = new ContactListItem();
                item.userName = contacts.get(i);
                String localHeadPath = DatabaseManager.getInstance().queryData(item.userName).getHeadPortraitPath();
                if (localHeadPath == "" || localHeadPath == null) {
                    mContactListItems.clear();
                    isDone = 0;
                    Log.e("tag3", "" + isDone);
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.addWhereEqualTo("username", item.userName);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null) {
                                BmobFile file = list.get(0).getUserHeadImage();
                                downloadFile(contacts, item, file);
                            }
                        }
                    });
                    return;
                } else {
                    isDone = isDone * 2;
                    Log.e("tag", "" + localHeadPath);
                    item.setHeadPath(localHeadPath);
                    DatabaseManager.getInstance().deleteContacts(item.userName);
                    saveContactToDatabase(item.userName, localHeadPath);
                }
                if (itemInSameGroup(i, item)) {
                    item.showFirstLetter = false;
                }
                mContactListItems.add(item);
            }
            if (isDone != 0) {
                notifyGetContactListSuccess();
            }
        }
    }
    /**
     * 当前联系人跟上个联系人比较，如果首字符相同则返回true
     *
     * @param i    当前联系人下标
     * @param item 当前联系人数据模型
     * @return true 表示当前联系人和上一联系人在同一组
     */
    private boolean itemInSameGroup(int i, ContactListItem item) {
        return i > 0 && (item.getFirstLetter() == mContactListItems.get(i - 1).getFirstLetter());
    }

    private void notifyGetContactListSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactView.onGetContactListSuccess();
            }
        });
    }

    private void downloadFile(final List<String> contacts, final ContactListItem item, BmobFile file) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
            }

            @Override
            public void done(String savePath, BmobException e) {
                Log.e("tag2", "" + savePath);
                item.setHeadPath(savePath);
                saveContactToDatabase(item.userName, savePath);
                syncData(contacts);
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
            }

        });

    }
}
