package com.nasserapps.apitester.Controllers.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.nasserapps.apitester.Controllers.Adapters.ChecklistAdapter;
import com.nasserapps.apitester.Model.Checklists.Checklist;
import com.nasserapps.apitester.Model.Checklists.Rule;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;

import java.util.HashMap;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity {

    ChecklistAdapter listAdapter;
    ExpandableListView expListView;
    List<Checklist> mChecklists;
    HashMap<Checklist, List<Rule>> listDataChild;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUser=User.getUser(this);
        mChecklists = mUser.getChecklists();
         //get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableList);
        listAdapter = new ChecklistAdapter(this, mChecklists);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
}
