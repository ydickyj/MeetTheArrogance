package app.dicky.meetthearrogance.mvpView;

import cn.bmob.v3.exception.BmobException;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/16 22:05
 * 描述：    TODO
 */

public interface RegisterView {

    void onStartRegister();

    void onRegisterError(String e);

    void onResisterUserExist();

    void onRegisterSuccess();

    void onUserNameError();

    void onPasswordError();

    void onConfirmPasswordError();

}
