package my.edu.utar.assignment;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ActionHistory extends AppCompatActivity {

    private ListView historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_history);

        historyList = findViewById(R.id.history_list);

        // TODO: Load the action history into the ListView
    }
}