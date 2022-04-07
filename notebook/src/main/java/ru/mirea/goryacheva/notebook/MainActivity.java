package ru.mirea.goryacheva.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    final String FILE_NAME = "file_name";

    private EditText FileName;
    private EditText FileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileName = findViewById(R.id.editTextFileName);
        FileText = findViewById(R.id.editTextText);
        preferences = getPreferences(MODE_PRIVATE);

        FileText.setText(getTextFromFile());
    }


    public void onClick(View view){
        writeTextinFile();
    }

    public void writeTextinFile() {
        String fileName = FileName.getText().toString();
        String fileText = FileText.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, fileName);
        editor.apply();

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(fileText.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getTextFromFile() {

        FileInputStream fin = null;
        try {
            String fileName = preferences.getString(FILE_NAME,  "Empty");
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }
}