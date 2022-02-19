package com.prelimtek.android;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.prelimtek.android.customcomponents.R;

public class ActionBarUtilities {
    private static final String TAG = ActionBarUtilities.class.getName();
    private ActionBarUtilities(){}
    private volatile ProgressBar progressBar;
    private TextView errorText;
    private TextView warnText;
    private TextView infoText;
    private ActionBarUtilities(Activity _context){
        this.context=_context;
        //LayoutInflater inflater = context.getLayoutInflater();
        //View rootView = inflater.inflate(R.layout.action_bar, null, false);

    }

    private void initialize(Activity context){
        this.context = context;
        progressBar = (ProgressBar)context.findViewById(R.id.ptek_actionbar_progress_bar);
        errorText = (TextView)context.findViewById(R.id.ptek_actionbar_error_text);
        warnText = (TextView)context.findViewById(R.id.ptek_actionbar_warning_text);
        infoText = (TextView)context.findViewById(R.id.ptek_actionbar_info_text);
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
                warnText.setVisibility(View.GONE);
                infoText.setVisibility(View.GONE);
            }});
    }
    private Activity context;
    private static ActionBarUtilities instant;
    static public ActionBarUtilities instance(Activity context){

        if(instant==null) {
            instant = new ActionBarUtilities( context);
        }
        //this is necessary for view to be updatable
        instant.initialize(context);
        return instant;
    }

    public void showProgress(String message) {

        progressBar.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"showProgress");

                progressBar.setVisibility(ProgressBar.VISIBLE);
                showInfo(message);
            }
        });

    }

    public void updateProgress(String message, int progress){

        progressBar.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"updateProgress");
                if (ProgressBar.VISIBLE != progressBar.getVisibility()) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress, true);
                showInfo(message);
            }});
    }

    public void hideProgress(){

        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"hideProgress");
                progressBar.setVisibility(ProgressBar.GONE);
            }},300);
    }

    public void showError(String message){

        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"showError : "+message);
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(message);
            }});
    }

    public void showNetworkError(String message){

        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"showNetworkError : "+message);
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(message);
            }});
    }


    public void showInfo(String message){

        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"showInfo : "+message);
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(message);
                hideDelayed(infoText);
            }});
    }

    public void hideDelayed(View view){
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        },3000);
    }

    public void clearMessages(){
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                errorText = (TextView)context.findViewById(R.id.ptek_actionbar_error_text);
                warnText = (TextView)context.findViewById(R.id.ptek_actionbar_warning_text);
                infoText = (TextView)context.findViewById(R.id.ptek_actionbar_info_text);

                errorText.setText("");
                warnText.setText("");
                infoText.setText("");
                errorText.setVisibility(View.GONE);
                warnText.setVisibility(View.GONE);
                infoText.setVisibility(View.GONE);
            }});
    }
}
