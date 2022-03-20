package com.prelimtek.android.basecomponents;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import com.google.common.base.Strings;
import com.prelimtek.android.customcomponents.BuildConfig;
import com.prelimtek.android.customcomponents.R;

public class Configuration {

    /** 24 hours */
    public static int expiration_24_hours = 24;

    /**
     * Will be used to as a user configuredPreferences key to track session user data.
     * */
    public static String SERVER_SIDE_PREFERENCES_TAG = "EAT_SERVER_SIDE_PREFERENCES";

    public static String USER_SCREEN_PREFERENCES_TAG = "EAT_USER_SCREEN_PREFERENCES";

    public static String preferences_jwt_key = "JWT_PREF_KEY";

    /**
     * This is used in conjuction with user configuredPreferences remote server
     * */

    public static final String apiKey_Name = "x-mtini-apikey";

    public static final String authServiceKey = "authentication_service";

    public static final String walletAddressKey = "walletAddress";

    public static final String customerIdKey = "id";

    public static final String DEFAULT_AUTH_SERVICE = "None";


    //public static final String DISK_CACHE_SUBDIR = "appDiskCache";
    //public static final int  DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB

    //IMAGE CONFIGURATIONS

    public static int captureImgMaxHeight = 300;

    public static int captureImgMaxWidth  = 300;

    public static int bitmapMaxHeight = 400;

    public static int bitmapMaxWidth  = 400;

    public static int imageDialogMaxHeight = 1000;

    public static int imageDialogMaxWidth = 1000;

    public static boolean tlsEnabled=false;

    public static boolean networkRequired=false;

    public static boolean uiDarkMode=false;

    public enum SUPPORTED_AUTH_SERVICE{
        none,facebook,mtini,firebase
    }

    private Configuration(){
    }

    private Context context;

    private Configuration(Context context){
        this.context = context;
        versionCode = getVersionCode(context);
        versionName = getVersionName(context);
        PreferenceManager.setDefaultValues(context.getApplicationContext(),R.xml.preferences,false);
    }

    public SharedPreferences.Editor edit() {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Configuration.SERVER_SIDE_PREFERENCES_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        return editor;
    }

    public static SharedPreferences preferences(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Configuration.SERVER_SIDE_PREFERENCES_TAG, Context.MODE_PRIVATE);
        return pref;
    }

    public static Configuration configuredPreferences(Context context){
        Configuration conf = new Configuration(context);
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Configuration.SERVER_SIDE_PREFERENCES_TAG, Context.MODE_PRIVATE);

        //server-side gets
        conf.apikey  = pref.getString(Configuration.preferences_jwt_key, null);
        conf.customerId = pref.getString(customerIdKey, null);
        //conf.walletJson = pref.getString(walletAddressKey,null);

        //presets
        SharedPreferences defaultPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        conf.authService = defaultPrefs.getString(authServiceKey,DEFAULT_AUTH_SERVICE);
        conf.currencyCode = defaultPrefs.getString("base_currency","USD");
        conf.currencyCode = Strings.isNullOrEmpty(conf.currencyCode)?Currency.getInstance(Locale.getDefault()).getCurrencyCode():conf.currencyCode;

        conf.dateFormatStr = defaultPrefs.getString("date_format","yyyy/MM/dd");
        conf.dateFormat = new SimpleDateFormat(conf.dateFormatStr);

        conf.tlsEnabled= defaultPrefs.getBoolean("remoteServerTLSEnabled",false);
        conf.remoteHostUrl = defaultPrefs.getString("remoteServer", null);
        conf.remoteMqttUrl = defaultPrefs.getString("queueBroker",null);

        conf.networkRequired = defaultPrefs.getBoolean("networkRequired",Boolean.FALSE);
        conf.uiDarkMode = defaultPrefs.getBoolean("uiDarkMode",Boolean.FALSE);

        return conf;
    }

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {}
        return 0;
    }


    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException ex) {}
        return "";
    }


    public static String getVersionText(Context context) {

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return  ""+pi.versionName+":"+pi.versionCode;
        } catch (PackageManager.NameNotFoundException ex) {}
        return "";
    }
    @Deprecated //Use BuildConfig
    public String remoteHostUrl;
    @Deprecated //Use BuildConfig
    public String remoteMqttUrl;

    public String customerId;
    public String apikey;
    public String userEmail;
    public String phoneNumber;
    public String currencyCode;
    public String dateFormatStr;
    public SimpleDateFormat dateFormat;
    public String authService;
    public String versionName;
    public int versionCode;

}
