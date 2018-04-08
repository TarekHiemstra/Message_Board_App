package com.metropolia.tarekh.exercise_9_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private EditText mPostText;
    private Button mSubmitButton;
    private Button mCancelButton;

    private StorageReference mStorage;
    private DatabaseReference mPostDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();
        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Posts");

        mPostText = findViewById(R.id.postTextEt);
        mSubmitButton = findViewById(R.id.submitPost);
        mCancelButton = findViewById(R.id.cancelBtn);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitThePost();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPostActivity.this, AllPostsListedActivity.class));
                finish();
            }
        });

    }

    private void submitThePost() {

        final String displayNameValue = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        final String textValue = mPostText.getText().toString().trim();

        // include the current date and time with SimpleDateFormat
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat
                ("dd MMM yyyy HH:mm:ss", Locale.getDefault());
        final String currentDateAndTime = simpleDateFormat.format(new Date());

        if (!TextUtils.isEmpty(textValue)) {

            DatabaseReference newPost = mPostDatabase.push();

            Map<String, String> dataInHashMap = new HashMap<>();
            dataInHashMap.put("userID", mUser.getUid());
            dataInHashMap.put("displayName", displayNameValue);
            dataInHashMap.put("timestamp", currentDateAndTime);
            dataInHashMap.put("text", textValue);
            dataInHashMap.put("username", mUser.getEmail());
            newPost.setValue(dataInHashMap);

            startActivity(new Intent(AddPostActivity.this, AllPostsListedActivity.class));
            finish();
        }
    }
}
