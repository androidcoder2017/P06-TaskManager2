package com.example.a15056112.p06_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int requestCodes = 1;
    ListView lv;
    Button btnAdd;
    ArrayList<String> alTask;
    ArrayAdapter<String> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.lv);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        alTask = new ArrayList<String>();

        DBHelper db = new DBHelper(MainActivity.this);
        db.getAllTasks();

        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alTask);
        alTask.clear();
        for (int i = 0; i < db.getAllTasks().size(); i++){
            alTask.add(db.getAllTasks().get(i).getName() + "\n" + db.getAllTasks().get(i).getDescription());
        }

        lv.setAdapter(aa);
        aa.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);

                startActivityForResult(intent, requestCodes);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && requestCode == this.requestCodes) {
            aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alTask);
            DBHelper db = new DBHelper(MainActivity.this);
            alTask.clear();
            for (int i = 0; i < db.getAllTasks().size(); i++){
                alTask.add(db.getAllTasks().get(i).getId() + " " + db.getAllTasks().get(i).getName() + "\n" + db.getAllTasks().get(i).getDescription());
        }

            lv.setAdapter(aa);
            aa.notifyDataSetChanged();
        }

    }
}
