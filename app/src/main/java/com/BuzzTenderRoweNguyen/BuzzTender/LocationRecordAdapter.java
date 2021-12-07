package com.BuzzTenderRoweNguyen.BuzzTender;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocationRecordAdapter extends FirestoreRecyclerAdapter<LocationRecord, LocationRecordAdapter.MyViewHolder> {

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private static final DateFormat formatter = new SimpleDateFormat("MM-dd-yy HH:mm:ss", Locale.US);
    private final OnDataChangedListener listener;

    LocationRecordAdapter(FirestoreRecyclerOptions<LocationRecord> options, OnDataChangedListener listener) {
        super(options);
        this.listener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView id;
        TextView timestamp;
        TextView latitude;
        TextView longitude;
        TextView accuracy;

        MyViewHolder(LinearLayout v) {
            super(v);
            layout = v;
            id = v.findViewById(R.id.item_id);
            timestamp = v.findViewById(R.id.timestamp);
            latitude = v.findViewById(R.id.item_lat);
            longitude = v.findViewById(R.id.item_lon);
            accuracy = v.findViewById(R.id.item_acc);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LocationRecordAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position, LocationRecord loc) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.timestamp.setText(formatter.format(loc.getTimestamp()));
        holder.latitude.setText(String.format(Locale.US, "%.7f", loc.getLocation().getLatitude()));
        holder.longitude.setText(String.format(Locale.US, "%.7f", loc.getLocation().getLongitude()));
        holder.accuracy.setText(String.format(Locale.US, "%.2f", loc.getAcc()));
    }

    @Override
    public void onDataChanged() {
        // Called each time there is a new query snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        listener.onDataChanged();
    }

}
