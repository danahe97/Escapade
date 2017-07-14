package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.adapters.LocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.User;

import java.util.ArrayList;

/**
 * Created by ericar on 7/13/17.
 */

public class ListView extends Fragment{

    public ArrayList <User> people;
    public ArrayList<Location> places;
    ArrayList<Location> locations;
    RecyclerView rvPlanList;
    LocationsAdapter locationAdapter;
    android.app.FragmentManager fm;


    public static ListView newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        ListView fragment = new ListView();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_listsview, container, false);

        locations = new ArrayList<>();

        fm = getActivity().getFragmentManager();
        // find RecyclerView
        rvPlanList = (RecyclerView) v.findViewById(R.id.rvPlanList);
        // construct the adapter from this data source
        locationAdapter = new LocationsAdapter(locations);
        locationAdapter.setFm(fm);
//        locationAdapter.exploreActivity = exploreActivity;
        // RecyclerView setup (layout manager, use adapter)
        rvPlanList.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlanList.setAdapter(locationAdapter);

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        addItems(places);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        MenuItem miProfile = menu.findItem(R.id.miProfile);
        miProfile.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        MenuItem miPlan = menu.findItem(R.id.miPlan);
        miPlan.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void addItems(ArrayList<Location> response) {
        for (int i = 0; i < response.size(); i++) {
            Location location = response.get(i);
            locations.add(location);
            locationAdapter.notifyItemInserted(locations.size() - 1);
        }
    }
}


/**
 * Created by ericar on 7/11/17.
 *
 */
