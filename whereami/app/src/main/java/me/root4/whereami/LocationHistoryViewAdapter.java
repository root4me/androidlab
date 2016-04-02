package me.root4.whereami;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by harish on 4/2/16.
 */

public class LocationHistoryViewAdapter extends RecyclerView.Adapter<LocationHistoryViewAdapter.LocationViewHolder> {

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView attrName, attrValue;

        public LocationViewHolder(View v) {
            super(v);
            attrName = (TextView) v.findViewById(R.id.loc_lat);
            attrValue = (TextView) v.findViewById(R.id.loc_lng);
        }
    }

    private String[] mDataset;
    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationHistoryViewAdapter(String[] myDataset) {
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
        holder.attrName.setText(mDataset[position]);
        Log.d("VIEWADAPTER", "inside onBindViewHolder ");
    }

    @Override
    public int getItemCount() {
        Log.d("VIEWADAPTER", "inside getItemCount ");
        return mDataset.length;
    }


}
