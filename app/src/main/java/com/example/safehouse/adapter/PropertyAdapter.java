package com.example.safehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.safehouse.Objects.Property;
import com.example.safehouse.R;

import java.util.ArrayList;
 /*
public class PropertyAdapter extends ArrayAdapter<Property> implements View.OnClickListener{
  private ArrayList<Property> propertyArrayList;
    Context context;

    private static class ViewHolder {
        TextView txtName;
        TextView txtTemperature;
        TextView txtHumidity;
        TextView txtGas;
    }

    public PropertyAdapter(ArrayList<Property> properties, Context context) {
        super(context, R.layout.content_air_quality_button, properties);  //Change layout
        this.propertyArrayList = properties;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        Object object = getItem(position);
        Property property= (Property) object;

        //Implement onClick()
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Property property = getItem(position);
        PropertyAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //Change sets
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

        //Change sets
        viewHolder.txtName.setText(property.getName());
        viewHolder.txtTemperature.setText(property.getTemperature());
        viewHolder.txtHumidity.setText(property.getHumidity());
        viewHolder.txtGas.setText(property.getGas());
        return convertView;


}}*/
