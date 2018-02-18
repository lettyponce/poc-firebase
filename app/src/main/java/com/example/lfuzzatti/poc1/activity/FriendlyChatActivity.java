package com.example.lfuzzatti.poc1.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lfuzzatti.poc1.BuildConfig;
import com.example.lfuzzatti.poc1.R;
import com.example.lfuzzatti.poc1.adapter.MessageAdapter;
import com.example.lfuzzatti.poc1.model.Message;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendlyChatActivity extends AppCompatActivity {

    public static final String TAG = "FriendlyChatActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static final String FRIENDLY_MSG_LENGTH_KEY = "friendly_msg_length";
    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PIC = 2;

    //Write a message to the database
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseStorage mFirebaseStorage;

    private DatabaseReference mMessagesDatabaseReference;
    private StorageReference mStorageReference;

    private ChildEventListener mChildEventListener;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    //Using for loading message
    private ListView listMessage;
    private MessageAdapter messageAdapter;
    private ProgressBar progressBar;

    //Using for insert message
    private ImageButton photoPickerButton;
    private EditText fieldMessage;
    private Button sendMessage;

    //View owner message
    private String ownerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_chat);

        ownerName = ANONYMOUS;

        // Initialize Firebase components
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("message");
        mStorageReference = mFirebaseStorage.getReference().child("chat_photos");

        //initialize referentes of view
        initializeViews();

        //initilize message ListView and its adapter
        initializeListMessages();

        //initialize progressbar
        progressBar.setVisibility(View.INVISIBLE);

        // Enable Send button when there's text to send
        defineSendButton();

        //Define limits to send text
        defineLimitMessage();

        // ImagePickerButton shows an image picker to upload a image for a message
        onClickPhotoPickerButton();

        //Send button sends a message and clear field message
        onClickSendMessageButton();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //sign in
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    onSignedOutCleanup();
                    //sign out
                    startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                                .build(),
                        RC_SIGN_IN);
                }
            }
        };

        // Create Remote Config Settings to enable developer mode.
        // Fetching configs from the server is normally limited to 5 request per hour.
        // Enabling developer mode allows many more requests to made per hour, so developers
        // can test differente config values during development.
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(remoteConfigSettings);

        // Define default config values.
        // Defaults are used when fetched config values are not available.
        // Ex: If an error ocurred fetching values from the server
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put(FRIENDLY_MSG_LENGTH_KEY, DEFAULT_MSG_LENGTH_LIMIT);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
        fetchConfig();

    }

    private void attachDatabaseReadListener() {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message myNewMessage = dataSnapshot.getValue(Message.class);
                messageAdapter.add(myNewMessage);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void onSignedInInitialize(String username) {
        ownerName = username;
        attachDatabaseReadListener();
    }

    private void onSignedOutCleanup(){
        ownerName = ANONYMOUS;
        messageAdapter.clear();
        detachDatabaseReadListener();
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void onClickSendMessageButton() {
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Send message on click
                Message myMessage = new Message(fieldMessage.getText().toString(),
                        ownerName,
                        null);

                mMessagesDatabaseReference.push().setValue(myMessage);

                //Clear field message
                fieldMessage.setText("");
            }
        });
    }

    private void onClickPhotoPickerButton() {
        photoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                startActivityForResult(Intent.createChooser(intent,
                        "Complete action using"), RC_PHOTO_PIC);
            }
        });
    }

    private void defineLimitMessage() {
        fieldMessage.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)
        });
    }

    private void defineSendButton() {
        fieldMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendMessage.setEnabled(true);
                } else {
                    sendMessage.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void initializeListMessages() {
        List<Message> listMessages = new ArrayList<>();
        messageAdapter = new MessageAdapter(this,
                                            R.layout.item_message,
                                            listMessages);

        listMessage.setAdapter(messageAdapter);
    }

    private void initializeViews() {

        listMessage = (ListView) findViewById(R.id.messageListView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        photoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        fieldMessage = (EditText) findViewById(R.id.messageEditText);
        sendMessage = (Button) findViewById(R.id.sendButton);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (requestCode == RC_PHOTO_PIC && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            StorageReference photoRef = mStorageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            Message myMessage = new Message(null, ownerName,
                                    downloadUrl.toString());

                            mMessagesDatabaseReference.push().setValue(myMessage);
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        messageAdapter.clear();
        detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Fetch the config to determine the allowed length of messages
    public void fetchConfig() {

        long cacheExpiration = 3600; // 1 hour in seconds

        // If developer mode is enabled reduce cacheExpiration to 0 so that each fetch goes to the
        // server. This should not be used in release builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        // Make the fetched config available
                        // via FirebaseRemoteConfig get<type> calls, e.g., getLong, getString.
                        mFirebaseRemoteConfig.activateFetched();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        // An error occurred when fetching the config.
                        Log.w(TAG, "Error fetching config", e);

                        // Update the EditText length limit with
                        // the newly retrived values from Remote Config
                        applyRetrivedLengthLimit();
                    }
                });
    }

    /**
     *  Apply retrived length limit to edit text field.
     *  This result may be fresh from the server or it may be from cached values.
     */
    private void applyRetrivedLengthLimit() {
        Long friendly_msg_length = mFirebaseRemoteConfig.getLong(FRIENDLY_MSG_LENGTH_KEY);
        fieldMessage.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(friendly_msg_length.intValue())});

        Log.d(TAG, FRIENDLY_MSG_LENGTH_KEY + "=" + friendly_msg_length);
    }
}
