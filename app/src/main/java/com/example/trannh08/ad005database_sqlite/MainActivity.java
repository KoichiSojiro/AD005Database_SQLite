package com.example.trannh08.ad005database_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trannh08.ad005database_sqlite.classes.Contact;
import com.example.trannh08.ad005database_sqlite.classes.ContactAdapter;
import com.example.trannh08.ad005database_sqlite.classes.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    private ListView listView;
    ArrayList<Contact> contacts;
    ArrayAdapter<Contact> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();
        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.listView);

        // 1. retrieve contact list from SQLite
        contacts = dbHelper.getAllContacts();
        // 2. put the list in an ArrayAdapter
        // in this case, we use modified layout is [R.layout.contact_item]
        // we can use default layout [android.R.layout.simple_list_item_1]
        arrayAdapter = new ContactAdapter(this, R.layout.contact_item, contacts);
        // 3. set Adapter for the ListView
        listView.setAdapter(arrayAdapter);
        // 4. update data for ArrayAdapter
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = arrayAdapter.getItem(position);
                int contactID = contact.getId();
                Bundle dataBundle = new Bundle();
                dataBundle.putInt(dbHelper.CONTACTS_COLUMN_ID, contactID);
                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
