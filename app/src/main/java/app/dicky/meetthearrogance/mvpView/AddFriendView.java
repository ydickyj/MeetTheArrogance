package app.dicky.meetthearrogance.mvpView;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/19 14:40
 * 描述：    TODO
 */
public interface AddFriendView {
    public static final String TAG = "AddFriendView";

    void onStartSearch();

    void onSearchSuccess();

    void onSearchFailed();

    void onAddFriendSuccess();

    void onAddFriendFailed();
}
