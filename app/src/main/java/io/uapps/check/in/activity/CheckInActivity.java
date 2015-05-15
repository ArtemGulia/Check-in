package io.uapps.check.in.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import io.uapps.check.in.R;
import io.uapps.check.in.adapter.FragmentAdapter;
import io.uapps.check.in.fragment.AdminFragment;
import io.uapps.check.in.fragment.CheckInFragment;
import io.uapps.check.in.util.SharedPreferencesHandler;

public class CheckInActivity extends FragmentActivity implements AdminFragment.KeepScreenOnListener, CheckInFragment.EmailListener {

    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private View mDecorView;
    private SharedPreferencesHandler pHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        mDecorView = getWindow().getDecorView();
        mDecorView.setOnSystemUiVisibilityChangeListener
                (new VisibilityChangedListener());

        pHandler = SharedPreferencesHandler.getInstance(PreferenceManager.getDefaultSharedPreferences(this));

        pHandler.loadPrefs();
        if (pHandler.isKeepOn()){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideBars();
    }

    public void changeSleep(boolean flag) {
        pHandler.saveKeepOn(flag);
        if (flag){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.d("test keep on", "tested addFlags" );
        }else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            Log.d("test keep on", "tested clearFlags" );
        }
    }

    @Override
    public void changeEmail(String email) {
        pHandler.saveEmail(email);
    }

    @Override
    public boolean isKeepOn() {
        pHandler.loadPrefs();
        return pHandler.isKeepOn();
    }

    @Override
    public String getEmail() {
        pHandler.loadPrefs();
        return pHandler.getEmail();
    }

    private class VisibilityChangedListener implements View.OnSystemUiVisibilityChangeListener {

        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0
                    || (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0
                    || (visibility & View.SYSTEM_UI_FLAG_LOW_PROFILE) == 0) {
                hideBars();
            }
        }
    }


    /**
     * Hide both status and navigation bar also system bar on tablets
     */
    private void hideBars(){
        mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LOW_PROFILE);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.hide();
    }

    // TODO Not sure about this
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            hideBars();
        }else {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }


}
