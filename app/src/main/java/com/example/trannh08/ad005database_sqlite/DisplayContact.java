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

    int from_Where_I_Am_Coming = 0;
    private DBHelper dbHelper;

    TextView _id;
    TextView _name;
    TextView _phone;
    TextView _email;
    TextView _street;
    TextView _city;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        _id = (TextView) findViewById(R.id.textView_id);
        _name = (TextView) findViewById(R.id.editText_name);
        _phone = (TextView) findViewById(R.id.editText_phone);
        _email = (TextView) findViewById(R.id.editText_street);
        _street = (TextView) findViewById(R.id.editText_email);
        _city = (TextView) findViewById(R.id.editText_city);

        dbHelper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = dbHelper.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();
                String id = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_ID));
                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phone = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String email = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String street = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String city = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button_save);
                b.setVisibility(View.INVISIBLE);

                _id.setText((CharSequence) id);
                _id.setFocusable(false);
                _id.setClickable(false);

                _name.setText((CharSequence) nam);
                _name.setFocusable(false);
                _name.setClickable(false);

                _phone.setText((CharSequence) phone);
                _phone.setFocusable(false);
                _phone.setClickable(false);

                _email.setText((CharSequence) email);
                _email.setFocusable(false);
                _email.setClickable(false);

                _street.setText((CharSequence) street);
                _street.setFocusable(false);
                _street.setClickable(false);

                _city.setText((CharSequence) city);
                _city.setFocusable(false);
                _city.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            int Value = extras.getInt("id");
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
                _name.setEnabled(true);
                _name.setFocusableInTouchMode(true);
                _name.setClickable(true);

                _phone.setEnabled(true);
                _phone.setFocusableInTouchMode(true);
                _phone.setClickable(true);

                _email.setEnabled(true);
                _email.setFocusableInTouchMode(true);
                _email.setClickable(true);

                _street.setEnabled(true);
                _street.setFocusableInTouchMode(true);
                _street.setClickable(true);

                _city.setEnabled(true);
                _city.setFocusableInTouchMode(true);
                _city.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbHelper.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully",
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
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if ((dbHelper.updateContact(
                        id_To_Update,
                        _name.getText().toString(),
                        _phone.getText().toString(),
                        _email.getText().toString(),
                        _street.getText().toString(),
                        _city.getText().toString())
                ) > 0) {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if ((dbHelper.insertContact(
                        _name.getText().toString(),
                        _phone.getText().toString(),
                        _email.getText().toString(),
                        _street.getText().toString(),
                        _city.getText().toString())
                ) > 0) {
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
