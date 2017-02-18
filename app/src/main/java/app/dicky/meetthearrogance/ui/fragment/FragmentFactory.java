package app.dicky.meetthearrogance.ui.fragment;

import app.dicky.meetthearrogance.R;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/17 22:05
 * 描述：    TODO
 */
public class FragmentFactory {
    public static final String TAG = "FragmentFactory";

    private static FragmentFactory sFragmentFactory;

    private BaseFragment mMessageFragment;
    private BaseFragment mContactFragment;
    private BaseFragment mDynamicFragment;
    private BaseFragment mPersonFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragment(int id) {
        switch (id) {
            case R.id.conversations:
                return getConversationFragment();
            case R.id.contacts:
                return getContactFragment();
            case R.id.dynamic:
                return getDynamicFragment();
        }
        return null;
    }

    public BaseFragment getConversationFragment() {
        if (mMessageFragment == null) {
            mMessageFragment = new ConversationFragment();
        }
        return mMessageFragment;
    }

    public BaseFragment getDynamicFragment() {
        if (mDynamicFragment == null) {
            mDynamicFragment = new DynamicFragment();
        }
        return mDynamicFragment;
    }

    public BaseFragment getContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }

    public BaseFragment getPersonFragment() {
        if (mPersonFragment == null) {
            mPersonFragment = new PersonFragment();
        }
        return mPersonFragment;
    }


    public int switchId(int id) {
        switch (id) {
            case R.id.conversations:
                return 0;
            case R.id.contacts:
                return 1;
            case R.id.dynamic:
                return 2;
            case R.id.person:
                return 3;
        }
        return -1;
    }
}
