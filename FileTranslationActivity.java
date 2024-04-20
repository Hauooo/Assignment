package my.edu.utar.assignment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FileTranslationActivity extends AppCompatActivity {

    private TextView uploadedTextPreview;
    private TextView translatedTextPreview;
    private Button uploadButton;
    private Button copyButton;
    private Button downloadButton;
    private Button languageButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_translation);

        uploadedTextPreview = findViewById(R.id.uploaded_text_preview);
        translatedTextPreview = findViewById(R.id.translated_text_preview);
        uploadButton = findViewById(R.id.upload_button);
        copyButton = findViewById(R.id.copy_button);
        downloadButton = findViewById(R.id.download_button);
        languageButton = findViewById(R.id.language_button);
        backButton = findViewById(R.id.back_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle upload action
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle copy action
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle download action
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle language selection action
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back action
            }
        });
    }
}