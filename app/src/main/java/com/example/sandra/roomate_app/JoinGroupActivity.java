package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinGroupActivity extends Activity {

    private TextView groupCodeInput;
    private Button joinGroupButton;
    private String user;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        groupCodeInput = findViewById(R.id.groupCode_input);
        joinGroupButton = findViewById(R.id.join_button);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
    }

    public void joinGroup(View v){
        final String groupCode = groupCodeInput.getText().toString();

        if (!groupCode.equals("")){
            Query queryToGetGroupCode = myRef.child("Groups").orderByChild("code").equalTo(groupCode);

            queryToGetGroupCode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        Map<String, Object> membersUpdate = new HashMap<>();
                        membersUpdate.put("members", userId);

                        myRef.child("Groups").child(groupCode).updateChildren(membersUpdate);

                        Map<String, Object> groupCodeUpdate = new HashMap<>();
                        groupCodeUpdate.put("groupId", groupCode);

                        myRef.child("Users").child(userId).updateChildren(groupCodeUpdate);

                        Toast.makeText(JoinGroupActivity.this, "Added to the Group", Toast.LENGTH_SHORT);

                        Intent intent = new Intent(JoinGroupActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "The group code can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void createGroup(View v){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }
}
