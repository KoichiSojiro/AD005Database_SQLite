package com.example.trannh08.ad005database_sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trannh08.ad005database_sqlite.classes.Contact;
import com.example.trannh08.ad005database_sqlite.classes.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private ListView listView;
    TextView textView_contactItem;
    ArrayList<Contact> contacts;
    ArrayAdapter<Contact> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();


        dbHelper = new DBHelper(this);
        textView_contactItem = (TextView) findViewById(R.id.textView_contact_item);
        //ArrayList contacts = dbHelper.getAllContactNames();
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listView = (ListView) findViewById(R.id.listView);
        contacts = dbHelper.getAllContacts();
        arrayAdapter = new ContactAdapter(this, R.layout.contact_item, contacts);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /*
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int contactID = position + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(dbHelper.CONTACTS_COLUMN_ID, contactID);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
            */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int contactID = position + 1;
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(dbHelper.CONTACTS_COLUMN_ID, contactID);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    class ContactAdapter extends ArrayAdapter<Contact> {
        Activity activity;
        int resources;
        ArrayList<Contact> contacts;

        public ContactAdapter(Activity context, int resource, ArrayList<Contact> contacts) {
            super(context, resource, contacts);
            this.activity = context;
            this.resources = resource;
            this.contacts = contacts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View viewRow = layoutInflater.inflate(this.resources, null);
            TextView textView = (TextView) viewRow.findViewById(R.id.textView_contact_item);
            final Contact contact = this.contacts.get(position);
            textView.setText(contact.Name);
            return viewRow;
            //return super.getView(position, convertView, parent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.mainMenu:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(dbHelper.CONTACTS_COLUMN_ID, 0);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This will run when press "Back" on the Main Screen
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
