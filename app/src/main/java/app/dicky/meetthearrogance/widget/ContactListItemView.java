package app.dicky.meetthearrogance.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.mvpModel.ContactListItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/18 12:17
 * 描述：    TODO
 */
public class ContactListItemView extends RelativeLayout {
    public static final String TAG = "ContactItemView";
    @BindView(R.id.section)
    TextView mSection;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.new_head_portrait)
    ImageView mHeadPortrait;
    public ContactListItemView(Context context) {
        this(context, null);
    }

    public ContactListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_contact_item, this);
        ButterKnife.bind(this, this);

    }

    public void bindView(ContactListItem contactListItem) {
        mUserName.setText(contactListItem.userName);
        Bitmap bitmap = BitmapFactory.decodeFile(contactListItem.headPath);
        mHeadPortrait.setImageBitmap(bitmap);
        if (contactListItem.showFirstLetter) {
            mSection.setVisibility(VISIBLE);
            mSection.setText(contactListItem.getFirstLetterString());
        } else {
            mSection.setVisibility(GONE);
        }
    }
}
