package com.prelimtek.android.customcomponents.view;

import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prelimtek.android.basecomponents.TelephonyUtils;
import com.prelimtek.android.basecomponents.dialog.DialogUtils;
import com.prelimtek.android.customcomponents.R;


public class TelephoneActionDialogFragment extends DialogFragment {


    public static final String TAG = "PhoneActnDialogFrgmtTAG";
    public static final String ARG_TELEPHONE_NUMBER = "TelephoneNumberToCallOrMessage";


    public TelephoneActionDialogFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String telephoneNumberArg ;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        System.out.println("!!!!!!!!!!!!     onCreateView called ");
        super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null) {
            telephoneNumberArg = savedInstanceState.getString(ARG_TELEPHONE_NUMBER);
        }else if(getArguments()!=null) {
            telephoneNumberArg = getArguments().getString(ARG_TELEPHONE_NUMBER);
        }

        final String phoneNumber = telephoneNumberArg;

        Context context = getContext();

        View view = inflater.inflate(R.layout.phonenumber_action_dialog_layout, null);


        View whatsappMessageButton = view.findViewById(R.id.whatsapp_icon_btn);
        whatsappMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyUtils.composeWhatsappMessage(getActivity(),phoneNumber);
                //intent call
                /*if (Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, WHATSAPP_MESG_REQ_CODE);
                        Log.w(TAG,"Requesting Permission SEND_SMS .");
                        return;
                    }

                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setData(Uri.parse("sms:"+phoneNumber));
                    whatsappIntent.setPackage("com.whatsapp");
                    startActivity(Intent.createChooser(whatsappIntent,""));
                    //startActivityForResult(whatsappIntent,WHATSAPP_MESG_REQ_CODE );//a response is sent to onRequestPermissionsResult mthd

                }*/
            }
        });

        View textMessageButton = view.findViewById(R.id.text_msg_btn);
        textMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyUtils.composeSMSMessage(getActivity(),phoneNumber);
                //intent call
                /*if (Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, TEXT_MESG_REQ_CODE);
                        Log.w(TAG,"Requesting Permission SEND_SMS .");
                        return;
                    }

                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("sms:"+phoneNumber));
                startActivityForResult(callIntent,TEXT_MESG_REQ_CODE );//a response is sent to onRequestPermissionsResult mthd

                }*/
            }
        });

        View phoneCallButton = view.findViewById(R.id.make_phonecall_btn);
        phoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                TelephonyUtils.makePhoneCall(getActivity(),phoneNumber);
                /*if (Build.VERSION.SDK_INT > 22) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_REQ_CODE);
                        Log.w(TAG,"Requesting Permission CALL_PHONE .");
                        return;
                    }

                    //intent call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + phoneNumber));
                    startActivityForResult(callIntent,PHONE_CALL_REQ_CODE);//a response is sent to onRequestPermissionsResult mthd
                }*/
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case TelephonyUtils.PHONE_CALL_REQ_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Snackbar.make(this.getView(),"Permission CALL_PHONE granted. Please press call button again.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.i(TAG,"Permission CALL_PHONE granted. ");
                }else{
                    DialogUtils.startErrorDialog(this.getContext(),"Permission CALL_PHONE denied. contact administrator. ");
                    Log.w(TAG,"Permission CALL_PHONE denied. ");

                }
                break;
            case TelephonyUtils.TEXT_MESG_REQ_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Snackbar.make(this.getView(),"Permission SEND_SMS granted. Please press message button again.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.i(TAG,"Permission SEND_SMS granted. ");
                }else{
                    DialogUtils.startErrorDialog(this.getContext(),"Permission SEND_SMS denied. contact administrator. ");
                    Log.w(TAG,"Permission SEND_SMS denied. ");

                }
                break;
            case TelephonyUtils.WHATSAPP_MESG_REQ_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Snackbar.make(this.getView(),"Permission SEND_SMS granted. Please press message button again.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.i(TAG,"Permission SEND_SMS granted. ");
                }else{
                    DialogUtils.startErrorDialog(this.getContext(),"Permission SEND_SMS denied. contact administrator. "+grantResults[0]);
                    Log.w(TAG,"Permission SEND_SMS denied. "+grantResults[0]);

                }
                break;

        }

    }




}
