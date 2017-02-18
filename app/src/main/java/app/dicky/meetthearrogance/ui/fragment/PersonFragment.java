package app.dicky.meetthearrogance.ui.fragment;

import android.graphics.Color;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.mvpPresenter.DynamicPresenter;
import app.dicky.meetthearrogance.mvpPresenter.PersonPresenter;
import app.dicky.meetthearrogance.mvpPresenter.impl.DynamicPresenterImpl;
import app.dicky.meetthearrogance.mvpPresenter.impl.PersonPresenterImpl;
import app.dicky.meetthearrogance.mvpView.DynamicView;
import app.dicky.meetthearrogance.mvpView.PersonView;
import app.dicky.meetthearrogance.ui.activity.LoginActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/17 22:39
 * 描述：    TODO
 */
public class PersonFragment extends BaseFragment implements PersonView {
    public static final String TAG = "PersonFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.logout)
    Button mLogout;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.head_title)
    RelativeLayout mHeadTitle;

    private PersonPresenter mPersonPresenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_person;
    }

    @Override
    protected void init() {
        super.init();
        mPersonPresenter = new PersonPresenterImpl(this);
        String logout = String.format(getString(R.string.logout), EMClient.getInstance().getCurrentUser());
        mLogout.setText(logout);
        mTitle.setText(getString(R.string.dynamic));
        mHeadTitle.setBackgroundColor(Color.parseColor("#04dd98"));
    }

    @OnClick(R.id.logout)
    public void onClick() {
        mPersonPresenter.logout();
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
