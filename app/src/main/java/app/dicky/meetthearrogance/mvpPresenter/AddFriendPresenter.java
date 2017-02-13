package app.dicky.meetthearrogance.mvpPresenter;


import java.util.List;

import app.dicky.meetthearrogance.mvpModel.AddFriendItem;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:38
 * 描述：    TODO
 */
public interface AddFriendPresenter {
    public static final String TAG = "AddFriendPresenter";

    void searchFriend(String keyword);

    void onDestroy();

    List<AddFriendItem> getAddFriendList();
}
