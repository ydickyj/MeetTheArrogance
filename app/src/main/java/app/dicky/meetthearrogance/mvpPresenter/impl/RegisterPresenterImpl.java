package app.dicky.meetthearrogance.mvpPresenter.impl;

import android.graphics.Movie;
import android.media.Image;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;

import app.dicky.meetthearrogance.app.Constant;
import app.dicky.meetthearrogance.mvpModel.User;
import app.dicky.meetthearrogance.mvpModel.bean.PersonBean;
import app.dicky.meetthearrogance.mvpPresenter.RegisterPresenter;
import app.dicky.meetthearrogance.mvpView.RegisterView;
import app.dicky.meetthearrogance.utils.StringUtils;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/16 22:21
 * 描述：    TODO
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    public static final String TAG = "RegisterPresenterImpl";

    public RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void register(String userName, String pwd, String pwdConfirm, File mFile) {
        if (StringUtils.checkUserName(userName)) {
            if (StringUtils.checkPassword(pwd)) {
                if (pwd.equals(pwdConfirm)) {
                    mRegisterView.onStartRegister();
                    final BmobFile bmobFile = new BmobFile(mFile);
                    registerBmob(userName, pwd, bmobFile);
                } else {
                    mRegisterView.onConfirmPasswordError();
                }
            } else {
                mRegisterView.onPasswordError();
            }
        } else {
            mRegisterView.onUserNameError();
        }
    }

    private void registerBmob(final String userName, final String pwd, final BmobFile bmobFile) {

        Log.e("12312", "1231231");
        final User user = new User(userName, pwd, bmobFile);

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                registerEaseMob(userName, pwd);
                            } else {
                                notifyRegisterFailed(e);
                            }
                        }
                    });

                } else {
                    notifyRegisterFailed(e);
                }
            }
        });
    }

    private void registerEaseMob(final String userName, final String pwd) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName, pwd);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterSuccess();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterError(e + "");
                        }
                    });
                }
            }
        });
    }

    private void notifyRegisterFailed(BmobException e) {
        if (e.getErrorCode() == Constant.ErrorCode.USER_ALREADY_EXIST) {
            mRegisterView.onResisterUserExist();
        } else {
            mRegisterView.onRegisterError(e + "");
        }
    }


}
