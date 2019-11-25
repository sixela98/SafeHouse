package com.example.safehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safehouse.Objects.Room;
import com.example.safehouse.R;

import java.util.ArrayList;

public class RoomAdapter extends ArrayAdapter<Room> implements View.OnClickListener{
    private ArrayList<Room> roomArrayList;
    Context context;

    private static class ViewHolder {
        TextView txtName;
        TextView txtTemperature;
        TextView txtHumidity;
        TextView txtGas;
    }

    public RoomAdapter(ArrayList<Room> rooms, Context context) {
        super(context, R.layout.content_air_quality_button, rooms);
        this.roomArrayList = rooms;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        Object object = getItem(position);
        Room room = (Room)object;

        //Implement onClick()
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room room = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.content_air_quality_button, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.air_quality_sensor_name);
            viewHolder.txtTemperature = (TextView) convertView.findViewById(R.id.air_quality_sensor_temperature);
            viewHolder.txtHumidity = (TextView) convertView.findViewById(R.id.air_quality_sensor_humidity);
            viewHolder.txtGas = (TextView) convertView.findViewById(R.id.air_quality_sensor_gas);
            result = convertView;
            convertView.setBackground(context.getResources().getDrawable(R.drawable.buttonshape));
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Animation animation = AnimationUtils.loadAnimation(context, (position>lastPosition) ? R.anim.)
        //result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(room.getName());
        viewHolder.txtTemperature.setText(room.getTemperature());
        viewHolder.txtHumidity.setText(room.getHumidity());
        viewHolder.txtGas.setText(room.getGas());
        return convertView;
    }

}
