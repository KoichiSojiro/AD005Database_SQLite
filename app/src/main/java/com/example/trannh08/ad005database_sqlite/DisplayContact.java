package com.example.trannh08.ad005database_sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trannh08.ad005database_sqlite.classes.DBHelper;

public class DisplayContact extends AppCompatActivity {

    private DBHelper dbHelper;

    TextView textView_id;
    TextView textView_name;
    TextView textView_phone;
    TextView textView_email;
    TextView textView_street;
    TextView textView_city;
    int contactId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        textView_id = (TextView) findViewById(R.id.textView_id);
        textView_name = (TextView) findViewById(R.id.editText_name);
        textView_phone = (TextView) findViewById(R.id.editText_phone);
        textView_email = (TextView) findViewById(R.id.editText_street);
        textView_street = (TextView) findViewById(R.id.editText_email);
        textView_city = (TextView) findViewById(R.id.editText_city);

        dbHelper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int selectedId = extras.getInt(DBHelper.CONTACTS_COLUMN_ID);

            // showing Contact base on ContactId from MainActivity
            if (selectedId > 0) {
                Cursor cursor = dbHelper.getDataById(selectedId);
                contactId = selectedId;
                cursor.moveToFirst();
                String id = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String street = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String city = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                if (!cursor.isClosed()) {
                    cursor.close();
                }

                Button button_save = (Button) findViewById(R.id.button_save);
                button_save.setVisibility(View.INVISIBLE);

                textView_id.setText(id);
                textView_id.setFocusable(false);
                textView_id.setClickable(false);

                textView_name.setText(name);
                textView_name.setFocusable(false);
                textView_name.setClickable(false);

                textView_phone.setText(phone);
                textView_phone.setFocusable(false);
                textView_phone.setClickable(false);

                textView_email.setText(email);
                textView_email.setFocusable(false);
                textView_email.setClickable(false);

                textView_street.setText(street);
                textView_street.setFocusable(false);
                textView_street.setClickable(false);

                textView_city.setText(city);
                textView_city.setFocusable(false);
                textView_city.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt(DBHelper.CONTACTS_COLUMN_ID);
            if (Value > 0) {
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else {
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.Edit_Contact:
                Button b = (Button) findViewById(R.id.button_save);
                b.setVisibility(View.VISIBLE);
                textView_name.setEnabled(true);
                textView_name.setFocusableInTouchMode(true);
                textView_name.setClickable(true);

                textView_phone.setEnabled(true);
                textView_phone.setFocusableInTouchMode(true);
                textView_phone.setClickable(true);

                textView_email.setEnabled(true);
                textView_email.setFocusableInTouchMode(true);
                textView_email.setClickable(true);

                textView_street.setEnabled(true);
                textView_street.setFocusableInTouchMode(true);
                textView_street.setClickable(true);

                textView_city.setEnabled(true);
                textView_city.setFocusableInTouchMode(true);
                textView_city.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbHelper.deleteContact(contactId);
                                Toast.makeText(getApplicationContext(), R.string.delete_success,
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle(R.string.are_you_sure);
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt(dbHelper.CONTACTS_COLUMN_ID);
            if (Value > 0) {
                if ((dbHelper.updateContact(
                        contactId,
                        textView_name.getText().toString(),
                        textView_phone.getText().toString(),
                        textView_email.getText().toString(),
                        textView_street.getText().toString(),
                        textView_city.getText().toString())
                ) > 0) {
                    Toast.makeText(getApplicationContext(), R.string.updated_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.updated_fail, Toast.LENGTH_SHORT).show();
                }
            } else {
                if ((dbHelper.insertContact(
                        textView_name.getText().toString(),
                        textView_phone.getText().toString(),
                        textView_email.getText().toString(),
                        textView_street.getText().toString(),
                        textView_city.getText().toString())
                ) > 0) {
                    Toast.makeText(getApplicationContext(), R.string.inserted_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.inserted_fail, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
