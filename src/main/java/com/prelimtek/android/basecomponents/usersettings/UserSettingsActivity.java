package com.prelimtek.android.basecomponents.usersettings;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.annotation.Nullable;

public class UserSettingsActivity extends PreferenceActivity  {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        System.out.println(" UserSettingsActivity called : ");

        super.onCreate(savedInstanceState);

        this.getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }



}
