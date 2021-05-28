package com.example.animalpedia;

import androidx.appcompat.app.AppCompatDelegate;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class Settings extends PreferenceActivity {

    ListPreference listPreference;
    Preference infoPreference;
    Preference mailPreference;

    //public static final String MyPREFERENCES = "nightModePrefs";
    //public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        addPreferencesFromResource(R.xml.preference_screen);
        setTheme(R.style.SettingsTheme);
        //ActionBar actionBar = getActionBar();
        //actionBar.setTitle("Settings");
        //actionBar.setDisplayHomeAsUpEnabled(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        listPreference = (ListPreference) findPreference("listPref_theme");

        String theme = sharedPreferences.getString("listPref_theme", "false");
        if ("default".equals(theme)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            listPreference.setSummary(listPreference.getEntry());
        } else if ("light".equals(theme)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            listPreference.setSummary(listPreference.getEntry());
        } else if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            listPreference.setSummary(listPreference.getEntry());
        }

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String items = (String) newValue;
                if (preference.getKey().equals("listPref_theme")){
                    switch (items){
                        case "default":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            startActivity(getIntent());
                            overridePendingTransition(0,0);
                            break;
                        case "light":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            break;
                        case "dark":
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            break;
                    }
                    ListPreference lP = (ListPreference) preference;
                    lP.setSummary(lP.getEntries()[lP.findIndexOfValue(items)]);
                }
                return true;
            }
        });

        infoPreference = findPreference("dialog_pref");
        infoPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openInfo("Info", getString(R.string.info_message));
                return true;
            }
        });

        mailPreference = findPreference("mail_pref");
        mailPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("mailto:animalpedia2021@gmail.com");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                return false;
            }
        });

        //sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //checkNightModeActivated();



        /*
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB_DEFAULT:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        }
                        //finish();
                        break;
                    case R.id.RB_LIGHT:
                        finish();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        saveNightModeState(false);
                        recreate();
                        //finish();
                        break;
                    case R.id.RB_DARK:
                        finish();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        saveNightModeState(true);
                        recreate();
                        break;
                }
            }
        });

         */
    }


    private void openInfo(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(Settings.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    /*
    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE,nightMode);

        editor.apply();
    }

    private   void checkNightModeActivated(){
        if(sharedPreferences.getBoolean(KEY_ISNIGHTMODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

     */

}