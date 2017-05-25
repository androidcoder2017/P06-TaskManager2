package com.example.a15056112.p06_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDeleteActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnDelete, btnEdit;
    Task data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        etDesc = (EditText)findViewById(R.id.editTextDesc);
        etName = (EditText)findViewById(R.id.editTextNames);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);

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
            }
        });
    }
}
