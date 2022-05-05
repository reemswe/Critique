package com.example.critique;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewStores extends AppCompatActivity {

    private DBHelper database;
    //ArrayList<String> StoresList;
    //ArrayAdapter adapter;
    //ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stores);


        database = DBHelper.getInstance(ViewStores.this);
        showStores();


        //list = findViewById(R.id.list);
        //view();
    }

    public void showStores(){
        Cursor cd = database.getAllStores();
        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
        TextView noStores = findViewById(R.id.noStores);

        //DB does not hvae any sotres
        if(cd.getCount()==0){
            noStores.setText("There are not any stores");
            noStores.setTextColor(Color.rgb(241,98,80));
        }

        else{
            //columons header
            {
                noStores.setVisibility(View.GONE);
                TableRow row = new TableRow(ViewStores.this);
                TextView c1 = new TextView(ViewStores.this);
                TextView c2 = new TextView(ViewStores.this);

                c1.setText("Store");
                c1.setBackgroundColor(Color.rgb(90, 0, 238));
                c1.setTextColor(Color.WHITE);
                c1.setTextSize(17);
                c1.setWidth(400);
                c2.setText("Review Store");
                c2.setBackgroundColor(Color.rgb(90, 0, 238));
                c2.setTextColor(Color.WHITE);
                c2.setTextSize(17);
                c2.setWidth(400);
                c2.setPadding(50,0,0,0);

                row.addView(c1);
                row.addView(c2);
                table.addView(row);
            }

            //content
            while(cd.moveToNext()){

                StringBuffer buffer1 = new StringBuffer();
                buffer1.append(cd.getString(2));
                TableRow row = new TableRow(ViewStores.this);
                TextView c1 = new TextView(ViewStores.this);
                c1.setText(cd.getString(2));//
                c1.setBackgroundColor(Color.WHITE);
                c1.setTextColor(Color.BLACK);
                c1.setTextSize(17);
                c1.setWidth(150);

                Button reviewButton = new Button((ViewStores.this));
                reviewButton.setText("review");
                reviewButton.setTextSize(14);
                reviewButton.setOnClickListener(reviewButtonOnClick);
                reviewButton.setId(cd.getInt(0));
                reviewButton.setWidth(150);



                row.addView(c1);
                row.addView(reviewButton);
                table.addView(row);

            }

        }
        cd.close();
    }


    View.OnClickListener reviewButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(ViewStores.this,reviewStore.class);
            i.putExtra("StoreID",view.getId());
            startActivity(i);
        }
    };


    /*
    private void view(){
        StoresList = database.readStores();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, StoresList);
        list.setAdapter(adapter);
    }
     */
}
