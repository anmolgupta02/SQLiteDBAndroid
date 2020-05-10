package com.example.sqlitedb;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        final String[] enteredID = new String[1];
        btnGetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredID[0] = etId.getText().toString();

                if (enteredID[0].equals("")) {
                    etId.setError("Please Enter the Id");
                    return;
                }

                Cursor cursor = myDB.getData(enteredID[0]);
                String cursorData = "";
                if (cursor.moveToNext()) {
                    cursorData = "ID: " + cursor.getString(0) + "\n" +
                            "Name: " + cursor.getString(1) + "\n" +
                            "Email: " + cursor.getString(2) + "\n" +
                            "Course Count: " + cursor.getString(3) + "\n";
                }

                if (cursorData.isEmpty()) {
                    showMessage("Get Data Operation", "No data found at ID: " + enteredID[0]);
                } else {
                    showMessage("Get Data Operation", cursorData);
                }
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDB.getAllData();

                StringBuffer buffer = new StringBuffer();

                if(cursor.getCount() == 0){
                    showMessage("Error", "No data in db currently");
                }else{

                    while(cursor.moveToNext()){
                        buffer.append("ID: "+cursor.getString(0)+"\n");
                        buffer.append("Name: "+cursor.getString(1)+"\n");
                        buffer.append("Email: "+cursor.getString(2)+"\n");
                        buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                    }

                    showMessage("Results", buffer.toString());
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDB.updateData(etId.getText().toString(),
                        etName.getText().toString(),
                        etEmail.getText().toString(),
                        etCourseCount.getText().toString());

                if(isUpdated){
                    Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Oops", Toast.LENGTH_SHORT).show();
                }
            }



        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Integer deletedRow = myDB.deleteData(etId.getText().toString());
                  if(deletedRow>0){
                      Toast.makeText(MainActivity.this, "Deleted Sucessfullly", Toast.LENGTH_SHORT).show();
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
