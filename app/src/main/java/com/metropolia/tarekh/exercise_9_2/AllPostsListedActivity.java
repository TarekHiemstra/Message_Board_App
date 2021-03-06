package com.metropolia.tarekh.exercise_9_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllPostsListedActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private List<Post> postsList;
    private PostsRecyclerAdapter postsRecyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_posts_listed);

        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference().child("Posts");
        mDatabaseReference.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        postsList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signout) {
            if (mUser != null && mAuth != null) {
                mAuth.signOut();
                startActivity(new Intent(AllPostsListedActivity.this, MainActivity.class));
                finish();
            }
        } else if (item.getItemId() == R.id.action_add) {
            if (mUser != null && mAuth != null) {
                startActivity(new Intent(AllPostsListedActivity.this, AddPostActivity.class));
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Post post = dataSnapshot.getValue(Post.class);
                Collections.reverse(postsList);
                postsList.add(post);
                Collections.reverse(postsList);
                postsRecyclerAdapter = new PostsRecyclerAdapter(AllPostsListedActivity.this, postsList);
                recyclerView.setAdapter(postsRecyclerAdapter);
                postsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        postsList.clear();
    }
}
