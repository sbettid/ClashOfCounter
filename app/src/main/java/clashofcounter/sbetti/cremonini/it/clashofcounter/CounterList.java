package clashofcounter.sbetti.cremonini.it.clashofcounter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class CounterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        ArrayList<String> myCounterTitles = new ArrayList<String>();

        try{

            FileInputStream os = openFileInput("CountersList.txt");
            BufferedReader myReader = new BufferedReader(new InputStreamReader(os));

            String line = myReader.readLine();

            while(line != null){
                myCounterTitles.add(line);
                line = myReader.readLine();
            }

            myReader.close();

        }catch(IOException ex){
            Log.d("IOERROR", "Cannot open input file");
        }


        final ListView myListView = (ListView) findViewById(R.id.myListView);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myCounterTitles);

        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = myListView.getItemAtPosition(position);
                TextView tw = (TextView)obj;

                String added =  tw.toString();
                Toast.makeText(getApplicationContext(), added + "", Toast.LENGTH_SHORT).show();
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object obj = myListView.getItemAtPosition(position);

                String st = (String) obj;

                Intent myIntent = new Intent(getApplicationContext(), CustomCounter.class);
                myIntent.putExtra("title", st);
                startActivity(myIntent);


            }
        });


        FloatingActionButton addCounter = (FloatingActionButton) findViewById(R.id.addCounterButton);

        addCounter.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CounterList.this);
                builder.setTitle("Add Counter");

                //final LayoutInflater inflater = CounterList.this.getLayoutInflater();
                //final View myView = inflater.inflate(R.layout.add_counter_prompt, null);

                final EditText myEdit = new EditText(getApplicationContext());



                builder.setView(myEdit);
                builder.setPositiveButton("Add",new DialogInterface.OnClickListener(){


                    public void onClick(DialogInterface dialog, int which) {



                       String newName = myEdit.getText().toString();
                //Toast.makeText(getApplicationContext(), "" + myEdit.getText().toString(), Toast.LENGTH_SHORT).show();
                        PrintWriter myPrint = null;
                        try{

                            FileOutputStream os = openFileOutput("CountersList.txt", Context.MODE_APPEND);
                            myPrint = new PrintWriter(os, true);

                           myPrint.println(newName);

                        }catch (IOException ex){
                            Log.d("IOERROR", "Cannot write to counter list file");
                        }
                Intent restart = getIntent();
                finish();
                startActivity(restart);


                    }
                } );
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){


                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

                //Toast.makeText(getApplicationContext(), "Inside listener", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
