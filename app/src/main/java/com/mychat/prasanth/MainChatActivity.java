package com.mychat.prasanth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.mychat.prasanth.RegisterActivity.CHAT_PREFS;
import static com.mychat.prasanth.RegisterActivity.DISPLAY_NAME_KEY;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabase;
    private String emailID;
    private ArrayList<InstantMessage> mMessageList;
    // chumma
    private String[] mStringArray = {"a","b","c","d","e"};

    private ArrayList<String> mStringList;

    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // gettting email from intent
        Intent getIntent = getIntent();
        emailID = getIntent.getStringExtra("email");

        // TODO: Set up the display name and get the Firebase reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        retriveMsg();


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);
        list = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed

        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return false;
            }
        });


        // TODO: Add an OnClickListener to the sendButton to send a message

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    // TODO: Retrieve the display name from the Shared Preferences

    private void RetriveDisplayName(){
       ValueEventListener displayListner = new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Username user = dataSnapshot.getValue(Username.class);
               mDisplayName = user.getUser();
               Toast.makeText(getApplicationContext(),"welcome back",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       };
    }

    public void retriveMsg(){


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                mMessageList = new ArrayList<>();
                mStringList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    InstantMessage message = postSnapshot.getValue(InstantMessage.class);
                    mMessageList.add(message);
                    mStringList.add(message.getMessage());
                    Log.w("s", "loadPost:onCancelled");
                }
                Log.w("s", "loadPost:onCancelled");
                listView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("s", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("chat").child("123").addValueEventListener(postListener);
    }

    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
        String message = mInputText.getText().toString();
        String trimmedMessage = message.trim();
        if(trimmedMessage.length()!=0){
            InstantMessage chat = new InstantMessage(message,mDisplayName);
            mDatabase.child("chat").child("123").push().setValue(chat);
            mInputText.setText("");
        }

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.

    }

    private void listView(){
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,mStringList);
        list.setAdapter(adapter);
    }
});


    }


}
