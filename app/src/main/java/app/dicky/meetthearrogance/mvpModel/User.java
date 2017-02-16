package app.dicky.meetthearrogance.mvpModel;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 创建者:  dicky
 * 创建时间:  2016/10/16 23:31
 * 描述：    TODO
 */
public class User extends BmobUser {
    private BmobFile userHeadImage;//电影文件

    public User(String userName, String password, BmobFile userHeadImage) {
        setUsername(userName);
        setPassword(password);
        this.userHeadImage = userHeadImage;
    }

    public BmobFile getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(BmobFile userHeadImage) {
        this.userHeadImage = userHeadImage;
    }
}
