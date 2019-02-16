package cn.lanhu.android_growth_plan.utils.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.ref.WeakReference;

import cn.lanhu.android_growth_plan.utils.filerelated.FileUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yx on 2017/5/16.
 * 相机,相册工具类
 * 可以单独调用相机相册,也可以配合ImagePickSelectDialog使用
 */
public class CameraAlbumUtils {

    public final static int CAMERA_REQUEST_CODE = 101;//相机request code
    public final static int ALBUM_REQUEST_CODE = 102;//相册request code
    public final static int CROP_REQUEST_CODE = 103;//裁剪 request code
    public static final String START_FROM_ACTIVITY = "activity";
    public static final String START_FROM_FRAGMENT = "fragment";
    private Uri mFileUri;
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;
    private Intent mCameraIntent, mAlbumIntent;
    private String mStartFrom;
    private Context mContext;
    private File mPhotoFile;//图片文件
    private String mImageName;//图片名称
    private String mPhtotPath;//图片路径
    private boolean mIsCrop = false;

    public CameraAlbumUtils(Activity activity, String imageName) {
        if (mActivity == null) {
            mActivity = new WeakReference<>(activity);
        }
        mContext = activity;
        mStartFrom = START_FROM_ACTIVITY;
        this.mImageName = imageName;
    }

    public CameraAlbumUtils(Fragment fragment, String imageName) {
        if (mFragment == null) {
            mFragment = new WeakReference<>(fragment);
        }
        mContext = fragment.getContext();
        mStartFrom = START_FROM_FRAGMENT;
        this.mImageName = imageName;
    }

    /**
     * 打开相机
     */
    public void openCamera() {
        createImageFile();
        if (mCameraIntent == null) {
            mCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mFileUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", mPhotoFile);
                mCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

            } else {
                mFileUri = Uri.fromFile(mPhotoFile);
                mCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
            }
        }

        //判断是从fragment 还是 avtivity启动
        if (START_FROM_ACTIVITY.equals(mStartFrom)) {
            mActivity.get().startActivityForResult(mCameraIntent, CAMERA_REQUEST_CODE);
        } else if (START_FROM_FRAGMENT.equals(mStartFrom)) {
            mFragment.get().startActivityForResult(mCameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 创建路径
     */
    private void createImageFile() {
        String imagePath = FileUtils.getAvatarPath();
        mPhtotPath = imagePath + mImageName;
        mPhotoFile = new File(mPhtotPath);
        if (!mPhotoFile.exists()) {
            mPhotoFile.getParentFile().mkdirs();
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        if (mAlbumIntent == null) {
            mAlbumIntent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
            /* 开启Pictures画面Type设定为image */
            mAlbumIntent.setType("image/*");

        }
        //判断是从fragment 还是 avtivity启动
        if (START_FROM_ACTIVITY.equals(mStartFrom)) {
            mActivity.get().startActivityForResult(mAlbumIntent, ALBUM_REQUEST_CODE);
        } else if (START_FROM_FRAGMENT.equals(mStartFrom)) {
            mFragment.get().startActivityForResult(mAlbumIntent, ALBUM_REQUEST_CODE);
        }
    }

    public String getStartFrom() {
        return mStartFrom;
    }

    public Uri getFileUri() {
        return mFileUri;
    }

    public File getPhotoFile() {
        return mPhotoFile;
    }

    public String getPhotoPath() {
        return mPhtotPath;
    }

    public void setCrop(Boolean crop) {
        mIsCrop = crop;
    }

    /**
     * 获取返回的bitmap 在activity或者fragment的onACtivityResult里回调
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public Bitmap onResultWithBmp(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (mIsCrop) {
                            startPhotoZoom(mFileUri);
                        } else {
                            return getBitmapFromUri(mFileUri);
                        }
                    } else {
                        if (mIsCrop) {
                            startPhotoZoom(mFileUri);
                        } else {
                            return getBitmapFromUri(mFileUri);
                        }
                    }
                    break;
                case ALBUM_REQUEST_CODE:
                    if (data != null) {
                        if (mIsCrop) {
                            startPhotoZoom(data.getData());
                        } else {
                            return getDataBitmap(data);
                        }
                    }
                    break;
                case CROP_REQUEST_CODE:
                    if (data != null) {
                       return getDataBitmap(data);
                    }
                    break;
            }
        }
        return null;
    }

    /**
     * 将返回的URL解析为图片
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = Media.getBitmap(mContext
                    .getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Logger.e("[Android]", e.getMessage());
            Logger.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 开启裁剪
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        String startFrom = getStartFrom();
        if (startFrom.equals(CameraAlbumUtils.START_FROM_ACTIVITY)) {
            mActivity.get().startActivityForResult(intent, CROP_REQUEST_CODE);
        } else if (startFrom.equals(CameraAlbumUtils.START_FROM_FRAGMENT)) {
            mFragment.get().startActivityForResult(intent, CROP_REQUEST_CODE);
        }
    }

    /**
     * 获取图片
     *
     * @param data
     * @return
     */
    private Bitmap getDataBitmap(Intent data) {
        Bitmap bitmap = null;
        Uri uri = data.getData();
        if (uri != null) {
            bitmap = getBitmapFromUri(uri); //拿到图片
        }
        if (bitmap == null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                bitmap = (Bitmap) bundle.get("data");
                if (bitmap == null) {
                    bitmap = data.getParcelableExtra("data");
                    if (bitmap == null) {
                        bitmap = data.getExtras().getParcelable("data");
                    }
                }
            } else {
                ToastUtils.showToast("找不到图片");
                return null;
            }
        }
        return bitmap;
    }


}
