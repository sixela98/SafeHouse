package com.example.safehouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.safehouse.Database;
import com.example.safehouse.OnGetDataListener;
import com.example.safehouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class PropertiesAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<String> properties;
    private final int userId;

    public PropertiesAdapter(Context context, ArrayList<String> properties, int userId) {
        this.mContext = context;
        this.properties = properties;
        this.userId = userId;
    }

    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String property = properties.get(position);

        if(convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_property, null);
        }

        final TextView propertyNameTextView = (TextView)convertView.findViewById(R.id.textview_property_name);
        final ListView roomsListView = (ListView)convertView.findViewById(R.id.listview_rooms);

        propertyNameTextView.setText(property);

        String roomsPath = "/u/u" + userId + "/p/p" + (position+1) + "/r";
        new Database().getSize(roomsPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int roomSize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> roomArrayList = new ArrayList<>();
                for(int i = 1; i <= roomSize; i++) {
                    String roomPath = roomsPath + "/r";
                    new Database().readData(roomPath + i + "/n", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            roomArrayList.add(n);
                            ArrayAdapter roomAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, roomArrayList);
                            roomsListView.setAdapter(roomAdapter);
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(DatabaseError databaseError) {

            }
        });

        return convertView;
    }
}
