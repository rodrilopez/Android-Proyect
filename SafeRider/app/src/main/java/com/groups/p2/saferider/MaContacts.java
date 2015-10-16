package com.groups.p2.saferider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.content.ContentResolver;

/**
 * Created by rodrigo on 15/10/15.
 */
public class MaContacts {

    private final int CONTACT_PICKER_RESULT = 1;
    private Activity activity;
    private Uri uriContact;
    private String contactID;
    private static final String TAG = MaContacts.class.getSimpleName();

    public MaContacts(Activity activity) {
        this.activity = activity;
    }

    public void selectContact() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        this.activity.startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }
}