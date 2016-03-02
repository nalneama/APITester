package com.nasserapps.apitester.Controllers.InProgress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nasserapps.apitester.Model.Database.DataSource;
import com.nasserapps.apitester.Model.Stock;
import com.nasserapps.apitester.R;

public class RulesActivity extends AppCompatActivity {



    TextView tv1;

    DataSource mDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDataSource = new DataSource(this);
        tv1 = (TextView)findViewById(R.id.textViewContact);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Stock stock = mDataSource.getStocks().get("MERS.QA");

                tv1.setText("Name: "+ stock.getAPICode());

            }
        });

    }

}
