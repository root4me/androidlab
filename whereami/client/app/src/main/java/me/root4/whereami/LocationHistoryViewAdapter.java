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
        public TextView createdTime;
        public TextView createdDate;

        public LocationViewHolder(View v) {
            super(v);
            lat = (TextView) v.findViewById(R.id.loc_lat);
            lng = (TextView) v.findViewById(R.id.loc_lng);
            //capturedTime = (TextView) v.findViewById(R.id.loc_capturedTime);
            createdTime = (TextView) v.findViewById(R.id.loc_createdTime);
            createdDate = (TextView) v.findViewById(R.id.loc_createdDate);
        }
    }

    private List<Location> mDataset;
    public LocationHistoryViewAdapter(List<Location> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public LocationHistoryViewAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        Log.d("VIEWADAPTER", "inside onCreateViewHolder ");
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
        //holder.capturedTime.setText((String.valueOf (((Location) mDataset.get(position)).getCaptureDate())));
        holder.createdTime.setText((String.valueOf(Utils.getTime(((Location) mDataset.get(position)).getCreateDate()))));
        holder.createdDate.setText((String.valueOf(Utils.getDate(((Location) mDataset.get(position)).getCreateDate()))));
    }

    @Override
    public int getItemCount() {
        Log.d("VIEWADAPTER", "inside getItemCount ");
        return mDataset.size();
    }


}
