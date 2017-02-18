package app.dicky.meetthearrogance.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;


import java.util.Date;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.app.Constant;
import app.dicky.meetthearrogance.ui.activity.ChatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建者:   dicky
 * 创建时间:  2016/10/21 10:20
 * 描述：    TODO
 */
public class ConversationItemView extends RelativeLayout {
    public static final String TAG = "ConversationItemView";
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.last_message)
    TextView mLastMessage;
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.unread_count)
    TextView mUnreadCount;
    @BindView(R.id.conversation_item_container)
    RelativeLayout mConversationItemContainer;
    @BindView(R.id.avatar)
    ImageView mHead;

    public ConversationItemView(Context context) {
        this(context, null);
    }

    public ConversationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_conversation_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(final EMConversation emConversation, String headPath) {
        mUserName.setText(emConversation.getLastMessage().getUserName());
        updateLastMessage(emConversation);
        updateUnreadCount(emConversation);
        Bitmap bitmap = BitmapFactory.decodeFile(headPath);
        mHead.setImageBitmap(bitmap);
        mConversationItemContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(Constant.Extra.USER_NAME, emConversation.getLastMessage().getUserName());
                getContext().startActivity(intent);
            }
        });

    }

    private void updateLastMessage(EMConversation emConversation) {
        EMMessage emMessage = emConversation.getLastMessage();
        if (emMessage.getBody() instanceof EMTextMessageBody) {
            mLastMessage.setText(((EMTextMessageBody) emMessage.getBody()).getMessage());
        } else {
            mLastMessage.setText(getContext().getString(R.string.no_text_message));
        }
        mTimestamp.setText(DateUtils.getTimestampString(new Date(emMessage.getMsgTime())));
    }

    private void updateUnreadCount(EMConversation emConversation) {
        int unreadMsgCount = emConversation.getUnreadMsgCount();
        if (unreadMsgCount > 0) {
            mUnreadCount.setVisibility(VISIBLE);
            mUnreadCount.setText(String.valueOf(unreadMsgCount));
        } else {
            mUnreadCount.setVisibility(GONE);
        }
    }
}
