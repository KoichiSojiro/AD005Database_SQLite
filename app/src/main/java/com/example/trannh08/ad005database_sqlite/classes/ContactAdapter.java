package com.example.trannh08.ad005database_sqlite.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trannh08.ad005database_sqlite.R;

import java.util.ArrayList;

/**
 * Created by trannh08 on 8/24/2016.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
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
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View viewRow = layoutInflater.inflate(this.resources, null);
        TextView textView = (TextView) viewRow.findViewById(R.id.textView_contact_item);
        final Contact contact = this.contacts.get(position);
        textView.setText(contact.getName());
        return viewRow;
    }
}
