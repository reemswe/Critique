package com.example.critique;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStores extends AppCompatActivity {

    private DBHelper database;
    ArrayList<String> StoresList;
    ArrayAdapter adapter;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stores);
        database = DBHelper.getInstance(ViewStores.this);
        list = findViewById(R.id.list);
        view();
    }

    private void view(){
        StoresList = database.readStores();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, StoresList);
        list.setAdapter(adapter);
    }
}
