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

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView display;
    EditText input;
    Button shell,bubble;
    int[] arrayList;

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
                int n = arrayList.length;
                for (int i = 0; i < n - 1; i++){
                    int index = i;
                    for (int j = i + 1; j < n; j++)
                        if (arrayList[j] < arrayList[index])
                            index = j;
                    int smallerNumber = arrayList[index];
                    arrayList[index] = arrayList[i];
                    arrayList[i] = smallerNumber;
                }

                setDisplay();
            }
        });

        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                int n = arrayList.length;
                int temp = 0;
                for(int i=0; i < n; i++){
                    for(int j=1; j < (n-i); j++){
                        if(arrayList[j-1] > arrayList[j]){
                            //swap elements
                            temp = arrayList[j-1];
                            arrayList[j-1] = arrayList[j];
                            arrayList[j] = temp;
                        }
                    }
                }

                setDisplay();
            }
        });






    }
}
