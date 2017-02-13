package app.dicky.meetthearrogance.mvpPresenter.impl;

import com.hyphenate.chat.EMClient;

import app.dicky.meetthearrogance.adapter.em.EMCallBackAdapter;
import app.dicky.meetthearrogance.mvpPresenter.LoginPresenter;
import app.dicky.meetthearrogance.mvpView.LoginView;
import app.dicky.meetthearrogance.utils.StringUtils;
import app.dicky.meetthearrogance.utils.ThreadUtils;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/16 21:17
 * 描述：    TODO
 */
public class LoginPresenterImpl implements LoginPresenter {
    public static final String TAG = "LoginPresenterImpl";


    public LoginView mLoginView;
    private EMCallBackAdapter mEMCallBack = new EMCallBackAdapter() {

        @Override
        public void onSuccess() {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginSuccess();
                }
            });
        }

        @Override
        public void onError(int i, String s) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoginView.onLoginFailed();
                }
            });
        }
    };

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String userName, String pwd) {
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                mLoginView.onStartLogin();
                startLogin(userName, pwd);
            } else {
                mLoginView.onPasswordError();
            }
        } else {
            mLoginView.onUserNameError();
        }
    }

    private void startLogin(String userName, String pwd) {
        EMClient.getInstance().login(userName, pwd, mEMCallBack);
    }
}
