package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupInformationActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference myGroupsRef;
    private String userID;

    private ListView mListView;
    private TextView newNameInput, groupCodeInput;
    private Button changeNameButton;

    private String userGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_information);
        setTitle("Group Information");

        mListView = (ListView) findViewById(R.id.groupInfo_listview);
        newNameInput = findViewById(R.id.changeGroupName_input);
        changeNameButton = findViewById(R.id.changeName_Button);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        this.myGroupsRef = mFirebaseDatabase.getReference();
        myRef = mFirebaseDatabase.getReference().child("Users/" + userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String groupId = (String) dataSnapshot.child("groupId").getValue();
                Log.wtf("SNAPSHOT:", groupId);

                Query queryToGetGroupInfo = myGroupsRef.child("Groups").orderByChild("code").equalTo(groupId);

                queryToGetGroupInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            List groupData = new ArrayList<>();
                            for (DataSnapshot groupDataSnapshot : dataSnapshot.getChildren()) {

                                String name = "Group name: " + groupDataSnapshot.child("name").getValue().toString();
                                String size = "Group size: " + groupDataSnapshot.child("size").getValue().toString();

                                groupData.add(name);
                                groupData.add(size);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                    GroupInformationActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    groupData
                            );

                            mListView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void changeGroupName(View v){
        final String newName = newNameInput.getText().toString();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String groupId = (String) dataSnapshot.child("groupId").getValue();
                Log.wtf("SNAPSHOT:", groupId);

                if (!newName.equals("")){
                    Query queryToGetGroupCode = myGroupsRef.child("Groups").orderByChild("code").equalTo(groupId);

                    queryToGetGroupCode.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List groupData = new ArrayList<>();
                            if (dataSnapshot.exists()){
                                Map<String, Object> nameUpdate = new HashMap<>();
                                nameUpdate.put("name", newName);

                                myGroupsRef.child("Groups").child(groupId).updateChildren(nameUpdate);

                                Toast.makeText(GroupInformationActivity.this, "Name changed to " + newName + ".", Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(GroupInformationActivity.this, "The group name can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
