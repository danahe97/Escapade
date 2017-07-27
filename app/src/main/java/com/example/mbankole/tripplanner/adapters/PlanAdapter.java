package com.example.mbankole.tripplanner.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mbankole.tripplanner.R;
import com.example.mbankole.tripplanner.activities.PlanEditActivity;
import com.example.mbankole.tripplanner.models.Location;
import com.example.mbankole.tripplanner.models.Plan;
import com.example.mbankole.tripplanner.models.User;
import com.example.mbankole.tripplanner.utility.gradient;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.Blur;

/**
 * Created by danahe97 on 7/20/17.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    List<Plan> mPlans;
    Context context;
    android.app.FragmentManager fm;

    public static final int EDIT_PLAN_REQUEST_CODE = 30;

    public PlanAdapter(List<Plan> plans) {
        mPlans = plans;
    }

    public void setFm(android.app.FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_plan, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlanAdapter.ViewHolder holder, int position) {
        // get the data according to position
        Plan plan = mPlans.get(position);
        // populate the views according to this data
        holder.tvPlanTitle.setText(plan.title);
        holder.clearLocations();
        for (int i = 0; i < plan.places.size(); i++) {
            holder.addLocation(plan.places.get(i));
        }
        holder.ivBackground.setColorFilter(Color.argb(0, 0, 0, 0));
        Picasso.with(context)
                .load(plan.places.get(0).photoUrl)
                .transform(new gradient())
                .transform(new Blur(context))
                .into(holder.ivBackground);
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvPlanTitle;
        public LinearLayout llLocations;
        public ImageButton ibAdd;
        public ImageView ivBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPlanTitle = (TextView) itemView.findViewById(R.id.tvPlanTitle);
            llLocations = (LinearLayout) itemView.findViewById(R.id.llLocations);
            ibAdd = (ImageButton) itemView.findViewById(R.id.ibAdd);
            ivBackground = (ImageView) itemView.findViewById(R.id.ivPlanBackground);
            itemView.setOnClickListener(this);
            ibAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Integer position = getAdapterPosition();
            final Plan plan = mPlans.get(position);
            if (v.getId() == R.id.ibAdd) {
                User.generateChandler(context).plans.add(plan);
                Snackbar.make(v, "Added!", Snackbar.LENGTH_SHORT).show();
                ibAdd.setVisibility(View.GONE);
            } else {
                if (position != RecyclerView.NO_POSITION) {
                    Intent i = new Intent(context, PlanEditActivity.class);
                    i.putExtra("plan", plan);
                    i.putExtra("position", position);
                    ((Activity)context).startActivityForResult(i, EDIT_PLAN_REQUEST_CODE);
                }
            }
        }

        void addLocation(Location location) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_location, llLocations, false);
            final TextView tvLocationName = (TextView)v.findViewById(R.id.tvLocationname);
            final ImageView ivLocationImage = (ImageView)v.findViewById(R.id.ivLocationImage);
            if (location.photoUrl != null) {
                Picasso.with(context)
                        .load(location.photoUrl)
                        //.memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                        .into(ivLocationImage);
            }
            tvLocationName.setText(location.name);
            llLocations.addView(v);
        }

        void clearLocations() {
            if((llLocations).getChildCount() > 0)
                (llLocations).removeAllViews();
        }
    }
}