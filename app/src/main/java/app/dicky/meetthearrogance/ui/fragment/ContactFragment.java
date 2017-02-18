package app.dicky.meetthearrogance.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import java.util.List;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.adapter.em.ContactListAdapter;
import app.dicky.meetthearrogance.adapter.em.EMContactListenerAdapter;
import app.dicky.meetthearrogance.app.Constant;
import app.dicky.meetthearrogance.mvpModel.ContactListItem;
import app.dicky.meetthearrogance.mvpPresenter.ContactPresenter;
import app.dicky.meetthearrogance.mvpPresenter.impl.ContactPresenterImpl;
import app.dicky.meetthearrogance.mvpView.ContactView;
import app.dicky.meetthearrogance.ui.activity.AddFriendActivity;
import app.dicky.meetthearrogance.ui.activity.ChatActivity;
import app.dicky.meetthearrogance.widget.SlideBar;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 22:37
 * 描述：    TODO
 */
public class ContactFragment extends BaseFragment implements ContactView {
    public static final String TAG = "ContactFragment";
    private static final int POSITION_NOT_FOUND = -1;

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.add)
    ImageView mAdd;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.slide_bar)
    SlideBar mSlideBar;
    @BindView(R.id.section)
    TextView mSection;
    @BindView(R.id.head_title)
    RelativeLayout mHeadTitle;
    private ContactListAdapter mContactListAdapter;


    private ContactPresenter mContactPresenter;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mContactPresenter.refreshContactList();
        }
    };
    private SlideBar.OnSlideBarChangeListener mOnSlideBarChangeListener = new SlideBar.OnSlideBarChangeListener() {
        @Override
        public void onSectionChange(int index, String section) {
            mSection.setVisibility(View.VISIBLE);
            mSection.setText(section);
            scrollToSection(section);
        }

        @Override
        public void onSlidingFinish() {
            mSection.setVisibility(View.GONE);
        }
    };
    private EMContactListenerAdapter mEMContactListener = new EMContactListenerAdapter() {

        @Override
        public void onContactAdded(String s) {
            mContactPresenter.refreshContactList();
        }

        @Override
        public void onContactDeleted(String s) {
            mContactPresenter.refreshContactList();
        }
    };
    private ContactListAdapter.OnItemClickListener mOnItemClickListener = new ContactListAdapter.OnItemClickListener() {

        /**
         * 单击跳转到聊天界面
         * @param name 点击item的联系人名字
         */
        @Override
        public void onItemClick(String name) {
            startActivity(ChatActivity.class, Constant.Extra.USER_NAME, name);
        }

        /**
         * 长按删除好友
         * @param name 点击item的联系人名字
         */
        @Override
        public void onItemLongClick(final String name) {
            showDeleteFriendDialog(name);
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        super.init();
        mContactPresenter = new ContactPresenterImpl(this);
        initView();
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        mContactPresenter.getContactsFromServer();
    }

    private void initView() {
        mTitle.setText(getString(R.string.contacts));
        mAdd.setVisibility(View.VISIBLE);
        mHeadTitle.setBackgroundColor(Color.parseColor("#f28379"));
        initRecyclerView();

        mSwipeRefreshLayout.setColorSchemeResources(R.color.qq_blue, R.color.qq_red);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mSlideBar.setOnSlidingBarChangeListener(mOnSlideBarChangeListener);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mContactListAdapter = new ContactListAdapter(getContext(), mContactPresenter.getContactList());
        mContactListAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mContactListAdapter);
    }

    @OnClick(R.id.add)
    public void onClick() {
        startActivity(AddFriendActivity.class, false);
    }

    @Override
    public void onGetContactListSuccess() {
        mContactListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetContactListFailed() {
        toast(getString(R.string.get_contacts_error));
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDeleteFriendSuccess() {
        hideProgress();
        toast(getString(R.string.delete_friend_success));
        mContactPresenter.refreshContactList();
    }

    @Override
    public void onDeleteFriendFailed() {
        hideProgress();
        toast(getString(R.string.delete_friend_failed));
    }

    /**
     * RecyclerView滚动直到界面出现对应section的联系人
     *
     * @param section 首字符
     */
    private void scrollToSection(String section) {
        int sectionPosition = getSectionPosition(section);
        if (sectionPosition != POSITION_NOT_FOUND) {
            mRecyclerView.smoothScrollToPosition(sectionPosition);
        }
    }

    /**
     * @param section 首字符
     * @return 在联系人列表中首字符是section的第一个联系人在联系人列表中的位置
     */
    private int getSectionPosition(String section) {
        List<ContactListItem> contactListItems = mContactListAdapter.getContactListItems();
        for (int i = 0; i < contactListItems.size(); i++) {
            if (section.equals(contactListItems.get(i).getFirstLetterString())) {
                return i;
            }
        }
        return POSITION_NOT_FOUND;
    }

    private void showDeleteFriendDialog(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String message = String.format(getString(R.string.delete_friend_message), name);
        builder.setTitle(getString(R.string.delete_friend))
                .setMessage(message)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showProgress(getString(R.string.deleting_friend));
                        mContactPresenter.deleteFriend(name);

                    }
                });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().contactManager().removeContactListener(mEMContactListener);
    }
}
