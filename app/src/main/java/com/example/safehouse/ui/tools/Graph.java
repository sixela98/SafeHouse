package com.example.safehouse.ui.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.safehouse.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Graph extends AppCompatActivity {
    private GraphView graph;
    ArrayList<Double> randomArray= new ArrayList<Double>();
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (GraphView) findViewById(R.id.graph);

        //Every 15min store a new value
        //timer.scheduleAtFixedRate(new TimerTask()

        for (int i = 0; i < 200; i++) {
            double random;
            random = new Random().nextInt(2) + 20;
            randomArray.add(random);
        }

        //create DataPoints with array
        DataPoint[] data = new DataPoint[randomArray.size()];
        for (int i = 0; i < randomArray.size(); i++) {
            data[i] = new DataPoint(i, randomArray.get(i));
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data); // This one should be obvious right? :)
        graph.addSeries(series);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time in minutes");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature");


    }
}
