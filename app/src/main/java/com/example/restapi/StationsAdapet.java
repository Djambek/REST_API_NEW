package com.example.restapi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StationsAdapet extends BaseAdapter {
    Context context;
    ArrayList<ArrayList<String>> stations;

    StationsAdapet(Context context, ArrayList<ArrayList<String>> stations){
        this.stations = stations;
        this.context = context;
    }
    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int i) {
        return stations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View my_view = LayoutInflater.from(context).inflate(R.layout.station_adapter, null);
        TextView station = my_view.findViewById(R.id.textView);
        station.setText(stations.get(i).get(0));
        Log.d("RETROFIT", String.valueOf(stations));
        TextView dis = my_view.findViewById(R.id.textView2);
        dis.setText(stations.get(i).get(1));
        return my_view;
    }
}
