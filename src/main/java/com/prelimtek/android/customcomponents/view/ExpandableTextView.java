package com.prelimtek.android.customcomponents.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.prelimtek.android.basecomponents.Configuration;


/**
 * This class helps helps configure a textview with expandable/popup/scrollable features upon click.
 * **/
public class ExpandableTextView extends AppCompatTextView {


    public ExpandableTextView(Context context) {
        super(context);
        initControl(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    private void initControl(Context context) {

        AppCompatTextView textView = this;
         Float init_elevation=1F;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            init_elevation=textView.getElevation();
        }
        final float elevation = init_elevation;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVerticalScrollBarEnabled(true);
                //textView.setHorizontalScrollBarEnabled(true);
                textView.setScrollContainer(true);
                textView.setMinLines(3);
                textView.setMaxLines(10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView.setElevation(elevation+10);
                }
            }
        });

        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                textView.setVerticalScrollBarEnabled(false);
                //textView.setHorizontalScrollBarEnabled(false);
                textView.setScrollContainer(false);
                textView.setLines(1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textView.setElevation(elevation);
                }
                return true;
            }
        });

    }


}
