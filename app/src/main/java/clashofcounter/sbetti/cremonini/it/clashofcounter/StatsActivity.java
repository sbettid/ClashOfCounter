package clashofcounter.sbetti.cremonini.it.clashofcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        Intent intent = getIntent();

        String text = intent.getStringExtra("stats");

        TextView stats = (TextView) findViewById(R.id.textView6);
        stats.setMovementMethod(new ScrollingMovementMethod());
        stats.setText(text);

    }
}
