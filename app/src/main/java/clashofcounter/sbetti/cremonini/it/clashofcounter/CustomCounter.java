package clashofcounter.sbetti.cremonini.it.clashofcounter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
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
                                    brombeis.setText("" + counter);
                                  }
                              }
        );



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