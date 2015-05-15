package io.uapps.check.in.util;

import android.content.SharedPreferences;

/**
 * Check-in
 * Created by G_Art on 1/2/2015.
 */
public class SharedPreferencesHandler {
    private static SharedPreferencesHandler preferencesHandler;

    public static final String EMAIL = "email";
    public static final String KEEP_ON = "keepOn";

    private String dEmail = "";
    private boolean dKeepOn = true;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor prefsEditor;

    private String email;
    private boolean keepOn;

    public static SharedPreferencesHandler getInstance(SharedPreferences mPrefs) {
        if (preferencesHandler == null) {
            preferencesHandler = new SharedPreferencesHandler(mPrefs);
        } else {
            preferencesHandler.setSharedPreferences(mPrefs);
        }
        return preferencesHandler;
    }

    public boolean savePrefs(String email, boolean isKeepOn){
        prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean(KEEP_ON, isKeepOn);
        prefsEditor.putString(EMAIL, email);

        boolean result;
        try {
            prefsEditor.apply();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public void loadPrefs(){
        if (!mPrefs.contains(KEEP_ON) || !mPrefs.contains(EMAIL)) {
            saveDefaultSettings();
        }

        email = mPrefs.getString(EMAIL, dEmail);
        keepOn = mPrefs.getBoolean(KEEP_ON, dKeepOn);
    }

    private void saveDefaultSettings() {
        savePrefs(dEmail, dKeepOn);
    }

    public boolean saveKeepOn (boolean isKeepOn) {
        prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean(KEEP_ON, isKeepOn);

        boolean result;
        try {
            prefsEditor.apply();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public boolean saveEmail (String email){
        prefsEditor = mPrefs.edit();
        prefsEditor.putString(EMAIL, email);

        boolean result;
        try {
            prefsEditor.apply();
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isKeepOn() {
        return keepOn;
    }

    public void setKeepOn(boolean keepOn) {
        this.keepOn = keepOn;
    }

    private SharedPreferencesHandler(SharedPreferences mPrefs) {
        this.mPrefs = mPrefs;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.mPrefs = sharedPreferences;
    }
}
