package app.dicky.meetthearrogance.ui.fragment;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;


import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.mvpPresenter.DynamicPresenter;
import app.dicky.meetthearrogance.mvpPresenter.impl.DynamicPresenterImpl;
import app.dicky.meetthearrogance.ui.activity.LoginActivity;
import app.dicky.meetthearrogance.mvpView.DynamicView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/17 22:39
 * 描述：    TODO
 */
public class DynamicFragment extends BaseFragment implements DynamicView {
    public static final String TAG = "DynamicFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.back)
    ImageView mBack;

    private DynamicPresenter mDynamicPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void init() {
        super.init();
        mDynamicPresenter = new DynamicPresenterImpl(this);
        String logout = String.format(getString(R.string.logout), EMClient.getInstance().getCurrentUser());
        mLogout.setText(logout);
        mTitle.setText(getString(R.string.dynamic));

    }

    @OnClick(R.id.logout)
    public void onClick() {
        mDynamicPresenter.logout();
    }

    @Override
    public void onStartLogout() {
        showProgress(getString(R.string.logouting));
    }

    @Override
    public void onLogoutSuccess() {
        hideProgress();
        toast(getString(R.string.logout_success));
        startActivity(LoginActivity.class, true);
    }

    @Override
    public void onLogoutFailed() {
        hideProgress();
        toast(getString(R.string.logout_failed));
    }
}
