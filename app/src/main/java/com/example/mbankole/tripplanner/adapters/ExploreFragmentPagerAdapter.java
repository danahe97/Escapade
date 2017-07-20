package com.example.mbankole.tripplanner.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.ExploreActivity;
import com.example.mbankole.tripplanner.fragments.LocationsFragment;
import com.example.mbankole.tripplanner.fragments.MapFragment;
import com.example.mbankole.tripplanner.fragments.PeopleFragment;
import com.example.mbankole.tripplanner.fragments.PlanFragment;

/**
 * Created by mbankole on 7/11/17.
 *
 */

public class ExploreFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"", "", ""};
    private Context context;
    FragmentManager fragmentManager;
    LocationsFragment locationsFragment;
    MapFragment mapFragment;
    PeopleFragment peopleFragment;
    PlanFragment planFragment;
    public ExploreActivity exploreActivity;

    public ExploreFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
    }

    public LocationsFragment getLocationsFragment() {
        if (locationsFragment == null) {
            locationsFragment = LocationsFragment.newInstance();
            locationsFragment.exploreActivity = exploreActivity;
        }
        return locationsFragment;
    }

    public MapFragment getMapFragment() {
        if (mapFragment == null) {
            mapFragment = new MapFragment();
            mapFragment.exploreActivity = exploreActivity;
            mapFragment.setFm(fragmentManager);
        }
        return mapFragment;
    }

    public PeopleFragment getPeopleFragment() {
        if (peopleFragment == null) {
            peopleFragment = PeopleFragment.newInstance();
            peopleFragment.exploreActivity = exploreActivity;
        }
        return peopleFragment;
    }

    public PlanFragment getPlanFragment() {
        if (planFragment == null) {
            planFragment = PlanFragment.newInstance();
            //planFragment.viewPager = this;
        }
        return planFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //return PlaceholderFragment.newInstance();
                return getMapFragment();
            case 1:
                //return PlaceholderFragment.newInstance();
                return getLocationsFragment();
            case 2:
                return getPeopleFragment();
            case 3:
                return getPlanFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}