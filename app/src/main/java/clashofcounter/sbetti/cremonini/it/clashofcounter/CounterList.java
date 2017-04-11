package clashofcounter.sbetti.cremonini.it.clashofcounter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;

public class CounterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_list);
        ArrayList<String> myCounterTitles = new ArrayList<String>();
        try {
            BufferedReader myReader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("CountersList.txt")));

            String line = myReader.readLine();

            while(line != null){
                myCounterTitles.add(line);
                line = myReader.readLine();
            }

            myReader.close();

        } catch (IOException e) {
            Log.d("IO-ERROR", "Cannot open CountersLists.txt");
        }


        ListView myListView = (ListView) findViewById(R.id.myListView);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myCounterTitles);

        myListView.setAdapter(myAdapter);




        FloatingActionButton addCounter = (FloatingActionButton) findViewById(R.id.addCounterButton);

        addCounter.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CounterList.this);
                builder.setTitle("Add Counter");

                final LayoutInflater inflater = CounterList.this.getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.add_counter_prompt,null));
                builder.setPositiveButton("Add",new DialogInterface.OnClickListener(){


                    public void onClick(DialogInterface dialog, int which) {

                        EditText myEdit = (EditText) findViewById(R.id.editText);

                        //get text, open file CounterList and add line


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
