package app.dicky.meetthearrogance.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;


import java.util.ArrayList;
import java.util.List;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.adapter.em.EMMessageListenerAdapter;
import app.dicky.meetthearrogance.ui.fragment.FragmentFactory;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    List<Fragment> fragments;
    FragmentTransaction fragmentTransaction;
    private FragmentManager mFragmentManager;
    private OnTabSelectListener mOnTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            fragmentTransaction = mFragmentManager.beginTransaction();
            int index = FragmentFactory.getInstance().switchId(tabId);
            for (int i = 0; i < fragments.size(); i++) {
                if (i == index) {
                    fragmentTransaction.show(fragments.get(i));
                } else {
                    fragmentTransaction.hide(fragments.get(i));
                }
            }
            fragmentTransaction.commit();
        }
    };


    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        //该回调在子线程中调用
        @Override
        public void onMessageReceived(List<EMMessage> list) {
            updateUnreadCount();
        }
    };
    private EMConnectionListener mEMConnectionListener = new EMConnectionListener() {
        @Override
        public void onConnected() {

        }

        @Override
        public void onDisconnected(int i) {
            if (i == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(LoginActivity.class);
                        toast(getString(R.string.user_login_another_device));
                    }
                });
            }
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        mFragmentManager = getSupportFragmentManager();
        fragmentTransaction = mFragmentManager.beginTransaction();

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragments = new ArrayList<>();
        fragments.add(FragmentFactory.getInstance().getConversationFragment());
        fragments.add(FragmentFactory.getInstance().getContactFragment());
        fragments.add(FragmentFactory.getInstance().getDynamicFragment());
        fragments.add(FragmentFactory.getInstance().getPersonFragment());
        fragmentTransaction.add(R.id.fragment_container, fragments.get(0));
        fragmentTransaction.add(R.id.fragment_container, fragments.get(1));
        fragmentTransaction.add(R.id.fragment_container, fragments.get(2));
        fragmentTransaction.add(R.id.fragment_container, fragments.get(3));
        fragmentTransaction.show(fragments.get(0)).hide(fragments.get(1)).hide(fragments.get(2)).hide(fragments.get(3));
        fragmentTransaction.commit();
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);
        EMClient.getInstance().addConnectionListener(mEMConnectionListener);
    }

    private void updateUnreadCount() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BottomBarTab bottomBar = mBottomBar.getTabWithId(R.id.conversations);
                int count = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                bottomBar.setBadgeCount(count);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUnreadCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(mEMConnectionListener);
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }
}
