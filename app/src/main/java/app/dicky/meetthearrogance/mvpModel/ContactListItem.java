package app.dicky.meetthearrogance.mvpModel;


import app.dicky.meetthearrogance.R;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/18 12:10
 * 描述：    TODO
 */
public class ContactListItem {
    public static final String TAG = "ContactListItem";

    public int avatar = R.mipmap.avatar6;

    public String userName;

    public boolean showFirstLetter = true;

    public char getFirstLetter() {
        return userName.charAt(0);
    }

    public String getFirstLetterString() {
        return String.valueOf(getFirstLetter()).toUpperCase();
    }
}
