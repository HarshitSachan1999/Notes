package com.harshit.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class Main2Activity extends AppCompatActivity {

    EditText textView;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView = findViewById(R.id.editText);
        textView.setSingleLine(false);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);
        if(noteId != -1) {

            textView.setText(MainActivity.notes.get(noteId));
        }

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notes.set(noteId, s.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                MainActivity.set = new HashSet(MainActivity.notes);
                MainActivity.sharedPreferences.edit().putStringSet("notes", MainActivity.set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
