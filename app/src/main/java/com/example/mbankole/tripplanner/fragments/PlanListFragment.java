package com.example.mbankole.tripplanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.adapters.PlanLocationsAdapter;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.TransportOption;

import java.util.Collections;

/**
 * Created by mbankole on 7/13/17.
 */

public class PlanListFragment extends Fragment{

    public Plan plan;
    TextView tvTitle;
    TextView tvDate;
    TextView tvCreator;
    RecyclerView rvPlanList;
    public PlanLocationsAdapter listAdapter;
    android.app.FragmentManager fm;
    public PlanEditActivity planEditActivity;

    public static PlanListFragment newInstance() {
        Bundle args = new Bundle();
        //args.putParcelable("user", user);
        PlanListFragment fragment = new PlanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View v = inflater.inflate(R.layout.fragment_listsview, container, false);

        fm = getActivity().getFragmentManager();
        // find RecyclerView
        tvTitle = (TextView) v.findViewById(R.id.tvPlanName);
        tvDate = (TextView) v.findViewById(R.id.tvPlanDate);
        tvCreator = (TextView) v.findViewById(R.id.tvPlanCreator);
        rvPlanList = (RecyclerView) v.findViewById(R.id.rvPlanList);

        tvTitle.setText(plan.title);
        tvCreator.setText("Created by " + plan.creatorUserName);

        // construct the adapter from this data source
        listAdapter = new PlanLocationsAdapter(plan.places);
        listAdapter.planEditActivity = planEditActivity;
        listAdapter.setFm(fm);
//        locationAdapter.exploreActivity = exploreActivity;
        // RecyclerView setup (layout manager, use adapter)
        rvPlanList.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPlanList.setAdapter(listAdapter);

        // Extend the Callback class
        ItemTouchHelper.Callback _ithCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP );
            }

            //and in your implementaion of
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // get the viewHolder's and target's positions in your adapter data, swap them
                Collections.swap(plan.places, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // and notify the adapter that its dataset has changed
                listAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                planEditActivity.refresh();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };
        ItemTouchHelper ith = new ItemTouchHelper(_ithCallback);
        ith.attachToRecyclerView(rvPlanList);

        listAdapter.notifyItemInserted(plan.places.size() - 1);

        refresh();

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {}

    public void refresh() {
        if (plan.places != null && plan.places.size() > 1) {
            for (int i = 0; i < plan.places.size() - 1; i++) {
                Location loc1 = plan.places.get(i);
                Location loc2 = plan.places.get(i + 1);
                if (loc1.transport == null || loc1.transport.endId != loc2.googleId) {
                    loc1.transport = new TransportOption(loc1, loc2);
                    loc1.transport.mode = TransportOption.Mode.BLANK;
                }
            }
            plan.places.get(plan.places.size() - 1).transport = null;
        }
        listAdapter.notifyDataSetChanged();
    }

    public void addItem() {
        listAdapter.notifyItemInserted(plan.places.size() - 1);
        refresh();
    }
}