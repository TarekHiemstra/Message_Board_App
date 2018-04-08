package com.metropolia.tarekh.exercise_9_2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText displayName;
    private Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firstName = findViewById(R.id.newFirstNameEt);
        lastName = findViewById(R.id.newLasttNameEt);
        email = findViewById(R.id.newEmailEt);
        password = findViewById(R.id.newPasswordEt);
        confirmPassword = findViewById(R.id.newConfirmPasswordEt);
        displayName = findViewById(R.id.newDisplayNameEt);
        register = findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstNameString = firstName.getText().toString();
                final String lastNameString = lastName.getText().toString();
                final String emailString = email.getText().toString();
                final String passwordString = password.getText().toString();
                final String confirmPasswordString = confirmPassword.getText().toString();
                final String displayNameString = displayName.getText().toString();

                if (!emailString.equals("") && !passwordString.equals("") && passwordString.equals(confirmPasswordString)) {
                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(CreateAccountActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {

                                        Toast.makeText(CreateAccountActivity.this, "Account could not be created.", Toast.LENGTH_LONG)
                                                .show();
                                    } else {
                                        Toast.makeText(CreateAccountActivity.this, "Account has been created.", Toast.LENGTH_LONG)
                                                .show();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(displayNameString).build();
                                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                                        startActivity(new Intent(CreateAccountActivity.this, AllPostsListedActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            }
        });
    }


}
