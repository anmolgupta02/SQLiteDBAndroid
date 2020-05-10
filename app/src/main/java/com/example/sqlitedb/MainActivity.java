package com.example.sqlitedb;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etEmail, etCourseCount;
    Databasehelper myDB;
    Button btnAdd, btnGetdata, btnUpdate, btnDelete, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new Databasehelper(this);

        btnAdd = findViewById(R.id.button_add);
        btnDelete = findViewById(R.id.button_delete);
        btnGetdata = findViewById(R.id.button_view);
        btnViewAll = findViewById(R.id.button_viewAll);
        btnUpdate = findViewById(R.id.button_update);

        etId = findViewById(R.id.editText_id);
        etName = findViewById(R.id.editText_name);
        etEmail = findViewById(R.id.editText_email);
        etCourseCount = findViewById(R.id.editText_CC);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertInto(etName.getText().toString(),
                        etEmail.getText().toString(),
                        etCourseCount.getText().toString());

                if (isInserted) {
                    showMessage("Add Operation", "Data Inserted Successfully");
                } else {
                    showMessage("Add Operation", "Something Went Wrong");
                }
            }
        });

    }

    public void showMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();

    }
}
