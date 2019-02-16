package cn.lanhu.android_growth_plan.utils.camera;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.abslistview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.abslistview.ViewHolder;
import cn.lanhu.android_growth_plan.utils.uirelated.MdDialogUtils;
import cn.lanhu.android_growth_plan.utils.uirelated.ToastUtils;
import me.drakeet.materialdialog.MaterialDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yx on 2017/5/16.
 * 可裁剪相机 相册Dailog类
 */
public class ImagePickSelectUtils implements OnClickListener {

    private Dialog mWayDialog;
    private Context mContext;
    private TextView mTvCamera, mTvAlbum, mTvCancel;//相机  相册 取消
    private CameraAlbumUtils mCameraAlbumUtils;
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;
    private boolean mIsCrop = true;
    private MaterialDialog mMaterialDialog;

    /**
     * 从Fragment启动
     *
     * @param fragment
     */
    public ImagePickSelectUtils(Fragment fragment, String imageName) {
        if (mFragment == null) {
            mFragment = new WeakReference<>(fragment);
            if (!imageName.endsWith(".jpg")) {
                throw new IllegalStateException("imageName must endWith .jpg");
            }
            mCameraAlbumUtils = new CameraAlbumUtils(fragment, imageName);
        }
        mContext = fragment.getActivity();
    }

    /**
     * 从Activity启动
     *
     * @param activity
     */
    public ImagePickSelectUtils(Activity activity, String imageName) {
        if (mActivity == null) {
            mActivity = new WeakReference<>(activity);
            if (!imageName.endsWith(".jpg")) {
                throw new IllegalStateException("imageName must endWith .jpg");
            }
            mCameraAlbumUtils = new CameraAlbumUtils(activity, imageName);
        }
        mContext = activity;
    }

    /**
     * 弹出仿QQ dialog 选择拍照 相册
     *
     */
    public void show() {
        if (mWayDialog == null) {
            mWayDialog = new Dialog(mContext, R.style.dialog);
            View view = View.inflate(mContext, R.layout.dialog_photo_choice, null);
            mWayDialog.setCancelable(true);
            mWayDialog.setCanceledOnTouchOutside(true);
            mWayDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            Window window = mWayDialog.getWindow();
            //设置动画
            window.setWindowAnimations(R.style.dialogWindowAnim);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = mActivity.get().getWindowManager().getDefaultDisplay().getHeight();
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mWayDialog.onWindowAttributesChanged(wl);
            mTvCamera = (TextView) mWayDialog.findViewById(R.id.tv_paishe);
            mTvAlbum = (TextView) mWayDialog.findViewById(R.id.tv_xiangce);
            mTvCancel = (TextView) mWayDialog.findViewById(R.id.tv_cancle);
            mTvCamera.setOnClickListener(this);
            mTvAlbum.setOnClickListener(this);
            mTvCancel.setOnClickListener(this);
        }
        mWayDialog.show();
    }

    /**
     * 弹出MD风格dialog 选择拍照 相册
     */
    public void showMdDialog() {
        if (mMaterialDialog == null) {
            mMaterialDialog = new MaterialDialog(mContext);
            ListView listView = new ListView(mContext);
            listView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            listView.setDividerHeight(0);
            listView.setAdapter(new CommonAdapter<String>(mContext, R.layout.item_shaixuan,
                    MdDialogUtils.createList("拍摄照片", "从相册选择")) {
                @Override
                protected void convert(ViewHolder viewHolder, String str, int position) {
                    viewHolder.setText(R.id.tv_riqi, str);
                }
            });
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                mMaterialDialog.dismiss();
                switch (position) {
                    case 0:
                        //相机
                        mCameraAlbumUtils.openCamera();
                        break;
                    case 1:
                        //相册
                        mCameraAlbumUtils.openAlbum();
                        break;
                }

            });
            mMaterialDialog.setContentView(listView).setCanceledOnTouchOutside(true).setTitle("设置头像");
            mMaterialDialog.show();

        } else {
            mMaterialDialog.show();
        }
    }

    public void dismiss() {
        mWayDialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_paishe:
                mCameraAlbumUtils.openCamera();
                break;
            case R.id.tv_xiangce:
                mCameraAlbumUtils.openAlbum();
                break;
            case R.id.tv_cancle:
                break;
        }
        dismiss();
    }

    /**
     * 获取图片回调 在activity或fragment 的 onActivityResult 中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public Bitmap onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CameraAlbumUtils.CAMERA_REQUEST_CODE:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        if (mIsCrop) {
                            startPhotoZoom(mCameraAlbumUtils.getFileUri());
                        } else {
                            return getBitmapFromUri(mCameraAlbumUtils.getFileUri());
                        }
                    } else {
                        if (mIsCrop) {
                            startPhotoZoom(mCameraAlbumUtils.getFileUri());
                        } else {
                            return getBitmapFromUri(mCameraAlbumUtils.getFileUri());
                        }
                    }
                    break;
                case CameraAlbumUtils.ALBUM_REQUEST_CODE:
                    if (data != null) {
                        if (mIsCrop) {
                            startPhotoZoom(data.getData());
                        } else {
                            return getDataBitmap(data);
                        }
                    }
                    break;
                case CameraAlbumUtils.CROP_REQUEST_CODE:
                    if (data != null) {
                        return getDataBitmap(data);
                    }
                    break;
            }
        }
        return null;
    }

    /**
     * 获取返回data中解析的bitmap
     * @param data
     * @return
     */
    private Bitmap getDataBitmap(Intent data) {
        Uri uri = data.getData();
        Bitmap bitmap = null;
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
                ToastUtils.showToast( "找不到图片");
                return null;
            }
        }
        return bitmap;
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
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        String startFrom = mCameraAlbumUtils.getStartFrom();
        if (startFrom.equals(CameraAlbumUtils.START_FROM_ACTIVITY)) {
            mActivity.get().startActivityForResult(intent, CameraAlbumUtils.CROP_REQUEST_CODE);
        } else if (startFrom.equals(CameraAlbumUtils.START_FROM_FRAGMENT)) {
            mFragment.get().startActivityForResult(intent, CameraAlbumUtils.CROP_REQUEST_CODE);
        }
    }

    /**
     * 将返回的URL解析为图片
     *
     * @param uri
     * @return
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext
                    .getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Logger.e("[Android]", e.getMessage());
            Logger.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    public void setCrop(Boolean crop) {
        mIsCrop = crop;
    }
}
