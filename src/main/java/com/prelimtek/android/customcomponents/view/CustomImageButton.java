package com.prelimtek.android.customcomponents.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;

import androidx.annotation.Nullable;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.prelimtek.android.basecomponents.ResourcesUtils;
import com.prelimtek.android.customcomponents.R;

public class CustomImageButton extends LinearLayout {

    public enum Direction {none, text_top, text_left, text_right, text_bottom}
    protected Direction direction;
    protected AttributeSet attrs = null;
    protected RelativeLayout viewlayout;
    protected ImageView imageview;
    protected TextView textview;

    private  boolean showText;
    private int textPosition;
    private String imageSrc;
    private String imageWidth;
    private String imageLength;
    private CharSequence text;

    public CustomImageButton(Context context) {
        super(context);
        initControl(context);
    }

    public CustomImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        initControl(context);
    }

    public CustomImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        initControl(context);
    }

    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Important! set attachRoot to false so that viewLayout layout orientation , width etc can take effect
        //remember to add viewLayout to component after making changes
        viewlayout = (RelativeLayout)inflater.inflate(R.layout.custom_imagebutton_layout, this,false);

        //Init components
         imageview = (ImageView)viewlayout.findViewById(R.id.custom_button_image);

         textview = (TextView)viewlayout.findViewById(R.id.custom_button_textView);

        TypedArray ta = getContext().obtainStyledAttributes(attrs,R.styleable.CustomImageButton,0,0);

        try {
            setTextPosition(ta.getInt(R.styleable.CustomImageButton_textPosition,0));
            setShowText( ta.getBoolean(R.styleable.CustomImageButton_showText, true));
            setText(ta.getText(R.styleable.CustomImageButton_text ));
            setImageSrc(ta.getString(R.styleable.CustomImageButton_imageSrc));
            setImageLength(ta.getString(R.styleable.CustomImageButton_imageLength ));
            setImageWidth(ta.getString(R.styleable.CustomImageButton_imageWidth ));
            setEnabled(ta.getBoolean(R.styleable.CustomImageButton_android_enabled,true));
        } finally {
            ta.recycle();
        }

        //Important! do this because inflated view was not attached to root.
        this.addView(viewlayout);

    }

    public void reDrawUI(boolean activated){
        
        super.setClickable(activated);

        int bcgrnColor= ResourcesUtils.getColor(this,R.color.io_mtini_custom_button_background_color);
        int textColor = ResourcesUtils.getColor(this,R.color.io_mtini_custom_button_text_color);

        if(!activated) {
            bcgrnColor = ResourcesUtils.getColor(this,R.color.io_mtini_deactivate_custom_button_background_color);
            textColor = ResourcesUtils.getColor(this,R.color.io_mtini_deactivate_custom_button_text_color);
        }

        viewlayout.setBackgroundColor(bcgrnColor);
        textview.setTextColor(textColor);

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        //textview.setActivated(showText);
        this.showText = showText;
        invalidate();
        requestLayout();
    }

    public int getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(int textPosition) {

        try {
            direction = Direction.values()[textPosition];
        }catch(Exception e){
            direction = Direction.none;
        }

        this.textPosition = textPosition;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (direction){
            case text_top:
                lp.addRule(RelativeLayout.BELOW, textview.getId());
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                imageview.setLayoutParams(lp);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
                textview.setLayoutParams(lp2);
                break;
            case text_left:
                lp.addRule(RelativeLayout.RIGHT_OF, textview.getId());
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                imageview.setLayoutParams(lp);
                lp2.addRule(RelativeLayout.CENTER_VERTICAL);
                textview.setLayoutParams(lp2);
                break;
            case text_bottom:
                lp.addRule(RelativeLayout.BELOW, imageview.getId());
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                textview.setLayoutParams(lp);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL);
                imageview.setLayoutParams(lp2);
                break;
            case text_right:
                lp.addRule(RelativeLayout.RIGHT_OF, imageview.getId());
                lp.addRule(RelativeLayout.CENTER_VERTICAL);
                textview.setLayoutParams(lp);
                lp2.addRule(RelativeLayout.CENTER_VERTICAL);
                imageview.setLayoutParams(lp2);
                break;
            default:
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                imageview.setLayoutParams(lp);
                textview.setLayoutParams(lp);
                break;
        }


        invalidate();
        requestLayout();

    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        if(imageSrc==null)return;
        this.imageSrc = imageSrc;

        try {

            String name = toName(imageSrc);
            int resId = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
            imageview.setImageResource(resId);

        }catch(Throwable e){
            Uri uri =  Uri.parse(imageSrc);
            if(uri!=null)
                imageview.setImageURI(uri);
            else
                textview.setError(uri+" not loaded");
        }finally{
            invalidate();
            requestLayout();
        }
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        if(imageWidth==null)return;
        int width = dpToInt(imageWidth);
        this.imageWidth = imageWidth;
        ViewGroup.LayoutParams params = imageview.getLayoutParams();
        params.width=width;
        imageview.setLayoutParams(params);
        imageview.requestLayout();

        invalidate();
        requestLayout();
    }

    public String getImageLength() {
        return imageLength;
    }

    public void setImageLength(String imageLength) {
        if(imageLength==null)return;
        this.imageLength = imageLength;

        int length = dpToInt(imageLength);
        ViewGroup.LayoutParams params = imageview.getLayoutParams();
        params.height=length;
        imageview.setLayoutParams(params);
        imageview.requestLayout();
        invalidate();
        requestLayout();
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        if(text!=null && !text.toString().isEmpty() && showText) {
            textview.setText(text);
            this.text = text;
        }else{
            textview.setText(null);
            this.text = null;
        }

        invalidate();
        requestLayout();
    }

    @Override
    public void setActivated(boolean activate){
        super.setActivated(activate);
        reDrawUI(activate);
        invalidate();
        requestLayout();
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        reDrawUI(enabled);
        invalidate();
        requestLayout();
    }

    public void setClickable(boolean clickeable){
        super.setClickable(clickeable);
        //this.setClickable(clickeable);
        this.viewlayout.setClickable(clickeable);
    }


    static String anyresourcefilenameregex = "(?!.*/)(.*?)(?=(\\.[^.]*$))";
    public static final Pattern imageNamePattern = Pattern.compile(anyresourcefilenameregex);
    private String toName(String imagePath){
        Matcher m = imageNamePattern.matcher(imagePath);
        if (m.find()) {
            String s = m.group(0);
            System.out.println(s);
            return s;
        }
        return imagePath;
    }

    public static int DEFAULT_IMAGE_DIM = 50;
    public static final Pattern dimensionsPattern = Pattern.compile("([0-9])+dp");

    private int dpToInt(String value){
        if(value!=null) {
            Matcher m = dimensionsPattern.matcher(value);
            if (m.find()) {
                String s = m.group(0).replaceAll("[^0-9]", "");
                System.out.println(s);
                return Integer.parseInt(s);
            }
        }

        return DEFAULT_IMAGE_DIM;
    }




}
