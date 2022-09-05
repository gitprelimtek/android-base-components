package com.prelimtek.android;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                if(progressBar!=null)
                    progressBar.setVisibility(View.GONE);
                if(errorText!=null)
                    errorText.setVisibility(View.GONE);
                if(warnText!=null)
                    warnText.setVisibility(View.GONE);
                if(infoText!=null)
                    infoText.setVisibility(View.GONE);
            }
        });
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
        if(progressBar==null)return;
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"showProgress");
                if(progressBar==null)return;
                progressBar.setVisibility(ProgressBar.VISIBLE);
                showInfo(message);
            }
        });
    }

    public void updateProgress(String message, int progress){
        if(progressBar==null)return;
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"updateProgress");
                if(progressBar==null)return;
                if (ProgressBar.VISIBLE != progressBar.getVisibility()) {
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                showInfo(message);
            }
        });
    }

    public void hideProgress(){
        if(progressBar==null)return;
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"hideProgress");
                if(progressBar==null)return;
                progressBar.setVisibility(ProgressBar.GONE);
            }},300);
    }

    public void showError(String message){
        Log.d(TAG,"showError : "+message);
        if(errorText==null)return;
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(errorText==null)return;
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(message);
            }
        });
    }

    public void showNetworkError(String message){
        Log.d(TAG,"showNetworkError : "+message);
        if(errorText==null)return;
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(errorText==null)return;
                errorText.setVisibility(View.VISIBLE);
                errorText.setText(message);
            }
        });
    }


    public void showInfo(String message){
        Log.d(TAG,"showInfo : "+message);
        if(infoText==null)return;
        BaseHandlerFactory.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(infoText==null)return;
                infoText.setVisibility(View.VISIBLE);
                infoText.setText(message);
                hideDelayed(infoText);
            }
        });
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

                if(errorText!=null){
                    errorText.setText("");
                    errorText.setVisibility(View.GONE);
                }

                if(warnText!=null){
                    warnText.setText("");
                    warnText.setVisibility(View.GONE);
                }

                if(infoText!=null){
                    infoText.setText("");
                    infoText.setVisibility(View.GONE);
                }

            }
        });
    }
}
