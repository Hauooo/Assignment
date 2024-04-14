package my.edu.utar.assignment;

import my.edu.utar.assignment.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_normal_translation:
                // Handle normal translation action
                Intent normal = new Intent(this, NormalTranslationActivity.class);
                startActivity(normal);
                return true;
            case R.id.action_file_translation:
                // Handle file translation action
                Intent file = new Intent(this, FileTranslationActivity.class);
                startActivity(file);
                return true;
            case R.id.action_history:
                // Handle history action
                Intent history = new Intent(this, ActionHistory.class);
                startActivity(history);
                return true;
            case R.id.action_logout:
                // Handle logout action
                // Clear user session data
                // Navigate back to LoginActivity
                Intent logout = new Intent(this, LoginActivity.class);
                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logout);
                finish();
                return true;
            case R.id.login:
                // Start LoginActivity
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}