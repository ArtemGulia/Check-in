package io.uapps.check.in.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.uapps.check.in.fragment.AdminFragment;
import io.uapps.check.in.fragment.CheckInFragment;

/**
 * Check-in
 * Created by G_Art on 31/1/2015.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 2;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fr;
        switch (i) {
            case 0:
                fr = new CheckInFragment();
                return  fr;
            case 1:
                fr = new AdminFragment();
                return fr;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
