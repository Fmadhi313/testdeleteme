package com.example.chapter13;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(MainActivity.this);
        Button add = (Button) findViewById(R.id.buttonAdd);
        Button find = (Button) findViewById(R.id.buttonFind);
        Button delete = (Button) findViewById(R.id.buttonDelete);
        EditText productName = (EditText) findViewById(R.id.productName);
        EditText productQuantity = (EditText) findViewById(R.id.productQuantity);
        EditText productReview = (EditText) findViewById(R.id.productReview);
        TextView productID = (TextView) findViewById(R.id.productID);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();
                String review = productReview.getText().toString();

                if (name.equals("") || quantity.equals("") || review.equals(""))
                    Toast.makeText(MainActivity.this, "make sure all filed are filled", Toast.LENGTH_SHORT).show();
                else {
                    if (!myDb.addData(name, quantity, review)) {
                        Toast.makeText(MainActivity.this, "Insert Failed", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Insert Successful", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = productName.getText().toString();
                if (name.equals("")){
                    Toast.makeText(MainActivity.this, "make sure the filed is filled", Toast.LENGTH_SHORT).show();

                }
                else {
                    Cursor search = myDb.structuredQuery(name);
                    if (search.getCount() == 0){
                        Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        productID.setText(search.getString(0));
                        productName.setText(search.getString(1));
                        productQuantity.setText(search.getString(2));
                        productReview.setText(search.getString(3));

                    }
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delID = productID.getText().toString();
                if (productID.getText().toString().equals("")|| productID.getText().toString().equals("NaN")){
                    Toast.makeText(MainActivity.this, "Find the data you want to delete", Toast.LENGTH_SHORT).show();
                }
                else {
                    myDb.deleteData(delID);
                    Toast.makeText(MainActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}