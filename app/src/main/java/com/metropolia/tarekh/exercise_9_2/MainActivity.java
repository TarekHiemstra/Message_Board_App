package com.metropolia.tarekh.exercise_9_2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;

    private EditText emailEt;
    private EditText passwordEt;
    private Button login;
    private Button register;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.loginEmailEt);
        passwordEt = findViewById(R.id.loginPasswordEt);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.signupBtn);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("message");
        databaseReference.setValue("Connected to database!");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();

                if (mUser != null) {
                    Log.d(TAG, "user signed in");
                    Toast.makeText(MainActivity.this, "You are signed in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, AllPostsListedActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "user signed out");
                    Toast.makeText(MainActivity.this, "You are not signed in", Toast.LENGTH_LONG).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(emailEt.getText().toString()) &&
                        !TextUtils.isEmpty(passwordEt.getText().toString())) {
                    final String email = emailEt.getText().toString();
                    String password = passwordEt.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Username or password incorrect", Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Sign in successful!", Toast.LENGTH_LONG)
                                                .show();
                                        startActivity(new Intent(MainActivity.this, AllPostsListedActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateAccountActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
