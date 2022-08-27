package com.prelimtek.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.prelimtek.android.alerts.DisplayAlertsBroadcastReceiver;
import com.prelimtek.android.basecomponents.Configuration;
import com.prelimtek.android.basecomponents.dialog.DialogUtils;
import com.prelimtek.android.customcomponents.R;

public abstract class PtekBaseAppCompatActivity extends AppCompatActivity {

    private static final String TAG = PtekBaseAppCompatActivity.class.getName();
    protected Activity currentActivity;

    private UiModeManager uiModeManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);

        currentActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerDisplayAlertsBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if (displayAlertsCallback != null) {
        //    unregisterReceiver(displayAlertsCallback);
        //}

        //hideProgress();
        ActionBarUtilities.instance(currentActivity).hideProgress();
        hideAlertDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (displayAlertsCallback != null) {
            unregisterReceiver(displayAlertsCallback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Configuration.configuredPreferences(this).uiDarkMode){
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        }else{
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }

        currentActivity = this;
    }


    private BroadcastReceiver displayAlertsCallback;

    private BroadcastReceiver registerDisplayAlertsBroadcastReceiver(){
          displayAlertsCallback = new DisplayAlertsBroadcastReceiver(
                new DisplayAlertsBroadcastReceiver.AppReceiver(){
                    @Override
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        CharSequence message = resultData.getCharSequence(DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_MESSAGE_KEY);
                        String type = resultData.getString(DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_TYPE_KEY);
                        Activity activity = getCurrentOpActivity();
                        switch(type){
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_ERROR_MESSAGE_TYPE:
                                //showError(message==null?null:message.toString());
                                ActionBarUtilities.instance(activity).showError(message.toString());
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_NETWORK_ALERT_ERROR_MESSAGE_TYPE:
                                //showNetworkError(message==null?null:message.toString());
                                ActionBarUtilities.instance(activity).showNetworkError(message.toString());
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_NORMAL_MESSAGE_TYPE:
                                showSnackBarMessage(message==null?null:message.toString());
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_SHOW_PROGRESS_TYPE:
                                //showProgress(message==null?null:message.toString());
                                ActionBarUtilities.instance(activity).showProgress(message.toString());
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_UPDATE_PROGRESS_TYPE:
                                int progress = resultData.getInt(DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_PROGRESS_KEY,-1);
                                //updateProgress(message==null?null:message.toString(),progress);
                                ActionBarUtilities.instance(activity).updateProgress(message.toString(),progress);
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_HIDE_PROGRESS_TYPE:
                                //hideProgress();
                                ActionBarUtilities.instance(activity).hideProgress();
                                break;
                            case DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_POP_MESSAGE_TYPE:
                                //hideProgress();
                                showInfoMessage(message==null?null:message.toString());
                                break;
                            case DisplayAlertsBroadcastReceiver.SEND_NOTIFICATION_TYPE:
                                //hideProgress();
                                if(message!=null)
                                    showNotification(message.toString());
                                break;

                            default:
                                showError(message==null?null:message.toString());
                                break;
                        }

                    }
                }
        );
        IntentFilter intentFilter = new IntentFilter( DisplayAlertsBroadcastReceiver.DISPLAY_ALERT_ACTION);
        //LocalBroadcastManager.getInstance(this).registerReceiver(displayAlertsCallback, intentFilter);
        registerReceiver(displayAlertsCallback, intentFilter);

        return displayAlertsCallback;
    }


    protected void showNotification(String message){
        Log.i(TAG,"showNotification "+message);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"BaseNotification")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Notice")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat.from(this).notify(9876,builder.build());
    }

    private void showSnackBarMessage(String message){
        Log.i(TAG,"showSnackBarMessage "+message);
        Activity activity = getCurrentOpActivity();
        if(activity.getCurrentFocus()!=null) Snackbar.make(activity.getCurrentFocus(), message, BaseTransientBottomBar.LENGTH_LONG).setAction("Action", null).show();
        Toast.makeText(PtekBaseAppCompatActivity.this, message, Toast.LENGTH_SHORT).show();

    }

    private Dialog errorDialog;
    private void showNetworkError(String message){
        Log.i(TAG,"callbackError "+message);
        Activity activity = getCurrentOpActivity();
        if(errorDialog!=null && errorDialog.isShowing()){errorDialog.dismiss();}
        errorDialog= DialogUtils.startErrorDialog(activity,message);
        //DialogUtils.startErrorDialogRunnable(currentActivity,message,false);

    }
    private void showError(String message){
        Log.i(TAG,"callbackError "+message);
        Activity activity = getCurrentOpActivity();
        if(errorDialog!=null && errorDialog.isShowing()){errorDialog.dismiss();}
        errorDialog= DialogUtils.startErrorDialog(activity,message);
        //DialogUtils.startErrorDialogRunnable(currentActivity,message,false);

    }

    private void showInfoMessage(String message){
        Log.i(TAG,"callbackError "+message);
        Activity activity = getCurrentOpActivity();
        if(activity==null)return;
        if(errorDialog!=null && errorDialog.isShowing()){errorDialog.dismiss();}
        errorDialog= DialogUtils.startInfoDialog(activity,"Info",message);

    }

    private static Dialog progressDialog;
    private static ProgressBar bar;
    private static TextView progressTextView;
    private void showProgress(String message) {

        Log.i(TAG, "showProgress " + message);
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressDialog = DialogUtils.startProgressDialog(getCurrentOpActivity(), message);

            }
        });
    }

    private void updateProgress(String message, int progress){
        Log.i(TAG, "updateProgress " + message);

        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null || !progressDialog.isShowing() || bar == null) {
                    //showProgress(message);
                } else
                    progressDialog.setTitle("Update .... " + message);
            }});
    }

    private void hideAlertDialog() {
        Log.i(TAG, "hideAlertDialog " );
        if(errorDialog!=null //&& errorDialog.isShowing()
         ) {
            errorDialog.dismiss();
        }

        errorDialog = null;
    }

    private void hideProgress(){
        //if(Looper.myLooper()==null)Looper.prepare();
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "hideProgress progressDialog=" + progressDialog);
                if (progressDialog != null //&& progressDialog.isShowing()
                ) {
                    progressDialog.dismiss();
                }
                //bar = null;
                //progressTextView = null;
                progressDialog = null;
                Log.i(TAG, "hideProgress complete progressDialog=" + progressDialog);
            }});
    }

    public void enableNightMode(){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
    }

    public void disableNightMode(){
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }


    private Activity getCurrentOpActivity(){
        return currentActivity.getParent()!=null && !currentActivity.getParent().isFinishing()
                ?currentActivity.getParent()
                :currentActivity;
    }

}

class BaseHandlerFactory{
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void runNowOnUiThread(Runnable action){
        mHandler.postAtFrontOfQueue(action);
    }

    public static void runOnUiThread(Runnable action){
        mHandler.post(action);
    }

    public static void runOnUiThread(Runnable action, int delayMilliSec){
        mHandler.postDelayed(action,delayMilliSec);
    }
}
