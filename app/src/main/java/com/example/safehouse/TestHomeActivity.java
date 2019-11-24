package com.example.safehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import com.example.safehouse.adapter.PropertiesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class TestHomeActivity extends AppCompatActivity {
    private GridView gridView;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_home_activity);
        setupUI();
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        String propertiesPath = "/u/u" + 1 + "/p";
        new Database().getSize(propertiesPath, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                int propertySize = (int)dataSnapshot.getChildrenCount();
                ArrayList<String> propertyArrayList = new ArrayList<>();
                for(int i = 1; i <= propertySize; i++) {
                    String propertyPath = propertiesPath + "/p";
                    new Database().readData(propertyPath + i + "/n", new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String n = dataSnapshot.getValue(String.class);
                            propertyArrayList.add(n);
                            Context context = getApplicationContext();
                            PropertiesAdapter propertiesAdapter = new PropertiesAdapter(context, propertyArrayList, 1);
                            gridView.setAdapter(propertiesAdapter);
                            System.out.println("The size of property: " + propertyArrayList.size());
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TestHomeActivity.this, RoomActivity.class);
                intent.putExtra("propertyId", position+1);
                System.out.println("Position pressed: " + position);
                startActivity(intent);
            }
        });
    }

    //Setup the UI with the desired element
    protected void setupUI() {
        gridView = (GridView)findViewById(R.id.propertyGridview);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() < event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe left - startActivity()",
                        Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(
                        TestHomeActivity.this, MainActivity.class);
                startActivity(intent);
            }

            return true;
        }
    }
}
