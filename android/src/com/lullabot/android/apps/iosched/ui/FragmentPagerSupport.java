package com.lullabot.android.apps.iosched.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.lullabot.android.apps.iosched.R;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;

public class FragmentPagerSupport extends FragmentActivity {
	static ImageView floorPlan;

    MyAdapter mAdapter;

    ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
		TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
		indicator.setTextColor(R.color.all_track_color);
		indicator.setFooterColor(R.color.block_column_3);
		indicator.setSelectedColor(R.color.all_track_color);

		indicator.setViewPager(mPager);
		//We set this on the indicator, NOT the pager
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

    }

    public static class MyAdapter extends FragmentPagerAdapter implements TitleProvider{
    	protected static final String[] TITLES = new String[] { "Floor 2", "Floor 3", "Floor 6", };
    	private int mCount = TITLES.length;


    	public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
        
        
    	@Override
    	public String getTitle(int position) {
    		return MyAdapter.TITLES[position % TITLES.length];
    	}

    	@Override
        public void destroyItem(View collection, int position, Object view) {}
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;
        private static Drawable sBackground;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            floorPlan = (ImageView) v.findViewById(R.id.floor_plan2);

            if (mNum == 0) {
        		sBackground = getResources().getDrawable(R.drawable.floorplan_floor2);
        		floorPlan.setImageDrawable(sBackground);
        	}
        	if (mNum == 1) {
        		sBackground = getResources().getDrawable(R.drawable.floorplan_floor3);
        		floorPlan.setImageDrawable(sBackground);
        	}
        	if (mNum == 2) {
        		sBackground = getResources().getDrawable(R.drawable.floorplan_floor6);
        		floorPlan.setImageDrawable(sBackground);
        	}

        	return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }
    }

}
