package my.edu.utar.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NormalTranslationActivity extends AppCompatActivity {

    private EditText inputText;
    private Button translateButton;
    private TextView translatedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_translation);

        inputText = findViewById(R.id.input_text);
        translateButton = findViewById(R.id.translate_button);
        translatedText = findViewById(R.id.translated_text);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle translation action
            }
        });
    }
}