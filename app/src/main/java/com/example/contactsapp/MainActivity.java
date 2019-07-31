package com.example.contactsapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.contactsapp.helper.ContactHelper;
import com.example.contactsapp.model.CustomContact;

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

        listView = findViewById(R.id.list_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        cursor = ContactHelper.getContactCursor(getContentResolver(), "");
        String[] fields = new String[]{ContactsContract.Data.DISPLAY_NAME};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1,
                cursor, fields, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                cursor.moveToPosition(position);
                CustomContact customContact = new CustomContact(cursor);
                Toast.makeText(MainActivity.this, ""+customContact.getPhoneNumber(),
                        Toast.LENGTH_SHORT).show();
            }
        };

        listView.setOnItemClickListener(itemClickListener);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_READ_WRITE_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(MainActivity.this, R.string.grant_contact_permission,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
