package app.dicky.meetthearrogance.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import app.dicky.greendao.gen.ContactDao;
import app.dicky.greendao.gen.DaoMaster;
import app.dicky.greendao.gen.DaoSession;
import app.dicky.meetthearrogance.app.Constant;
import app.dicky.meetthearrogance.database.bean.Contact;
import app.dicky.meetthearrogance.mvpModel.User;

/**
 * 创建者:   dicky
 * 创建时间:  2017/2/11
 * 描述：    TODO
 */
public class DatabaseManager {
    public static final String TAG = "DatabaseManager";

    private static DatabaseManager sInstance;
    private DaoSession mDaoSession;


    public static DatabaseManager getInstance() {
        if (sInstance == null) {
            synchronized (DatabaseManager.class) {
                if (sInstance == null) {
                    sInstance = new DatabaseManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, Constant.Database.DATABASE_NAME, null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        mDaoSession = daoMaster.newSession();
    }

    public void saveContact(String userName, String mheadPath) {
        Contact contact = new Contact();
        contact.setUsername(userName);
        contact.setHeadPortraitPath(mheadPath);
        mDaoSession.getContactDao().save(contact);
    }

    public List<String> queryAllContacts() {
        List<Contact> list = mDaoSession.getContactDao().queryBuilder().list();
        ArrayList<String> contacts = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            String contact = list.get(i).getUsername();
            contacts.add(contact);
        }
        return contacts;
    }

    public void deleteAllContacts() {
        ContactDao contactDao = mDaoSession.getContactDao();
        contactDao.deleteAll();
    }

    public Contact queryData(String Value) {
        ContactDao contactDao = mDaoSession.getContactDao();
        List<Contact> mList = contactDao.queryBuilder().where(ContactDao.Properties.Username.eq(Value)).build().list();
        if (mList.size() == 0) {
            return new Contact();
        }
        return mList.get(0);
    }

    public void deleteContacts(String value) {
        ContactDao contactDao = mDaoSession.getContactDao();
        List<Contact> mList = contactDao.queryBuilder().where(ContactDao.Properties.Username.eq(value)).build().list();
        if (mList.size() != 0) {
            for (int i = 0; i < mList.size(); i++) {
                contactDao.deleteByKey(mList.get(i).getId());
            }
        }
    }
}
