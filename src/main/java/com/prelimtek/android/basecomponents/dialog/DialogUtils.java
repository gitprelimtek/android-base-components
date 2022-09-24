package com.prelimtek.android.basecomponents.dialog;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.prelimtek.android.customcomponents.R;


public class DialogUtils {

    public static Dialog startProgressDialog(Context context , String message){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           return createProgressDialog(context,message);
        }else{
           return createProgressDialog_1(context,message);
        }
    }

    public static Dialog startProgressDialog(Context context ){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return createProgressDialog(context,"");
        }else{
            return createProgressDialog_1(context,"");
        }
    }

    public static void startProgressDialogRunnable(final Activity context, final String message){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    createProgressDialog(context,message);
                }else{
                    createProgressDialog_1(context,message);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected static Dialog createProgressDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.process_running_layout_2);

        //AlertDialog dialog = builder.create();
        Dialog dialog = new Dialog(context,R.style.PtekGenericDialog);
        dialog.setContentView(R.layout.process_running_layout_2);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView messageView = dialog.findViewById(R.id.process_message);
        if(messageView!=null)messageView.setText(message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    protected static Dialog createProgressDialog_1(Context context, String message) {
        ProgressBar bar = new ProgressBar(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(bar);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


    public static void startErrorDialogRunnable( Activity activity, final String message, boolean autocancel){

        final Context context = activity.getBaseContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     startErrorDialog(context,message,autocancel);
                }else{
                     startErrorDialog_1(context,message,autocancel);
                }
            }
        });
    }

    public static void startErrorDialogRunnable( Activity activity, final String message){
        startErrorDialogRunnable(activity,message,true);
    }

    public static Dialog startErrorDialog(Context context, String message){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return startErrorDialog(context,message,true);
        }else{
            return startErrorDialog_1(context,message,true);
        }
    }

    public static AlertDialog startErrorDialog_1(Context context, String message,boolean autocancel){

        if(Looper.myLooper()==null)Looper.prepare();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //TODO add icon dialogBuilder.setIcon()
        dialogBuilder.setMessage(message)
                .setTitle(R.string.error_message);
        dialogBuilder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog errorDialog = dialogBuilder.create();
        errorDialog.getWindow().setType(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);
        //errorDialog.getWindow().setGravity(Gravity.TOP);
        errorDialog.setCanceledOnTouchOutside(autocancel);

        errorDialog.show();
        return errorDialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Dialog startErrorDialog(Context context, String message, boolean autocancel){
        return createGenericInfoDialog(context,R.string.error_message,R.drawable.sad_cloud_100,"",message,autocancel);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Dialog createGenericInfoDialog(Context context, int title_id, int icon_id, String message, String errorMessage, boolean autocancel){
        if(Looper.myLooper()==null)Looper.prepare();

        Dialog errorDialog = new Dialog(context,R.style.PtekGenericDialog);
        errorDialog.setTitle(title_id);
        errorDialog.setContentView(R.layout.generic_info_dialog_layout);
        errorDialog.setCancelable(true);
        errorDialog.setCanceledOnTouchOutside(autocancel);
        errorDialog.show();

        ImageView iconView = errorDialog.findViewById(R.id.generic_icon);
        if(icon_id>0) {
            Drawable icon = context.getResources().getDrawable(icon_id);
            if (iconView != null && icon != null) iconView.setImageDrawable(icon);
        }
        TextView errorTxtView = errorDialog.findViewById(R.id.generic_errorMessage);
        if(errorTxtView!=null)errorTxtView.setText(errorMessage);

        TextView msgTxtView = errorDialog.findViewById(R.id.generic_message);
        if(msgTxtView!=null)msgTxtView.setText(message);

        return errorDialog;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Dialog startNetworkErrorDialog(Context context, String message, boolean autocancel){
        return createGenericInfoDialog(context,R.string.error_message,R.drawable.sad_cloud_100,"",message,autocancel);
    }

    public static AlertDialog createErrorDialog_1(Context context, String message,boolean autocancel){
        if(Looper.myLooper()==null)Looper.prepare();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //TODO add icon dialogBuilder.setIcon()
        dialogBuilder.setMessage(message)
                .setTitle(R.string.error_message);
        dialogBuilder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog errorDialog = dialogBuilder.create();
        errorDialog.getWindow().setType(WindowManager.LayoutParams.LAST_APPLICATION_WINDOW);
        //errorDialog.getWindow().setGravity(Gravity.TOP);
        errorDialog.setCanceledOnTouchOutside(autocancel);

        errorDialog.show();
        return errorDialog;
    }

    public static AlertDialog startInfoDialog(Context context, CharSequence title, String message){
        if(Looper.myLooper()==null)Looper.prepare();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //TODO add icon dialogBuilder.setIcon()
        dialogBuilder.setMessage(message);
        if(title!=null)
            dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton(R.string.ok,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog errorDialog = dialogBuilder.create();
        errorDialog.setCanceledOnTouchOutside(true);
        errorDialog.show();
        return errorDialog;
    }
    /**<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/> in manifest*/
    public static void showSystemPopupWindow(Context context, String message){
        WindowManager windowManager2 = (WindowManager)context.getSystemService(WINDOW_SERVICE);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.popup_message_layout, null);
        TextView text = view.findViewById(R.id.popup_message_text);
        //ImageView image = view.findViewById(R.id.popup_message_image);
        text.setText(message);
        WindowManager.LayoutParams params=new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity= Gravity.CENTER|Gravity.CENTER;
        params.x=0;
        params.y=0;
        windowManager2.addView(view, params);
    }

    public static AlertDialog startInfoDialog(Context context, CharSequence title, String message, DialogInterface.OnClickListener positiveListener){

        return startInfoDialog(context,title,message,positiveListener, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
    }


    public static AlertDialog startInfoDialog(Context context, CharSequence title, String message, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //TODO add icon dialogBuilder.setIcon()
        dialogBuilder.setMessage(message);
        if(title!=null)
            dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton(R.string.ok,positiveListener);
        dialogBuilder.setNegativeButton(R.string.cancel,negativeListener);
        AlertDialog errorDialog = dialogBuilder.create();
        errorDialog.setCanceledOnTouchOutside(false);
        errorDialog.show();
        return errorDialog;
    }

    /**
     * This is an SDK agnostic way to get a view's (dialog or fragment) parent activity.
     * */
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }

        return (Activity)context;
    }
}
