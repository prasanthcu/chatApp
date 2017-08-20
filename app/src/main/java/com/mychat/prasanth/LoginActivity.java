package com.mychat.prasanth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;


public class LoginActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button mContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        // For testing the contacts activity
        mContacts = (Button) findViewById(R.id.contacts);
        mContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this,Contacts.class);
                startActivity(myIntent);
            }
        });


       /* mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                attemptLogin();
                return false;
            }
        });*/

        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.mychat.prasanth.RegisterActivity.class);
        startActivityForResult(intent, 1);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        // TODO: Use FirebaseAuth to sign in with email & password
        if( !email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        SignInErrorDialog();
                    } else {
                        Toast.makeText(LoginActivity.this,"Signing-in",Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(LoginActivity.this, ConnectScreen.class);
                        myIntent.putExtra("email",email);
                        startActivity(myIntent);
                        finish();
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this,"enter a vaild email and password combination",Toast.LENGTH_SHORT).show();
        }


    }

    // TODO: Show error on screen with an alert dialog

    private void SignInErrorDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Sign-in failed");
        alertDialog.setMessage("Wrong email and password combination");
        alertDialog.setPositiveButton("OK",null);
        alertDialog.show();
        return;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
                String displayName =  prefs.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
                String email =  prefs.getString(RegisterActivity.DISPLAY_EMAIL_KEY,null);

                Username user = new Username(email,displayName);

                mDatabase.child("user").child(email).setValue(user);

            }
        }
    }

}