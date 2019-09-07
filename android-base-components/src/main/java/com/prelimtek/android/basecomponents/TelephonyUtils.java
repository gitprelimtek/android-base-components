package com.prelimtek.android.basecomponents;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.prelimtek.android.basecomponents.dialog.DialogUtils;

public class TelephonyUtils {

    public static final String TAG = "TelephonyUtilsTAG";

    public static final int PHONE_CALL_REQ_CODE = 11111;
    public static final int TEXT_MESG_REQ_CODE = 11112;
    public static final int WHATSAPP_MESG_REQ_CODE = 11113;

    @Deprecated
    public static void makePhoneCall1(Activity context, String phoneNumber){
        //if (Build.VERSION.SDK_INT > 22) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_REQ_CODE);
            Log.w(TAG,"Requesting Permission CALL_PHONE .");
            return;
        }


        try{
            //intent call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:+" + phoneNumber));
            context.startActivityForResult(callIntent,PHONE_CALL_REQ_CODE);//a response is sent to onRequestPermissionsResult mthd
        }catch(Exception e){
            Log.e(TAG,"Make phone call failed.",e);
            DialogUtils.startErrorDialog(context,"Make phone call failed.");
        }
        //}
    }

    @Deprecated
    public static void composeSMSMessage1(Activity context, String phoneNumber){

        //if (Build.VERSION.SDK_INT > 22) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.SEND_SMS}, TEXT_MESG_REQ_CODE);
            Log.w(TAG,"Requesting Permission SEND_SMS .");
            return;
        }

        try{
            Intent callIntent = new Intent(Intent.ACTION_VIEW);
            callIntent.setData(Uri.parse("sms:"+phoneNumber));
            context.startActivityForResult(callIntent,TEXT_MESG_REQ_CODE );//a response is sent to onRequestPermissionsResult mthd
        }catch(Exception e){
            Log.e(TAG,"Compose sms failed.",e);
            DialogUtils.startErrorDialog(context,"Compose SMS failed.");
        }
        //}

    }

    @Deprecated
    public static void composeWhatsappMessage1(Activity context, String phoneNumber){
        //intent call
        //if (Build.VERSION.SDK_INT > 22) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.SEND_SMS}, WHATSAPP_MESG_REQ_CODE);
            Log.w(TAG,"Requesting Permission SEND_SMS .");
            return;
        }

        try {
            Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO);
            whatsappIntent.setData(Uri.parse("smsto:" + phoneNumber));
            whatsappIntent.setPackage("com.whatsapp");
            //context.startActivity(Intent.createChooser(whatsappIntent,""));
            context.startActivityForResult(whatsappIntent, WHATSAPP_MESG_REQ_CODE);//a response is sent to onRequestPermissionsResult mthd
        }catch(ActivityNotFoundException e){
            Log.e(TAG,"App not found .",e);
            DialogUtils.startErrorDialog(context,"Please install Whatsapp app and try again.");
        }
        // }
    }


    public static void makePhoneCall(Activity context, String phoneNumber){

        try{
            //intent call
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:+" + phoneNumber));
            context.startActivityForResult(callIntent,PHONE_CALL_REQ_CODE);//a response is sent to onRequestPermissionsResult mthd
        }catch(Exception e){
            Log.e(TAG,"Make phone call failed.",e);
            DialogUtils.startErrorDialog(context,"Make phone call failed.");
        }
        //}
    }


    public static void composeSMSMessage(Activity context, String phoneNumber){

        try{
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
            context.startActivityForResult(smsIntent,TEXT_MESG_REQ_CODE );//a response is sent to onRequestPermissionsResult mthd
        }catch(Exception e){
            Log.e(TAG,"Compose sms failed.",e);
            DialogUtils.startErrorDialog(context,"Compose SMS failed.");
        }
        //}

    }


    public static void composeWhatsappMessage(Activity context, String phoneNumber){

        try {
            Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO);
            whatsappIntent.setData(Uri.parse("smsto:" + phoneNumber));
            whatsappIntent.setPackage("com.whatsapp");
            //context.startActivity(Intent.createChooser(whatsappIntent,""));
            context.startActivityForResult(whatsappIntent, WHATSAPP_MESG_REQ_CODE);//a response is sent to onRequestPermissionsResult mthd
        }catch(ActivityNotFoundException e){
            Log.e(TAG,"App not found .",e);
            DialogUtils.startErrorDialog(context,"Please install Whatsapp app and try again.");
        }
        // }
    }
}