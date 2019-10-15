package com.spp.sqlitedemodatabaseapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    Button btnView,btnAdd;
    TextInputEditText edtName,edtLang,edtNumber,edtEmail;
    // ArrayList<Visitor> visitorsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=(Button)findViewById(R.id.buttonAdd);
        btnView=(Button)findViewById(R.id.buttonView);
        edtName=(TextInputEditText)findViewById(R.id.edtTextName);
        edtLang=(TextInputEditText)findViewById(R.id.edtTextLanguage);
        edtNumber=(TextInputEditText)findViewById(R.id.edtMobileNo);
        edtEmail=(TextInputEditText)findViewById(R.id.edtEmail);


       db=new DatabaseHandler(this);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Insert user inputs in database
                Visitor visitor=new Visitor();
                visitor.setName(edtName.getText().toString());
                visitor.setLanguage(edtLang.getText().toString());
                visitor.setEmail(edtEmail.getText().toString());
                visitor.setNumber(edtNumber.getText().toString());
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                visitor.setDate(date);



                Long result=db.insertVisitor(visitor);
                 Log.i("result :",String.valueOf(result));

                // clear text once data is inserted in database
                edtEmail.setText("" );
                edtLang.setText("");
                edtName.setText("");
                edtNumber.setText("");
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),VisitorListActivity.class);
                startActivity(intent);
//                visitorsList=db.getAllVisitors();
//               Toast.makeText(getApplicationContext(),visitorsList.get(0).name,Toast.LENGTH_SHORT).show();
//              // Log.i("Visitors :",visitorsList.get(0).name);
            }
        });






    }


}
