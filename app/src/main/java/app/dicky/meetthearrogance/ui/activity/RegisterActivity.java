package app.dicky.meetthearrogance.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;

import app.dicky.meetthearrogance.R;
import app.dicky.meetthearrogance.mvpPresenter.RegisterPresenter;
import app.dicky.meetthearrogance.mvpPresenter.impl.RegisterPresenterImpl;
import app.dicky.meetthearrogance.mvpView.RegisterView;
import app.dicky.meetthearrogance.utils.ImageUtils;
import butterknife.BindView;
import butterknife.OnClick;

import static app.dicky.meetthearrogance.utils.ImageUtils.startPhotoZoom;

/**
 * 创建者:   dicky
 * 创建时间:  2016/10/16 22:07
 * 描述：    TODO
 */
public class RegisterActivity extends BaseActivity implements RegisterView {
    public static final String TAG = "RegisterActivity";
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    protected File mHPImage;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.register)
    Button mRegister;
    @BindView(R.id.head_portrait)
    ImageView mHeadPortrait;
    private boolean headPortraitSelected = false;
    private Bitmap mBitmap;
    private RegisterPresenter mRegisterPresenter;
    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                register();
                return true;
            }
            return false;
        }
    };

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        super.init();
        mRegisterPresenter = new RegisterPresenterImpl(this);
        mConfirmPassword.setOnEditorActionListener(mOnEditorActionListener);

    }

    @Override
    public void onStartRegister() {
        showProgress(getString(R.string.registering));
    }

    @Override
    public void onRegisterError(String e) {
        hideProgress();
        toast(getString(R.string.register_failed) + e);
    }

    @Override
    public void onResisterUserExist() {
        hideProgress();
        toast(getString(R.string.register_failed_user_exist));
    }

    @Override
    public void onRegisterSuccess() {
        hideProgress();
        toast(getString(R.string.register_success));
        startActivity(LoginActivity.class);
    }

    @Override
    public void onUserNameError() {
        mUserName.setError(getString(R.string.user_name_error));
    }

    @Override
    public void onPasswordError() {
        mPassword.setError(getString(R.string.user_password_error));
    }

    @Override
    public void onConfirmPasswordError() {
        mConfirmPassword.setError(getString(R.string.user_password_confirm_error));
    }

    @OnClick({R.id.register, R.id.head_portrait})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                register();
                break;
            case R.id.head_portrait:
                showChoosePicDialog();
                break;
        }
    }

    private void register() {
        hideKeyBoard();
        String userName = mUserName.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();
        if (headPortraitSelected) {
            try {
                mHPImage = ImageUtils.saveFile(mBitmap, "headPortrait" + userName + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.register);
            try {
                mHPImage = ImageUtils.saveFile(bm, "headPortrait" + userName + ".jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mRegisterPresenter.register(userName, password, confirmPassword, mHPImage);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        );
                        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (tempUri == null) {
                        Log.e("tag", "The uri is not exist.");
                    } else {
                        Intent intent = startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                        startActivityForResult(intent, CROP_SMALL_PICTURE);
                    }
                    break;
                case CHOOSE_PICTURE:
                    if (data.getData() == null) {
                        Log.e("tag", "The uri is not exist.");
                        toast("The uri is not exist.");
                    } else {
//                        Intent intent =; // 开始对图片进行裁剪处理
                        startActivityForResult(startPhotoZoom(data.getData()), CROP_SMALL_PICTURE);
                    }

                    break;
                case CROP_SMALL_PICTURE:
                    if (data == null) {
                        Log.e("tag", "The uri is not exist.");
                        toast("图片选择失败");
                    } else {
                        mBitmap = ImageUtils.setImageToView(data, tempUri); // 让刚才选择裁剪得到的图片显示在界面上
                        if (mBitmap == null) {
                            toast("图片选择失败");
                        } else {
                            mHeadPortrait.setImageBitmap(mBitmap);
                            headPortraitSelected = true;
                        }
                    }
                    break;
            }
        }
    }
}
