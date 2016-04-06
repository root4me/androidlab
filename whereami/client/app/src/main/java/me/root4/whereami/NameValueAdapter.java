package me.root4.whereami;

/**
 * Created by harish on 4/1/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NameValueAdapter extends RecyclerView.Adapter<NameValueAdapter.MyViewHolder> {

    private List<NameValue> nameValueList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView attrName, attrValue;

        public MyViewHolder(View view) {
            super(view);
            attrName = (TextView) view.findViewById(R.id.attrName);
            attrValue = (TextView) view.findViewById(R.id.attrValue);
        }
    }


    public NameValueAdapter(List<NameValue> nameValueList) {
        this.nameValueList = nameValueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.namevalue_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NameValue nameValue = nameValueList.get(position);
        holder.attrName.setText(nameValue.getAttrName());
        holder.attrValue.setText(nameValue.getAttrValue());
    }

    @Override
    public int getItemCount() {
        return nameValueList.size();
    }
}

