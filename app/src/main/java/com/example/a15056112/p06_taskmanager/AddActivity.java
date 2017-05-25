package com.example.a15056112.p06_taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDescription, etRemind;
    Button btnAdd, btnCancel;
    int reqCode = 12345;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName =(EditText)findViewById(R.id.editTextName);
        etDescription = (EditText)findViewById(R.id.editTextDescription);
        etRemind = (EditText)findViewById(R.id.editTextRemind);
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        btnCancel = (Button)findViewById(R.id.buttonCancel);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(AddActivity.this);
                Intent intent = new Intent();
                String name = etName.getText().toString();
                String desc = etDescription.getText().toString();
                db.insertTasks(name,desc);
                setResult(RESULT_OK, intent);
                db.close();
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.valueOf(etRemind.getText().toString()));

                Intent i = new Intent(AddActivity.this, TaskScheduledNotificationReceiver.class);
                i.putExtra("noti", etName.getText().toString());
                i.putExtra("notiText", etDescription.getText().toString());

                PendingIntent pIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }
}
