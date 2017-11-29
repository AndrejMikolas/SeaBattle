package com.example.andrej.seabattle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.net.ProtocolFamily;
import java.util.ArrayList;

/**
 * Created by Andrej on 29.11.2017.
 */

public class MyCustomAdapter extends ArrayAdapter<DataModelGameHistory> {
    private ArrayList<DataModelGameHistory> dataSet;
    Context mContext;
    Activity main;

    private static class ViewHolder
    {
        TextView textViewPlayers;
        TextView textViewWinner;
        TextView textViewDate;
        TextView textViewTime;
    }


    public MyCustomAdapter(ArrayList<DataModelGameHistory> data, Context context)
    {
        super(context, R.layout.history_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //return super.getView(position, convertView, parent);
        DataModelGameHistory model = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_row_item, parent, false);
            viewHolder.textViewPlayers = (TextView) convertView.findViewById(R.id.textView_players);
            viewHolder.textViewWinner = (TextView) convertView.findViewById(R.id.textView_winner);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textView_gameDate);
            viewHolder.textViewTime = (TextView) convertView.findViewById(R.id.textView_gameTime);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.textViewPlayers.setText(model.getPlayer1() + " vs " + model.getPlayer2());
        viewHolder.textViewWinner.setText("Winner: " + model.getWinner());
        viewHolder.textViewDate.setText(model.getDate());
        viewHolder.textViewTime.setText(model.getTime());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removeRecord(position);
                return true;
            }
        });
        return convertView;
    }

    private void removeRecord(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure you want to delete this record?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DataModelGameHistory model = getItem(position);
                DBHelper db = new DBHelper(getContext());
                db.delete(model.getId());
                remove(model);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
