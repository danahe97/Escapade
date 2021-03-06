package com.example.mbankole.tripplanner.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mbankole.tripplanner.fragments.ExplorePlansListFragment;
import com.example.mbankole.tripplanner.fragments.ExploreUsersListFragment;
import com.example.mbankole.tripplanner.models.Plan;

import java.util.ArrayList;

/**
 * Created by mbankole on 7/20/17.
 */

public class ExploreFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"", ""};
    FragmentManager fragmentManager;
    ExplorePlansListFragment explorePlansListFragment;
    ExploreUsersListFragment exploreUsersListFragment;
    ArrayList<Plan> plans;

    public ExploreFragmentPagerAdapter(FragmentManager fm, ArrayList<Plan> plans) {
        super(fm);
        fragmentManager = fm;
        this.plans = plans;
    }

    public ExplorePlansListFragment getExplorePlansListFragment() {
        if (explorePlansListFragment == null) {
            explorePlansListFragment = ExplorePlansListFragment.newInstance();
            explorePlansListFragment.plans = plans;
        }
        return explorePlansListFragment;
    }

    public ExploreUsersListFragment getExploreUsersListFragment() {
        if (exploreUsersListFragment == null) {
            exploreUsersListFragment = ExploreUsersListFragment.newInstance();

        }
        return exploreUsersListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               // return new PlaceholderFragment();
                return getExplorePlansListFragment();
            case 1:
                return getExploreUsersListFragment();
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

    public void refreshAdd() {
        getExploreUsersListFragment().refresh();
        getExplorePlansListFragment().refreshAdd();
    }

    public void refresh() {
        getExploreUsersListFragment().refresh();
        getExplorePlansListFragment().refresh();
    }
}