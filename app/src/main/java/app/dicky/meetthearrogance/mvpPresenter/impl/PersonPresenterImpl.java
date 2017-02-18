package app.dicky.meetthearrogance.mvpPresenter.impl;

import com.hyphenate.chat.EMClient;

import app.dicky.meetthearrogance.adapter.em.EMCallBackAdapter;
import app.dicky.meetthearrogance.mvpPresenter.DynamicPresenter;
import app.dicky.meetthearrogance.mvpPresenter.PersonPresenter;
import app.dicky.meetthearrogance.mvpView.DynamicView;
import app.dicky.meetthearrogance.mvpView.PersonView;
import app.dicky.meetthearrogance.utils.ThreadUtils;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/18 10:18
 * 描述：    TODO
 */
public class PersonPresenterImpl implements PersonPresenter {

    private PersonView mPersonView;
    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPersonView.onLogoutSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPersonView.onLogoutFailed();
                }
            });
        }
    };

    public PersonPresenterImpl(PersonView mPersonView) {
        mPersonView = mPersonView;
    }

    @Override
    public void logout() {
        mPersonView.onStartLogout();
        EMClient.getInstance().logout(true, mEMCallBackAdapter);
    }
}
