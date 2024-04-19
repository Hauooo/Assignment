package my.edu.utar.assignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NormalTranslationActivity extends AppCompatActivity {

    private EditText inputText;
    private Button translateButton;
    private TextView translatedText;
    private DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_translation);

        inputText = findViewById(R.id.input_text);
        translateButton = findViewById(R.id.translate_button);
        translatedText = findViewById(R.id.translated_text);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        DB = new DBHelper(this);


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean insertHistory = DB.insertHistory(inputText.getText().toString(),translatedText.getText().toString(),username);
                if(insertHistory == true){
                    Toast.makeText(NormalTranslationActivity.this, "Insert successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NormalTranslationActivity.this, "Insert failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}