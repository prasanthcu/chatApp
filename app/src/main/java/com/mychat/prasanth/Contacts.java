package com.mychat.prasanth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {


    ArrayList<String> mconactsList;
    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mDatabase = FirebaseDatabase.getInstance().getReference();

           getList();
//        setListView();


    }

//    private void setListView(){
//        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,mconactsList);
//
//        ListView list = new ListView(getApplicationContext());
//        list.setAdapter(Adapter);
//    }
//
    private void getList(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Username post = postSnapshot.getValue(Username.class);
                    mconactsList.add(post.getUser());
                }

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("chat", "loadPost:onCancelled", databaseError.toException());

            }
        };

        mDatabase.child("user").child("email").addValueEventListener(postListener);
    }
}







// mDatabase.child("user").child(email).setValue(user);