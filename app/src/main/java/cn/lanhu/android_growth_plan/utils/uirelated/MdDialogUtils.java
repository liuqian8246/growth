package cn.lanhu.android_growth_plan.utils.uirelated;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.lanhu.android_growth_plan.R;
import cn.lanhu.android_growth_plan.adapter.abslistview.CommonAdapter;
import cn.lanhu.android_growth_plan.adapter.abslistview.ViewHolder;
import cn.lanhu.android_growth_plan.gaiban.app.config.RxApplication;
import cn.lanhu.android_growth_plan.gaiban.mvvm.view.activity.MainActivity;
import cn.lanhu.android_growth_plan.net.rxevent.RxBus;
import cn.lanhu.android_growth_plan.net.rxevent.RxCodeConstants;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by yx on 2016/8/11.
 * dialog工具类
 */
public class MdDialogUtils {


    private static MdDialogUtils INSTANCE;

    private MaterialDialog mMaterialDialog;

    private MdDialogUtils() {
    }

    public static MdDialogUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (MdDialogUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MdDialogUtils();
                }
            }
        }

        return INSTANCE;
    }


    /***
     * 获取一个dialog
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    public static AlertDialog getCreateDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        return alertDialog;
    }

    /***
     * 获取一个耗时等待对话框
     *
     * @param context
     * @param message
     * @return
     */
    public static Dialog getWaitDialog(Context context, String message) {
        return getWaitDialog(context, message, true);
    }


    public static Dialog getWaitDialog(Context context, String message, boolean isCancel) {
        Dialog dialog = new Dialog(context, R.style.progress_dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_loading_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        AppCompatTextView tvMsg = dialog.findViewById(R.id.tv_loading);
        if (isCancel) {
            dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dialogInterface.dismiss();
                }
                return false;
            });
        }

        if (!message.isEmpty()) {
            tvMsg.setText(message);
        }
        return dialog;
    }

    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     *
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        return builder;
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setPositiveButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays,
                                                            int selectIndex, DialogInterface.OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
    }


    public static List<String> createList(String... str) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, str);
        return list;
    }


    public static void showListViewDialog(Context context, List<String> list, String title, TextView textView) {
        MaterialDialog materialDialog = new MaterialDialog(context).setTitle(title).setCanceledOnTouchOutside(true);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(0);
        listView.setAdapter(new CommonAdapter<String>(context, R.layout.item_shaixuan, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String str, int position) {
                viewHolder.setText(R.id.tv_riqi, str);
            }
        });
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            materialDialog.dismiss();
            textView.setText(list.get(position));
        });
        materialDialog.setContentView(listView);
        materialDialog.show();
    }

    public static void showListViewDialog(Context context, List<String> list, String title,
                                          TextView textView, OnDialogConfirmListener listener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setTitle(title).setCanceledOnTouchOutside(true);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(0);
        listView.setAdapter(new CommonAdapter<String>(context, R.layout.item_shaixuan, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String str, int position) {
                viewHolder.setText(R.id.tv_riqi, str);
            }
        });
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            materialDialog.dismiss();
            textView.setText(list.get(position));
            listener.onConfirmClick(view1);
        });
        materialDialog.setContentView(listView);
        materialDialog.show();
    }

    public static void showListViewDialog(Context context, List<String> list, String title, OnItemClickListener listener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setTitle(title).setCanceledOnTouchOutside(true);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(0);
        listView.setAdapter(new CommonAdapter<String>(context, R.layout.item_shaixuan, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String str, int position) {
                viewHolder.setText(R.id.tv_riqi, str);
            }
        });
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            materialDialog.dismiss();
            listener.onItemClick(view1, position);

        });
        materialDialog.setContentView(listView);
        materialDialog.show();

    }


    public static void showMesseageDialog(Context context, String title, String text, OnDialogConfirmListener listener) {

        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(true).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton("确定", v -> {
            materialDialog.dismiss();
            listener.onConfirmClick(v);
        }).setNegativeButton("取消", v -> materialDialog.dismiss());
        materialDialog.show();

    }

    public static void showNoCancleMesseageDialog(Context context, String title, String text, OnDialogConfirmListener listener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(true).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton("确定", v -> {
            materialDialog.dismiss();
            listener.onConfirmClick(v);
        });
        materialDialog.show();

    }

    public static void showNoTouchOutsiceMsgDialog(Context context, String title, String text, OnDialogConfirmListener listener) {

        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(false).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton("确定", v -> {
            materialDialog.dismiss();
            listener.onConfirmClick(v);
        });
        materialDialog.show();

    }

    /**
     * 全部弹出dialog
     *
     * @param title
     * @param text
     * @param confirmText
     * @param listener
     */
    public static void showApplicationMsgDialog(Activity activity, String title, String text, String confirmText, OnDialogConfirmListener listener) {
        MaterialDialog materialDialog = new MaterialDialog(activity)
            .setCanceledOnTouchOutside(false).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton(confirmText, v -> {
            materialDialog.dismiss();
            listener.onConfirmClick(v);
        });
        materialDialog.show();
    }


    public static void showTokenGuoqiDialog(Context context, String msg) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(false)
            .setTitle("提示").setMessage(msg);
        materialDialog.setPositiveButton("确定", v -> {
            materialDialog.dismiss();
            RxApplication.getInstance().cleanLoginInfo();
            RxApplication.getInstance().clearAppCache();
            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, RxCodeConstants.TYPE_USER_LOGOUT);
            context.startActivity(new Intent(context, MainActivity.class));
        });
        materialDialog.show();
    }

    public static void showLocationDialog(Context context, String title, String text, String btnText, OnDialogConfirmListener listener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(true)
            .setTitle(title).setMessage(text);
        materialDialog.setPositiveButton(btnText, v -> {
            materialDialog.dismiss();
            listener.onConfirmClick(v);
        }).setNegativeButton("取消", v -> materialDialog.dismiss());
        materialDialog.show();

    }

    public static void showMsgDialog(Context context, String title, String text, String confirm, String cancle,
                                     OnDialogConfirmListener confirmListener, OnDialogCancleListener cancleListener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(true)
            .setTitle(title).setMessage(text);
        materialDialog.setPositiveButton(confirm, v -> {
            materialDialog.dismiss();
            confirmListener.onConfirmClick(v);
        }).setNegativeButton(cancle, v -> {
            cancleListener.onCancleClick(v);
            materialDialog.dismiss();
        });
        materialDialog.show();

    }

    public static void showMsgDialog(Context context, String title, String text, String confirm, boolean isCancle,
                                     OnDialogConfirmListener confirmListener) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(isCancle).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton(confirm, v -> {
            materialDialog.dismiss();
            confirmListener.onConfirmClick(v);
        });
        materialDialog.show();

    }


    public static void showMsgDialog(Context context, String title, String text, String confirm, String cancle,
                                     OnDialogConfirmListener confirmListener, OnDialogCancleListener cancleListener
        , OnDialogDismissListener dismissListener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(true)
            .setTitle(title).setMessage(text);
        materialDialog.setPositiveButton(confirm, v -> {
            materialDialog.dismiss();
            confirmListener.onConfirmClick(v);
        }).setNegativeButton(cancle, v -> {
            cancleListener.onCancleClick(v);
            materialDialog.dismiss();
        })
            .setOnDismissListener(dismissListener::onDismiss);
        materialDialog.show();

    }

    public static void showMsgDialog(Context context, String title, String text, String confirm, String cancle, boolean isCancle,
                                     OnDialogConfirmListener confirmListener, OnDialogCancleListener cancleListener) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(isCancle).setTitle(title)
            .setMessage(text);
        materialDialog.setPositiveButton(confirm, v -> {
            materialDialog.dismiss();
            confirmListener.onConfirmClick(v);
        }).setNegativeButton(cancle, v -> {
            cancleListener.onCancleClick(v);
            materialDialog.dismiss();
        });
        materialDialog.show();

    }

    public static void showNewFileDialog(Context context, String errorMsg, OnDialogConfirmListener confirmListener) {
        Dialog dialog = new Dialog(context, R.style.alert_dialog);
        View view = View.inflate(context, R.layout.dialog_new_file, null);
        dialog.setContentView(view);
        EditText edit = view.findViewById(R.id.et_file_name);
        Button confirm = view.findViewById(R.id.confirm_button);
        Button cancel = view.findViewById(R.id.cancel_button);
        confirm.setOnClickListener(v -> {
            if (edit.getText().toString().isEmpty()) {
                ToastUtils.showToast(errorMsg);
            } else {
                dialog.dismiss();
                confirmListener.onConfirmClick(edit);
            }
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void showPositiveDialog(Context context, String msg) {
        MaterialDialog materialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(false)
            .setTitle("提示").setMessage(msg);
        materialDialog.setPositiveButton("确定", v -> materialDialog.dismiss());
        materialDialog.show();
    }


    public void showLongWaitDialog(Context context) {
        View view = View.inflate(context, R.layout.layout_longwait_dialog, null);
        mMaterialDialog = new MaterialDialog(context).setCanceledOnTouchOutside(false)
            .setContentView(view);
        mMaterialDialog.show();
    }

    public void hideLongWaitDialog() {
        if (mMaterialDialog == null) {
            Logger.w("mMaterialDialog==null");
            return;
        }
        mMaterialDialog.dismiss();
    }

    public interface OnDialogConfirmListener {
        void onConfirmClick(View view);
    }

    public interface OnDialogCancleListener {
        void onCancleClick(View view);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Integer integer);
    }

    public interface OnDialogDismissListener {
        void onDismiss(DialogInterface dialogInterface);
    }



}
