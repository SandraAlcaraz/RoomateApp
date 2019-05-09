package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoppingListener, AnnouncementListener {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef, shoppingRef, announcementRef;
    private String userID;

    private TextView greetingLabel;
    private ListView recentShoppingListView,
                     recentAnnouncementsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        greetingLabel = findViewById(R.id.greeting_textView);

        recentShoppingListView = findViewById(R.id.recentShoppingItems_listview);
        recentAnnouncementsListView = findViewById(R.id.recentAnnouncements_listview);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("Users/" + userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = (String) dataSnapshot.child("name").getValue();
                greetingLabel.setText("Hello, " + userName + ".");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        shoppingRef = mFirebaseDatabase.getReference().child("Shoppinglist");

        shoppingRef.addChildEventListener(new ChildEventListener() {
            List shoppingItemsActivity = new ArrayList<>();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String createdBy = dataSnapshot.child("createdBy").getValue().toString();
                String description = dataSnapshot.child("description").getValue().toString();
                String value = (createdBy + " added a Shopping Item: " + description);

                shoppingItemsActivity.add(value);
                onShoppingAdded(shoppingItemsActivity);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String EditedBy = dataSnapshot.child("createdBy").getValue().toString();
                String description = dataSnapshot.child("description").getValue().toString();
                String value = (EditedBy + " edited a Shopping Item to " + description);

                shoppingItemsActivity.add(value);
                onShoppingAdded(shoppingItemsActivity);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String description = dataSnapshot.child("description").getValue().toString();
                String value = ("The "+ description + " shopping item has been deleted.");

                shoppingItemsActivity.add(value);
                onShoppingAdded(shoppingItemsActivity);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        announcementRef = mFirebaseDatabase.getReference().child("Announcements");

        announcementRef.addChildEventListener(new ChildEventListener() {
            List announcementsActivity = new ArrayList<>();
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String createdBy = dataSnapshot.child("createdBy").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();
                String value = (createdBy + " added an announcement: " + content);

                announcementsActivity.add(value);
                onAnnouncementAdded(announcementsActivity);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String EditedBy = dataSnapshot.child("createdBy").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();
                String value = (EditedBy + " edited an announcement to " + content);

                announcementsActivity.add(value);
                onAnnouncementAdded(announcementsActivity);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String content = dataSnapshot.child("content").getValue().toString();
                String value = ("The "+ content + " announcement has been deleted.");

                announcementsActivity.add(value);
                onAnnouncementAdded(announcementsActivity);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    public void homeButton(View v){
        Intent homeIntent= new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        Toast.makeText(this, "You are in HOME", Toast.LENGTH_SHORT).show();
    }
    public void toDoButton(View v){
        Intent toDoIntent= new Intent(this, ToDoActivity.class);
        startActivity(toDoIntent);
    }
    public void meetingsButton(View v){
        Intent meetingsIntent= new Intent(this, MeetingsActivity.class);
        startActivity(meetingsIntent);
    }
    public void announcementButton(View v){
        Intent announcementIntent= new Intent(this, AnnouncementActivity.class);
        startActivity(announcementIntent);
    }
    public void shoppingButton(View v){
        Intent shoppingIntent= new Intent(this, ShoppingActivity.class);
        startActivity(shoppingIntent);
    }

    public void userProfileButton(View v){
        Intent userProfileIntent = new Intent(this, UserProfileActivity.class);
        startActivity(userProfileIntent);
    }

    @Override
    public void onShoppingAdded(List shoppingItemsActivity) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                shoppingItemsActivity
        );
        recentShoppingListView.setAdapter(adapter);
    }

    @Override
    public void onAnnouncementAdded(List announcementsActivity) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                announcementsActivity
        );
        recentAnnouncementsListView.setAdapter(adapter);
    }
}
