package com.spp.sqlitedemodatabaseapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VisitorListActivity extends AppCompatActivity {

    DatabaseHandler db;
    ArrayList<Visitor> visitorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_list);

        db = new DatabaseHandler(this);
        visitorsList = db.getAllVisitors();

        ListView listView = (ListView) findViewById(R.id.visitorlistview);
        final CustomAdaptor customAdaptor = new CustomAdaptor();
        listView.setAdapter(customAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(VisitorListActivity.this)
                        .setTitle("Delete entry " + String.valueOf(visitorsList.get(position).getId()))
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                db.deleteVisitor(String.valueOf(visitorsList.get(position).getId()));
                                //visitorsList.remove(position);
                                visitorsList.clear();
                                visitorsList=db.getAllVisitors();
                                customAdaptor.notifyDataSetChanged();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return visitorsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position,View view,ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listitem, null);

            // ImageView imageView=(ImageView)view.findViewById(R.id.image);
            TextView textViewName = (TextView) view.findViewById(R.id.name);
            TextView textViewDesc = (TextView) view.findViewById(R.id.desc);

            //imageView.setImageResource(visitorsList.get(position).getName());
            textViewName.setText(visitorsList.get(position).getName());
            textViewDesc.setText(visitorsList.get(position).getLanguage());


            return view;
        }
    }

}

