package com.example.trannh08.ad005database_sqlite.classes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trannh08.ad005database_sqlite.MainActivity;

import java.util.ArrayList;

/**
 * Created by trannh08 on 8/19/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mySQLiteDatabase.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "city";

    //private HashMap _HashMap;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "(" + CONTACTS_COLUMN_ID + " integer primary key, "
                        + CONTACTS_COLUMN_NAME + " text, "
                        + CONTACTS_COLUMN_EMAIL + " text, "
                        + CONTACTS_COLUMN_PHONE + " text,"
                        + CONTACTS_COLUMN_STREET + " text,"
                        + CONTACTS_COLUMN_CITY + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }

    public Cursor getDataById(int contactId) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from " + CONTACTS_TABLE_NAME
                        + " where " + CONTACTS_COLUMN_ID + "=" + contactId, null);
        return cursor;
    }

    public Cursor getDataByName(String contactName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from " + CONTACTS_TABLE_NAME
                        + " where " + CONTACTS_COLUMN_NAME + "=" + contactName, null);
        return cursor;
    }

    public int numberOfRows() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public long insertContact(String contact_name, String contact_email, String contact_phone,
                              String contact_street, String contact_city) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, contact_name);
        contentValues.put(CONTACTS_COLUMN_EMAIL, contact_email);
        contentValues.put(CONTACTS_COLUMN_PHONE, contact_phone);
        contentValues.put(CONTACTS_COLUMN_STREET, contact_street);
        contentValues.put(CONTACTS_COLUMN_CITY, contact_city);
        long insertedRecords = sqLiteDatabase.insert(
                CONTACTS_TABLE_NAME,
                null,
                contentValues);

        return insertedRecords;
    }

    public int updateContact(Integer contact_id, String contact_name, String contact_email,
                             String contact_phone, String contact_street, String contact_city) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, contact_name);
        contentValues.put(CONTACTS_COLUMN_EMAIL, contact_email);
        contentValues.put(CONTACTS_COLUMN_PHONE, contact_phone);
        contentValues.put(CONTACTS_COLUMN_STREET, contact_street);
        contentValues.put(CONTACTS_COLUMN_CITY, contact_city);
        int updatedRecords = sqLiteDatabase.update(
                CONTACTS_TABLE_NAME,
                contentValues,
                CONTACTS_COLUMN_ID + " = ?",
                new String[]{Integer.toString(contact_id)}
        );

        return updatedRecords;
    }

    public Integer deleteContact(Integer contact_id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int deletedRecords = sqLiteDatabase.delete(
                CONTACTS_TABLE_NAME,
                CONTACTS_COLUMN_ID + " = ? ",
                new String[]{Integer.toString(contact_id)}
        );

        return deletedRecords;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Contact contact = new Contact();
            contact.setId(cursor.getInt(cursor.getColumnIndex(CONTACTS_COLUMN_ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));
            contact.setPhone(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_PHONE)));
            contact.setStreet(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STREET)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_EMAIL)));
            contact.setCity(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_CITY)));
            contacts.add(contact);
            cursor.moveToNext();
        }

        return contacts;
    }

    public ArrayList<String> getAllContactNames() {
        ArrayList<String> contacts = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            contacts.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_NAME)));
            cursor.moveToNext();
        }

        return contacts;
    }
}
