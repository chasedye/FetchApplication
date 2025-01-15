package com.fetchApplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // tag is for logging purposes
    private final String TAG = this.getClass().toString();
    private List<DataObject> sortedObjects;

    public MyAdapter()
    {
        sortedObjects = new ArrayList<>();
    }

    public void addItems(List<DataObject> retrievedObj)
    {
        sortedObjects.addAll(retrievedObj);
        // need to notify adapter the list was updated
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fetch_app_obj, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // set textview for object
        DataObject dataObject = sortedObjects.get(position);
        holder.nameTextView.setText(dataObject.getName());
        holder.listIdTextView.setText(String.valueOf(dataObject.getListID()));
        holder.idTextView.setText(String.valueOf(dataObject.getId()));
    }


    @Override
    public int getItemCount() {
        return sortedObjects.size();
    }

    // ViewHolder class to hold views for each item
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView listIdTextView;
        TextView idTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.NameText);
            listIdTextView = itemView.findViewById(R.id.ListIDText);
            idTextView = itemView.findViewById(R.id.IDText);
        }


    }
}
