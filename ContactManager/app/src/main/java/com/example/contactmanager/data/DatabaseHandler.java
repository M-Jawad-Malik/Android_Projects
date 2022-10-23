package com.example.contactmanager.data;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.contactmanager.R;
import com.example.contactmanager.model.Contact;
import com.example.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME,null,Util.DATABASE_VERSION);
    }
    //We Create Table here and apply CRUD Operation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE="CREATE TABLE "+Util.TABLE_NAME+"("
                +Util.KEY_ID+" INTEGER PRIMARY KEY,"+Util.KEY_NAME+" TEXT,"+Util.KEY_PHONE_NUMBER+" TEXT)";
        db.execSQL(CREATE_CONTACT_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE=String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        onCreate(db);
    }
    public void addContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.KEY_NAME,contact.getName());
        contentValues.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNo());

        db.insert(Util.TABLE_NAME,null,contentValues);
        db.close();
    }

    public Contact getContact(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Util.TABLE_NAME,new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE_NUMBER},Util.KEY_ID+"=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        Contact contact=new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNo(cursor.getString(2));
        return contact;

    }
    public List<Contact> getAllContact()
    {
        List<Contact> contactsList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String selectAll="Select * from "+Util.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectAll,null);
        if(cursor.moveToFirst())
        {
            do{
                Contact contact=new Contact("","");
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNo(cursor.getString(2));
                contactsList.add(contact);
            }
            while (cursor.moveToNext());
        }
return contactsList;
    }
    public int updateContact(Contact contact)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.KEY_NAME,contact.getName());
        contentValues.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNo());
        return db.update(Util.TABLE_NAME,contentValues,Util.KEY_ID+"=?",new String[]{String.valueOf(contact.getId())});
    }
    public void delete(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }
    public int countContact()
    {
        String query="Select * from "+Util.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        return cursor.getCount();
    }
}