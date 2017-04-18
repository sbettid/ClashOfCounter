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

            while(line != null ) {
               // Log.d("NEW_FILE", "Before:" + line);
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

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final Object obj = myListView.getItemAtPosition(position);
                //Log.d("POS", "" + position);
               final int pos = position;


                AlertDialog.Builder builder = new AlertDialog.Builder(CounterList.this);
                builder.setTitle("Delete counter?");
                builder.setMessage("Are you sure?");
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener(){


                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int which) {

                                String st = (String) obj;

                                String newFile = "";


                                if(pos == 0 && myListView.getCount() == 1){
                                    File fil = new File(getFilesDir().getAbsolutePath(),"CountersList.txt" );
                                    fil.delete();
                                    //Log.d("IMP", "" +fil.exists() );
                                }
                                else {

                                    try {

                                        FileInputStream os = openFileInput("CountersList.txt");
                                        BufferedReader myReader = new BufferedReader(new InputStreamReader(os));

                                        String line = myReader.readLine();

                                        // Log.d("NEW_FILE", "St: " +st);

                                        while (line != null) {
                                            if (!line.equals(st)) {
                                                Log.d("NEW_FILE", "line is different");
                                                if (!newFile.equals(""))
                                                    newFile += "\n" + line;
                                                else
                                                    newFile += line;
                                            }

                                            line = myReader.readLine();
                                        }

                                        //Log.d("NEW_FILE", newFile);
                                        myReader.close();

                                    } catch (IOException ex) {
                                        Log.d("IOERROR", "Cannot open input file");
                                    }

                                    PrintWriter myPrint = null;
                                    try {

                                        FileOutputStream os = openFileOutput("CountersList.txt", Context.MODE_PRIVATE);
                                        myPrint = new PrintWriter(os);
                                        Log.d("NEW_FILE", "New:" + newFile);
                                        myPrint.println(newFile);

                                        myPrint.close();

                                    } catch (IOException ex) {
                                        Log.d("IOERROR", "Cannot write to counter list file");
                                    }

                                    //Delete file stats
                                    String fileName = st.trim();
                                    String dir = getFilesDir().getAbsolutePath();
                                    File fi = new File(dir, fileName);

                                    if (fi.exists()) {
                                        fi.delete();
                                        Log.d("DEL", "deleted");

                                    }
                                }
                                    Intent restart = getIntent();
                                    finish();
                                    startActivity(restart);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                            }
                        });
                    builder.show();
                //Toast.makeText(getApplicationContext(), "this is a toast, don't eat it", Toast.LENGTH_SHORT).show();
                return true;
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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


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
