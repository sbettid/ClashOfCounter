package clashofcounter.sbetti.cremonini.it.clashofcounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCounter extends AppCompatActivity {

    public int counter = 0;
    public TextView brombeis;
    public String title;
    public String fileName;
    public long start = 0, stop = 0, sec = 0, min = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_counter);


        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        fileName = title.trim();
        TextView tit = (TextView) findViewById(R.id.textView2);
        tit.setText(title);


        brombeis = (TextView) findViewById(R.id.textView5);

        Button up = (Button) findViewById(R.id.button);
        up.setOnClickListener(new View.OnClickListener() {

                                  public void onClick(View v) {
                                    counter ++;
                                    if (start != 0) {
                                        stop = System.nanoTime();
                                        sec = (stop - start)/1000000000;
                                        if (sec >= 60) {
                                            min = sec / 60;
                                            sec = sec - (min*60);
                                        }
                                        String label = "without \"" + title + "\" :(";
                                        Toast toast = Toast.makeText(getApplicationContext(), min + " min and " + sec + " sec " + label, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 175);
                                        toast.show();
                                        sec = 0;
                                        min = 0;
                                    }
                                    start = System.nanoTime();
                                    brombeis.setText("" + counter);
                                  }
                              }
        );

        ImageButton clear = (ImageButton) findViewById(R.id.imageButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                brombeis.setText(counter + "");
            }
        });



        Button saveStats = (Button) findViewById(R.id.button2);
        saveStats.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                PrintWriter myPrint = null;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                try{
                    FileOutputStream os = openFileOutput(fileName, Context.MODE_APPEND);
                    myPrint = new PrintWriter(os, true);

                    myPrint.println("On date " + dateFormat.format(date)+ " the counter said: " + counter);

                    myPrint.close();

                    if (counter >= 20) {
                        Toast toast = Toast.makeText(getApplicationContext(), "He was onfire today!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 175);
                        toast.show();
                    }

                }catch (IOException ex){
                    Log.d("IOERROR", "Cannot write to output file");
                }

            }
        });



        Button seeStats = (Button) findViewById(R.id.button3);
        seeStats.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String text = "";

                try{

                    FileInputStream os = openFileInput(fileName);
                    BufferedReader myReader = new BufferedReader(new InputStreamReader(os));

                    String line = myReader.readLine();

                    while(line != null){
                       text += line + "\n";
                        line = myReader.readLine();
                    }

                    myReader.close();

                }catch(IOException ex){
                    Log.d("IOERROR", "Cannot open input file");
                }

                Intent myIntent = new Intent(getApplicationContext(), StatsActivity.class);
                myIntent.putExtra("stats", text);
                startActivity(myIntent);

            }
        });

    }
}