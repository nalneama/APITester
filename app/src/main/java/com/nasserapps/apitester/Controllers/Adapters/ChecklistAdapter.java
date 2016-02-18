package com.nasserapps.apitester.Controllers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nasserapps.apitester.AI.CustomNotificationStatement.Checklist;
import com.nasserapps.apitester.AI.CustomNotificationStatement.Rule;
import com.nasserapps.apitester.R;

import java.util.HashMap;
import java.util.List;

public class ChecklistAdapter extends BaseExpandableListAdapter {

        private Context mContext;
        private List<Checklist> mListDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<Checklist, List<Rule>> mListDataChild;

        public ChecklistAdapter(Context context, List<Checklist> listDataHeader,
                                     HashMap<Checklist, List<Rule>> listChildData) {
            mContext = context;
            mListDataHeader = listDataHeader;
            mListDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return mListDataChild.get(mListDataHeader.get(groupPosition)).get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            Rule child = (Rule) getChild(groupPosition, childPosition);
            String childText = "Rule "+ (childPosition+1) +": "+ child.getRuleStatement();

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_rules, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.rule_statement);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mListDataChild.get(mListDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mListDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return mListDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            Checklist header = (Checklist) getGroup(groupPosition);
            String headerTitle = "Checklist 1: " + header.getChecklistName();
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item_checklist, null);
            }

            TextView lblListHeader = (TextView) convertView.findViewById(R.id.ListHeader);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
