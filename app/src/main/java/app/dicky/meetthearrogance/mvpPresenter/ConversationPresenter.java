package app.dicky.meetthearrogance.mvpPresenter;

import com.hyphenate.chat.EMConversation;

import java.util.List;

import app.dicky.meetthearrogance.database.bean.HeadEm;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/21 9:50
 * 描述：    TODO
 */
public interface ConversationPresenter {

    void loadAllConversations();

    List<EMConversation> getConversations();

    List<HeadEm> getmConversations();
}
