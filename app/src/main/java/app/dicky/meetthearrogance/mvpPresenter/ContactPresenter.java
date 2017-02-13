package app.dicky.meetthearrogance.mvpPresenter;


import java.util.List;

import app.dicky.meetthearrogance.mvpModel.ContactListItem;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 15:33
 * 描述：    TODO
 */
public interface ContactPresenter {

    void getContactsFromServer();

    List<ContactListItem> getContactList();

    void refreshContactList();

    void deleteFriend(String name);

}
