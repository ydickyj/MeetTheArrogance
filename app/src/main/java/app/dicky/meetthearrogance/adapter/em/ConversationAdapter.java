package app.dicky.meetthearrogance.adapter.em;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hyphenate.chat.EMConversation;

import org.androidannotations.annotations.rest.Head;

import java.util.List;

import app.dicky.meetthearrogance.database.bean.HeadEm;
import app.dicky.meetthearrogance.widget.ConversationItemView;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/21 10:11
 * 描述：    TODO
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationItemViewHolder> {
    public static final String TAG = "ConversationAdapter";

    public Context mContext;
    //    public List<EMConversation> mEMConversations;
    public List<HeadEm> mHeadEm;

    public ConversationAdapter(Context context, List<HeadEm> conversations) {
        mContext = context;
        mHeadEm = conversations;
    }

    @Override
    public ConversationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConversationItemViewHolder(new ConversationItemView(mContext));
    }

    @Override
    public void onBindViewHolder(ConversationItemViewHolder holder, int position) {
        holder.mConversationItemView.bindView(mHeadEm.get(position).getmEMConversation(), mHeadEm.get(position).getmHeadPortrait());
    }

    @Override
    public int getItemCount() {
        return mHeadEm.size();
    }


    public class ConversationItemViewHolder extends RecyclerView.ViewHolder {

        public ConversationItemView mConversationItemView;

        public ConversationItemViewHolder(ConversationItemView itemView) {
            super(itemView);
            mConversationItemView = itemView;
        }
    }
}
