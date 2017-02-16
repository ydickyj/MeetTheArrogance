package app.dicky.meetthearrogance.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by dicky on 2015/9/17.
 *
 *
 */
public class ImageUtils {
    public static Bitmap getResizeBitmap(String path, int[] imageSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // only get bitmap infomation
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calcSampleSize(options, imageSize);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calcSampleSize(BitmapFactory.Options options, int[] imageSize) {
        Log.i("", options.outWidth + "," + imageSize[0] + "\n" + options.outHeight + "," + imageSize[1] + "\n------------\n");
        int inSampleSize = 1;
        if (imageSize[0] < options.outWidth || imageSize[1] < options.outHeight) {
            int widthRadio = Math.round(options.outWidth * 1.0f / imageSize[0]);
            int heightRadio = Math.round(options.outHeight * 1.0f / imageSize[1]);
            inSampleSize = Math.min(widthRadio, heightRadio);
        }
        return inSampleSize;
    }


    public static void getImageViewSize(ImageView imageView, int[] imageSize) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        imageSize[0] = imageView.getWidth();
        if (imageSize[0] <= 0) {
            imageSize[0] = layoutParams.width;
        }

        if (imageSize[0] <= 0) {
            imageSize[0] = getImageViewField(imageView, "mMaxWidth");
        }

        if (imageSize[0] <= 0) {
            DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
            imageSize[0] = displayMetrics.widthPixels;
        }

        imageSize[1] = imageView.getHeight();
        if (imageSize[1] <= 0) {
            imageSize[1] = layoutParams.height;
        }

        if (imageSize[1] <= 0) {
            imageSize[1] = getImageViewField(imageView, "mMaxHeight");
        }

        if (imageSize[1] <= 0) {
            DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
            imageSize[1] = displayMetrics.heightPixels;
        }

    }

    private static int getImageViewField(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void setImageBitmap(ImageView imageView, String imagePath) {
        //resize image and load image
        int[] imageSize = new int[2];
        getImageViewSize(imageView, imageSize);
        //resize bitmap
        Bitmap bitmap = ImageUtils.getResizeBitmap(imagePath, imageSize);
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static Intent startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 保存裁剪之后的图片数据
     */
    public static Bitmap setImageToView(Intent data, Uri uri) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = toRoundBitmap(photo, uri); // 这个时候的图片已经被处理成圆形的了
            return photo;
        } else {
            return null;
        }
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap  传入Bitmap对象
     * @param tempUri
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap, Uri tempUri) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);//
        // 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() + "/temporaryPhoto/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File myCaptureFile = new File(path + fileName);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

    /**
     * 获取SD卡路径
     *
     * @throws IOException
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
