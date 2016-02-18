package com.nasserapps.apitester.Controllers.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.nasserapps.apitester.AI.CustomNotificationStatement.Checklist;
import com.nasserapps.apitester.AI.CustomNotificationStatement.ExpressionParser;
import com.nasserapps.apitester.AI.CustomNotificationStatement.Rule;
import com.nasserapps.apitester.Controllers.Adapters.ChecklistAdapter;
import com.nasserapps.apitester.Model.User;
import com.nasserapps.apitester.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChecklistActivity extends AppCompatActivity {

    ChecklistAdapter listAdapter;
    ExpandableListView expListView;
    List<Checklist> listDataHeader;
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
        listDataHeader = mUser.getChecklists();
        listDataChild = new HashMap<>();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableList);

        listAdapter = new ChecklistAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                // Adding child data
                listDataHeader.add(new Checklist("Winning Stocks"));

                // Adding child data
                ArrayList<Rule> rules = new ArrayList<>();
                rules.add(new ExpressionParser().getRule("PE Ratio", "<", "15.0"));
                rules.add(new ExpressionParser().getRule("PBV Ratio", ">", "10.0"));
                rules.add(new ExpressionParser().getRule("Volume", "=", "500000"));

                listDataHeader.get(0).setRules(rules);

                listDataChild.put(listDataHeader.get(0), listDataHeader.get(0).getRules());
                listAdapter = new ChecklistAdapter(getBaseContext(), listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

                mUser.setChecklists((ArrayList) listDataHeader);

            }
        });
    }
}
