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
import java.util.Map;

public class GroupInformationActivity extends Activity {

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

        mListView = (ListView) findViewById(R.id.groupInfo_listview);
        newNameInput = findViewById(R.id.changeGroupName_input);
        groupCodeInput = findViewById(R.id.groupCode_textView);
        changeNameButton = findViewById(R.id.changeName_Button);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = mFirebaseDatabase.getReference("/Users/"+userID);
        myGroupsRef = mFirebaseDatabase.getReference();

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                showData(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

//    private void showData(DataSnapshot dataSnapshot) {
//        int counter = 1;
//        for (DataSnapshot ds : dataSnapshot.getChildren()){
//             if (counter == 2){
//                 this.userGroupId = ds.getValue().toString();
//             }
//
//             counter++;
//        }
//        Log.d("Result", userGroupId);
//    }

    public void changeGroupName(View v){
        final String newName = newNameInput.getText().toString();
        final String groupCode = groupCodeInput.getText().toString();

        if (!newName.equals("")){
            Query queryToGetGroupCode = myGroupsRef.child("Groups").orderByChild("code").equalTo(groupCode);

            queryToGetGroupCode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        System.out.println("Hello!!!");
                        Map<String, Object> nameUpdate = new HashMap<>();
                        nameUpdate.put("name", newName);

                        myGroupsRef.child("Groups").child(groupCode).updateChildren(nameUpdate);

                        Toast.makeText(GroupInformationActivity.this, "Name changed to " + newName + ".", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(this, "The group name can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
