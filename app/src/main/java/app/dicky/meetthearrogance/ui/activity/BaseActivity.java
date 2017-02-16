package app.dicky.meetthearrogance.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/15
 * 描述：    TODO
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    private Handler mHandler = new Handler();

    private ProgressDialog mProgressDialog;

    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public abstract int getLayoutRes();

    protected void startActivity(Class activity) {
        startActivity(activity, true);
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void post(Runnable runnable) {
        postDelay(runnable, 0);
    }

    protected void postDelay(Runnable runnable, long millis) {
        mHandler.postDelayed(runnable, millis);
    }

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyBoard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Toast.makeText(this, "后退键", Toast.LENGTH_SHORT).show();
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
//            Toast.makeText(this, "声音+", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
//            Toast.makeText(this, "声音-", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_MUTE) {
//            Toast.makeText(this, "静音", Toast.LENGTH_SHORT).show();
//            return false;
//        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
//            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
