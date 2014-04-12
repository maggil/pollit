package com.pollit.android.app;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Locale;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.support.v7.app.ActionBar.Tab;
import android.R.id;
import android.graphics.drawable.BitmapDrawable;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;



public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    private ParseUser theUser = null;
    private Collection<String> userFriends = null;
	static ImageView rand_image;
    boolean picture_taken = false;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PARSE INIT
		Parse.initialize(this, "WmfBdhTHHEqrwHr50D6uixGoyktvu8woe8fv4EdU", "6FmLA0Ys0j4ibHCAQxasHX4IouPHhg68B8MCDJtj");
		
		//Camera Init
        
        //Set ActionBar attributes.
        ActionBar ab = getSupportActionBar();
        //ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // lets you swipe across screens
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        
        //generate fragments.

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
        	switch(position){
        		case 2:
        			return CameraFragment.newInstance(3);
        		default:
        			return PlaceholderFragment.newInstance(position + 1);
        	}
            

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
            }
            return null;
        }
    }

   
    //}

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:

            }
           // LinearLayout lin = (LinearLayout) rootView.findViewById(R.id.stuff);
            return rootView;
        }
    }
    
    public static class CameraFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CameraFragment newInstance(int sectionNumber) {
            CameraFragment fragment = new CameraFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public CameraFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            
            rootView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
            });
            
           // LinearLayout lin = (LinearLayout) rootView.findViewById(R.id.stuff);
            return rootView;
        }
    }

    /*class MyTabsListener implements ActionBar.TabListener {
        public Fragment fragment;
        public Context context;
        ViewPager vp = (ViewPager) findViewById(R.id.pager);

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            TextView textView = (TextView) findViewById(R.id.section_label);

            //List of friends
            if(tab.getContentDescription() == "Friends")
            {
                textView.setText("Hello Friends!");

                //ft.replace(R.id.fragment_container, fragment.getTargetFragment());
            }
            //Inbox
            else if (tab.getContentDescription() == "FriendsBox")
            {
                textView.setText("Hello Meeper!");
                //ft.replace(R.id.fragment_container, fragment.getTargetFragment());
            }
            //Public Inbox
            else if (tab.getContentDescription() == "Public")
            {
                textView.setText("Creeper!");
                //ft.replace(R.id.fragment_container, fragment.getTargetFragment());
            }
            //Add friends tab.
            else
            {
                textView.setText("Kinda cool");
                //ft.replace(R.id.fragment_container, fragment.getTargetFragment());
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {

        }

    }*/

}
