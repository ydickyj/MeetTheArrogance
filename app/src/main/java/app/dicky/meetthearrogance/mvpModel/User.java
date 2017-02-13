package app.dicky.meetthearrogance.mvpModel;

import cn.bmob.v3.BmobUser;

/**
 * 创建者:  dicky
 * 创建时间:  2016/10/16 23:31
 * 描述：    TODO
 */
public class User extends BmobUser {

    public User(String userName, String password) {
        setUsername(userName);
        setPassword(password);
    }

}
