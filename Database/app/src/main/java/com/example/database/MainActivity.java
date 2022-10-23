package com.example.database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editId;
    Button btnAddData;
    Button btnViewData;
    Button btnUpdateData;
    Button btnDeleteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText_surname);
        editMarks = (EditText)findViewById(R.id.editText_marks);
        editId = (EditText)findViewById(R.id.editText_id);
        btnAddData = (Button)findViewById(R.id.button_add);
        btnViewData = (Button)findViewById(R.id.button_view);
        btnUpdateData = (Button)findViewById((R.id.button_update));
        btnDeleteData = (Button)findViewById((R.id.button_delete));
        AddData();
        ViewData();
        UpdateData();
        DeleteData();
    }
    public void AddData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());
                if(isInserted == true)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }



        });
    }
    public void ViewData(){
        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    //show meesage
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Surname :"+ res.getString(2)+"\n");
                    buffer.append("Marks :"+ res.getString(3)+"\n\n");
                }
                //show all the data
                showMessage("Data",buffer.toString());



            }
        });
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void UpdateData(){
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdate = myDb.updateData(editId.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());

                if(isUpdate == true)
                    Toast.makeText(MainActivity.this,"Data update",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not update",Toast.LENGTH_LONG).show();
            }


        });
    }
    public void DeleteData(){
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer deletedRows = myDb.deleteData(editId.getText().toString());


                if(deletedRows > 0)
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_LONG).show();
            }
        });
    }
}