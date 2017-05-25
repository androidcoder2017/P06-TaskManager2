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

public class EditDeleteActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnDelete, btnEdit, btnCancel;
    Task data;
    int reqCode = 123456;
    int reqCodes = 1234567;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        etDesc = (EditText)findViewById(R.id.editTextDesc);
        etName = (EditText)findViewById(R.id.editTextNames);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        final Intent i = getIntent();
        data = (Task) i.getSerializableExtra("data");

        etName.setText(data.getName());
        etDesc.setText(data.getDescription());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditDeleteActivity.this);
                data.setName(etName.getText().toString());
                data.setDescription(etDesc.getText().toString());
                dbh.updateTasks(data);
                dbh.close();
                setResult(RESULT_OK, i);
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent i = new Intent(EditDeleteActivity.this, TaskEditNotificationReceiver.class);
                i.putExtra("noti", etName.getText().toString());
                i.putExtra("notiText", etDesc.getText().toString());

                PendingIntent pIntent = PendingIntent.getBroadcast(EditDeleteActivity.this, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditDeleteActivity.this);
                dbh.deleteTasks(data.getId());
                dbh.close();
                setResult(RESULT_OK, i);
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);

                Intent i = new Intent(EditDeleteActivity.this, TaskDeleteNotificationReceiver.class);
                i.putExtra("noti", etName.getText().toString());
                i.putExtra("notiText", etDesc.getText().toString());

                PendingIntent pIntent = PendingIntent.getBroadcast(EditDeleteActivity.this, reqCodes, i, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pIntent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });
    }
}
