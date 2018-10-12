package com.example.luism.tareacorta2;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;


public class MainActivity extends AppCompatActivity {

    TextView display;
    EditText input;
    Button shell,bubble;
    int[] arrayList;
    GraphView graph;

    private void setDisplay(){
        int n = arrayList.length;
        display.setText("");
        for(int i=0; i < n; i++){
            if(i==0){
                display.append("[");
            }
            display.append(String.valueOf(arrayList[i]));
            if(i==(n-1)){
                display.append("]");
            }else{
                display.append(",");
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView)findViewById(R.id.display);
        input = (EditText)findViewById(R.id.input);
        shell = (Button)findViewById(R.id.shell);
        bubble = (Button)findViewById(R.id.bubble);
        graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("Eje x: Tamaño de array | Eje y: Duracion (ms)");

        input.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {

                    String arrayString = input.getText().toString();
                    display.setText("["+arrayString+"]");

                    String[] splitedString = arrayString.split(",");
                    arrayList = new int[splitedString.length];

                    for (int i = 0; i < splitedString.length; i++) {
                        arrayList[i] = Integer.parseInt(splitedString[i]);
                    }

                    return true;
                }
                return false;
            }
        });

        shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                graph.removeAllSeries();
                int n = arrayList.length;
                long startTime = System.nanoTime();
                for (int i = 0; i < n - 1; i++){
                    int index = i;
                    for (int j = i + 1; j < n; j++)
                        if (arrayList[j] < arrayList[index])
                            index = j;
                    int smallerNumber = arrayList[index];
                    arrayList[index] = arrayList[i];
                    arrayList[i] = smallerNumber;
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                createGraph(arrayList.length, duration);
                setDisplay();
            }
        });

        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                graph.removeAllSeries();
                int n = arrayList.length;
                int temp = 0;
                long startTime = System.nanoTime();
                for(int i=0; i < n; i++){
                    for(int j=1; j < (n-i); j++) {
                        if (arrayList[j - 1] > arrayList[j]) {
                            //swap elements
                            temp = arrayList[j - 1];
                            arrayList[j - 1] = arrayList[j];
                            arrayList[j] = temp;
                        }
                    }
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                createGraph(arrayList.length, duration);
                setDisplay();
            }
        });
    }

    protected void createGraph(int arraySize, long duration){
        int intDuration = (int) duration/1000;
        graph.getViewport().setXAxisBoundsManual(true);
        int min = arraySize < 5 ? 0 : arraySize - 5;
        graph.getViewport().setMinX(min);
        graph.getViewport().setMaxX(arraySize + 5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(intDuration - 10);
        graph.getViewport().setMaxY(intDuration + 10);
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>();
        series.appendData(new DataPoint(arraySize,intDuration), true, 500);
        graph.addSeries(series);
        series.setShape(PointsGraphSeries.Shape.POINT);
    }
}
