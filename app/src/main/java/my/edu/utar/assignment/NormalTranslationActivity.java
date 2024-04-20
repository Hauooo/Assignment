package my.edu.utar.assignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

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
        Spinner inputLanguageSpinner = findViewById(R.id.input_language_spinner);
        Spinner translatedLanguageSpinner = findViewById(R.id.translated_language_spinner);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        DB = new DBHelper(this);


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle translation action
                String langPrefFrom  = GetLangCode(inputLanguageSpinner.getSelectedItem().toString());
                String langPrefTo  = GetLangCode(translatedLanguageSpinner.getSelectedItem().toString());

                //error
                if(langPrefFrom == "err" || langPrefTo == "err"){
                    Toast.makeText(NormalTranslationActivity.this, "Invalid language", Toast.LENGTH_SHORT).show();
                    return;
                }

                TranslatorOptions options = new TranslatorOptions.Builder()
                        .setSourceLanguage(langPrefFrom)
                        .setTargetLanguage(langPrefTo)
                        .build();
                Translator translator = Translation.getClient(options);
                ProgressDialog progLog = new ProgressDialog(NormalTranslationActivity.this);
                progLog.setMessage("Downloading new translation model....");
                progLog.setCancelable(false);
                progLog.show();

                //download translation language model
                translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progLog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        translatedText.setText(e.getMessage().toString());
                        progLog.dismiss();
                    }
                });

                String sourceText = inputText.getText().toString();

                //translation task
                Task<String> result = translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        translatedText.setText(s);

                        //insert translation records into database
                        Boolean insertHistory = DB.insertHistory(inputText.getText().toString(),translatedText.getText().toString(),username);
                        if(insertHistory == true){
                            Toast.makeText(NormalTranslationActivity.this, "Translation saved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(NormalTranslationActivity.this, "Database Insertion failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        translatedText.setText(e.getMessage().toString());
                    }
                });
            }
        });
    }

    String GetLangCode(String lang){
        switch(lang){
            case "English":
                return "en";
            case "Chinese":
                return "zh";
            case "Malay":
                return "ms";
            case "Japanese":
                return "ja";
            case "French":
                return "fr";
            default:
                return "err";
        }
    }
}
