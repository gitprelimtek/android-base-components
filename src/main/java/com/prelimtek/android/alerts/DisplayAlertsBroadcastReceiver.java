package com.prelimtek.android.alerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.prelimtek.android.customcomponents.R;

public class DisplayAlertsBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = DisplayAlertsBroadcastReceiver.class.getSimpleName();
    private static final String BUNDLE_KEY=TAG+"_Bundle";
    public static final String DISPLAY_ALERT_ACTION = "com.ptek.DisplayAlertsBroadcastReceiver_Action";
    public static final String DISPLAY_ALERT_TYPE_KEY = DisplayAlertsBroadcastReceiver.class.getName()+"_TYPE_KEY";
    public static final String DISPLAY_ALERT_MESSAGE_KEY = DisplayAlertsBroadcastReceiver.class.getName()+"_MESSAGE_KEY";
    public static final String DISPLAY_ALERT_PROGRESS_KEY = DisplayAlertsBroadcastReceiver.class.getName()+"_PROGRESS_KEY";
    public static final String DISPLAY_ALERT_ERROR_MESSAGE_TYPE = "DisplayAlertsBroadcastReceiver_ERROR_MESSAGE_TYPE";
    public static final String DISPLAY_NETWORK_ALERT_ERROR_MESSAGE_TYPE = "DisplayAlertsBroadcastReceiver_NETWORK_ERROR_MESSAGE_TYPE";
    public static final String DISPLAY_ALERT_NORMAL_MESSAGE_TYPE = "DisplayAlertsBroadcastReceiver_NORMAL_MESSAGE_TYPE";
    public static final String DISPLAY_ALERT_SHOW_PROGRESS_TYPE = "DisplayAlertsBroadcastReceiver_SHOW_PROGRESS__TYPE";
    public static final String DISPLAY_ALERT_HIDE_PROGRESS_TYPE = "DisplayAlertsBroadcastReceiver_HIDE_PROGRESS__TYPE";
    public static final String DISPLAY_ALERT_UPDATE_PROGRESS_TYPE = "DisplayAlertsBroadcastReceiver_UPDATE_PROGRESS__TYPE";

    //just added this because Manifest was complaining
    public DisplayAlertsBroadcastReceiver(){}

    AppReceiver appReceiver;
    public DisplayAlertsBroadcastReceiver(
                                          AppReceiver receiver) {
        this.appReceiver = receiver;
    }



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,intent.toString());

        int resultcode = 1;

        if(appReceiver!=null)appReceiver.onReceiveResult(resultcode, intent.getExtras());

        //if(handler!=null)handler.sendMessage(composeMessage(intent));

    }

    public void setAppReceiver(DisplayAlertsBroadcastReceiver.AppReceiver receiver){
        appReceiver = receiver;
    }

    public static void sendMessage(Context context,String message){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_MESSAGE_KEY,message);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_ALERT_NORMAL_MESSAGE_TYPE);
        context.sendBroadcast(intent);
    }

    public static void sendErrorMessage(Context context,String message){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_MESSAGE_KEY,message);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_ALERT_ERROR_MESSAGE_TYPE);
        context.sendBroadcast(intent);
    }

    public static void sendNetworkErrorMessage(Context context,String message){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_MESSAGE_KEY,message);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_NETWORK_ALERT_ERROR_MESSAGE_TYPE);
        context.sendBroadcast(intent);
    }

    public static void startGenericProgress(Context context,String message){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_MESSAGE_KEY,message);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_ALERT_SHOW_PROGRESS_TYPE);
        context.sendBroadcast(intent);
    }

    public static void updateProgress(Context context,String message, int progress){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_MESSAGE_KEY,message);
        intent.putExtra(DISPLAY_ALERT_PROGRESS_KEY,progress);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_ALERT_UPDATE_PROGRESS_TYPE);
        context.sendBroadcast(intent);
    }

    public static void stopProgress(Context context){
        Intent intent = new Intent(DISPLAY_ALERT_ACTION);
        intent.putExtra(DISPLAY_ALERT_TYPE_KEY, DISPLAY_ALERT_HIDE_PROGRESS_TYPE);
        context.sendBroadcast(intent);
    }

    public static void sendNotification(Context context,int notificationId, String title, String message){
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.notification);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context.getApplicationContext());

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public interface AppReceiver{
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
}
