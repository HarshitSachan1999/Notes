package com.harshit.notes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    public static ListView listView;
    public static ArrayList<String> notes = new ArrayList<>();
    public static SharedPreferences sharedPreferences;
    private static Context context;
    public static ArrayAdapter arrayAdapter;
    public static HashSet set = new HashSet<>();

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

     @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        notes.add("");
         Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
         intent.putExtra("noteId", notes.size()-1);
         startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        sharedPreferences = getSharedPreferences("com.harshit.notes", Context.MODE_PRIVATE);
        listView = findViewById(R.id.listView);

        if(sharedPreferences.getStringSet("notes",null)==null) {

            notes.add("Example Note");
        }else{

            set = (HashSet) sharedPreferences.getStringSet("notes", null);
            notes = new ArrayList(set);
        }
        arrayAdapter = new ArrayAdapter(getAppContext(), android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable
                                .ic_dialog_alert).setTitle("WARNING")
                                .setMessage("Do you Want to Delete this note")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        notes.remove(position);
                                        set = new HashSet(notes);
                                        sharedPreferences.edit().putStringSet("notes", set).apply();
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("No", null).show();
                return true;
            }
        });
    }
}
