package app.dicky.meetthearrogance.mvpPresenter.impl;

import com.hyphenate.chat.EMClient;

import app.dicky.meetthearrogance.adapter.em.EMCallBackAdapter;
import app.dicky.meetthearrogance.mvpPresenter.DynamicPresenter;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import app.dicky.meetthearrogance.mvpView.DynamicView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 10:18
 * 描述：    TODO
 */
public class DynamicPresenterImpl implements DynamicPresenter {

    private DynamicView mDynamicView;
    private EMCallBackAdapter mEMCallBackAdapter = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDynamicView.onLogoutFailed();
                }
            });
        }
    };

    public DynamicPresenterImpl(DynamicView dynamicView) {
        mDynamicView = dynamicView;
    }

    @Override
    public void logout() {
        mDynamicView.onStartLogout();
        EMClient.getInstance().logout(true, mEMCallBackAdapter);
    }
}
