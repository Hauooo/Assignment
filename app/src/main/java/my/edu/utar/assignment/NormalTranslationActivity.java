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
    String[] language = {"English","Chinese","Malay","Japanese","French"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_translation);

        inputText = findViewById(R.id.input_text);
        translateButton = findViewById(R.id.translate_button);
        translatedText = findViewById(R.id.translated_text);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        autoCompleteTextView = findViewById(R.id.auto_complete_txt2);
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        DB = new DBHelper(this);
        adapterItems = new ArrayAdapter<String>(this,R.layout.activity_normal_translation);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String language = parent.getItemAtPosition(position).toString();
                Toast.makeText(NormalTranslationActivity.this, "Language: " + language, Toast.LENGTH_SHORT).show();
            }
        });


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle translation action
                TranslatorOptions options = new TranslatorOptions.Builder()
                        .setSourceLanguage("en")
                        .setTargetLanguage("fr")
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
                            Toast.makeText(NormalTranslationActivity.this, "Insert successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(NormalTranslationActivity.this, "Insert failed", Toast.LENGTH_SHORT).show();
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
}
