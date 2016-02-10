package com.nasserapps.apitester.Controllers.InProgress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Contact;
import com.nasserapps.apitester.Model.SqlLiteDbHelper;
import com.nasserapps.apitester.R;

public class RulesActivity extends AppCompatActivity {



    SqlLiteDbHelper dbHelper;
    Contact contacts ;
    TextView tv1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SqlLiteDbHelper(this);

        tv1 = (TextView)findViewById(R.id.textViewContact);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                dbHelper.openDataBase();
                contacts = new Contact();
                contacts = dbHelper.Get_ContactDetails();
                tv1.setText("Name: "+contacts.getName()+"\n Mobile No: "+contacts.getMobileNo());
            }
        });






    }

}
