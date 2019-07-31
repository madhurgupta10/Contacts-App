package com.example.contactsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.contactsapp.helper.ContactHelper;
import com.example.contactsapp.model.CustomContact;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    int PERMISSION_READ_WRITE_CONTACTS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        // Initiate the views

        listView = findViewById(R.id.list_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // If permission is not granted then the user will get a prompt to grant permission
        if ((checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            && (checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[] {
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.READ_CONTACTS
            }, PERMISSION_READ_WRITE_CONTACTS);

        } else {
            showContacts();
        }
    }

    private void showContacts() {
        // This method fetch the contacts from the helper class and then displays them in a list view
        cursor = ContactHelper.getContactCursor(getContentResolver(), "");
        String[] fields = new String[]{ContactsContract.Data.DISPLAY_NAME};

        // We are using a SimpleCursor Adapter for the List View
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1,
                cursor, fields, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                cursor.moveToPosition(position);
                CustomContact customContact = new CustomContact(cursor);
                Intent intent =  new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("customContact", customContact);
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(itemClickListener);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Asks user for the permission
        if (requestCode == PERMISSION_READ_WRITE_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Snackbar.make(findViewById(android.R.id.content), R.string.grant_contact_permission,
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
