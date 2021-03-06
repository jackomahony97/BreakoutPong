package com.cs3305.breakoutpong;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * Entry activity for app to link to initial fragments
 * <p>
 * Includes code from http://www.androidtutorialshub.com/android-login-and-register-with-sqlite-database-tutorial/
 * with modifications as per comments on page regarding code reuse
 *
 * and https://developer.android.com/guide/components/fragments
 * and https://developer.android.com/reference/android/widget/Button
 * with modifications as per developer.android.com code reuse licence
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate to set fragments list
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.viewPager);
        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        //add to list
        pagerAdapter.addFragment(new LoginFragment());
        //add to list
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * Method to call super onPause
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Method to call super onResume
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Inner class for FragmentPagerAdapter
     */
    class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        /**
         * ArrayList : list of fragments
         */
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        /**
         *
         *
         * @param fm
         */
        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        /**
         * Fragment list getter method
         *
         * @param i position in list
         * @return Fragment
         */
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        /**
         * Method to get size of fragment list
         *
         * @return int size of fragment list
         */
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * Method to add fragment to list
         *
         * @param fragment
         */
        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}