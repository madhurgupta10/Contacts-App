package com.example.contactsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsapp.model.CustomContact;
import com.google.android.material.snackbar.Snackbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        CustomContact customContact = (CustomContact) getIntent().getSerializableExtra("customContact");

        // Checks if the customContact Object is null or not
        if (customContact != null) {
            initViews(customContact);
        } else {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.sorry),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    // Initialises the Views
    private void initViews(final CustomContact customContact) {

        TextView nameTextView = findViewById(R.id.name);
        nameTextView.setText(customContact.getName());

        TextView numberTextView = findViewById(R.id.number);
        numberTextView.setText(customContact.getPhoneNumber());

        LinearLayout smsLinearLayout = findViewById(R.id.sms);
        smsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessageApp(customContact.getPhoneNumber());
            }
        });

        LinearLayout callLinearLayout = findViewById(R.id.call);
        callLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCallApp(customContact.getPhoneNumber());
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // If profile pic is null then the visibility will remain GONE
        ImageView profilePicImageView = findViewById(R.id.profile_pic);
        if (customContact.getProfilePicUri() != null) {
            profilePicImageView.setImageURI(Uri.parse(customContact.getProfilePicUri()));
            profilePicImageView.setVisibility(View.VISIBLE);
        }
    }

    // Opens the Call Dialer with the number
    private void openCallApp(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(callIntent);
    }

    // Opens the Message App with the number
    private void openMessageApp(String number) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
        startActivity(sendIntent);
    }
}
