package com.itheima.leon.qqdemo.presenter;

import com.itheima.leon.qqdemo.model.ContactListItem;

import java.util.List;

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
