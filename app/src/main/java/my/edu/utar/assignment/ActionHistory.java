package my.edu.utar.assignment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActionHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    DBHelper DB;
    MyAdapter myAdapter;
    ArrayList<String> history_id, inputText_list,translatedText_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_history);

        DB = new DBHelper(this);
        history_id = new ArrayList<>();
        inputText_list = new ArrayList<>();
        translatedText_list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        myAdapter = new MyAdapter(this,history_id,inputText_list,translatedText_list);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DB.storeDataInArrays(history_id,inputText_list,translatedText_list);
    }
}