package me.root4.whereami;


import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by harish on 4/2/16.
 */

public class LocationHistoryViewAdapter extends RecyclerView.Adapter<LocationHistoryViewAdapter.LocationViewHolder> {

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView lat;
        public TextView lng;
        public TextView capturedTime;
        public TextView capturedDate;

        public LocationViewHolder(View v) {
            super(v);
            lat = (TextView) v.findViewById(R.id.loc_lat);
            lng = (TextView) v.findViewById(R.id.loc_lng);
            //capturedTime = (TextView) v.findViewById(R.id.loc_capturedTime);
            capturedTime = (TextView) v.findViewById(R.id.loc_capturedTime);
            capturedDate = (TextView) v.findViewById(R.id.loc_capturedDate);
        }
    }

    private List<Location> mDataset;
    public LocationHistoryViewAdapter(List<Location> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public LocationHistoryViewAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locations_row, parent, false);

        LocationViewHolder vh = new LocationViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {

        holder.lat.setText((String.valueOf(((Location) mDataset.get(position)).getLat())));
        holder.lng.setText((String.valueOf(((Location) mDataset.get(position)).getLng())));
        holder.capturedTime.setText((String.valueOf(Utils.getTime(((Location) mDataset.get(position)).getCaptureDate()))));
        holder.capturedDate.setText((String.valueOf(Utils.getDate(((Location) mDataset.get(position)).getCaptureDate()))));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
