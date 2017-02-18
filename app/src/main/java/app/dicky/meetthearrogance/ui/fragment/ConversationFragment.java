package app.dicky.meetthearrogance.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;


import java.util.List;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.adapter.em.ConversationAdapter;
import app.dicky.meetthearrogance.adapter.em.EMMessageListenerAdapter;
import app.dicky.meetthearrogance.mvpPresenter.ConversationPresenter;
import app.dicky.meetthearrogance.mvpPresenter.impl.ConversationPresenterImpl;
import app.dicky.meetthearrogance.mvpView.ConversationView;
import app.dicky.meetthearrogance.utils.ThreadUtils;
import butterknife.BindView;

import static app.dicky.meetthearrogance.R.color.groove_color;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/17 22:32
 * 描述：    TODO
 */
public class ConversationFragment extends BaseFragment implements ConversationView {

    public static final String TAG = "ConversationFragment";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.head_title)
    RelativeLayout mHeadTitle;

    private ConversationAdapter mConversationAdapter;

    private ConversationPresenter mConversationPresenter;
    private EMMessageListenerAdapter mEMMessageListenerAdapter = new EMMessageListenerAdapter() {

        @Override
        public void onMessageReceived(List<EMMessage> list) {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast(getString(R.string.receive_new_message));
                    mConversationPresenter.loadAllConversations();
                }
            });
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void init() {
        super.init();
        mConversationPresenter = new ConversationPresenterImpl(this);
        mTitle.setText(getString(R.string.messages));
        mHeadTitle.setBackgroundColor(Color.parseColor("#0093b9"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mConversationAdapter = new ConversationAdapter(getContext(), mConversationPresenter.getmConversations());
        mRecyclerView.setAdapter(mConversationAdapter);

        mConversationPresenter.loadAllConversations();
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListenerAdapter);

    }

    @Override
    public void onAllConversationsLoaded() {
        toast(getString(R.string.load_conversations_success));
        mConversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mConversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListenerAdapter);
    }
}
