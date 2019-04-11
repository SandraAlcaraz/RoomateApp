package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateGroupActivity extends Activity {

    private Button createGroupButton;
    private TextView groupName, groupSize;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        createGroupButton = findViewById(R.id.createG_button);
        groupName = findViewById(R.id.groupName_input);
        groupSize = findViewById(R.id.groupSize_input);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
    }

    private String generateAccessCode(){
        String generatedCode = "";
        Random random = new Random();

        String alphabet = "1234567890abdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 7; i++){
            char newChar = alphabet.charAt(random.nextInt(alphabet.length()));

            if (!Character.isDigit(newChar)){
                newChar = Character.toUpperCase(newChar);
            }

            generatedCode = generatedCode + newChar;
        }

        return generatedCode;
    }

    public void createGroup(View v){
        String name = groupName.getText().toString();
        int size = Integer.parseInt(groupSize.getText().toString());
        if (!name.equals("") && size != 0){
            String groupCode = generateAccessCode();
            ArrayList<String> members = new ArrayList<>();
            members.add(userId);
            DatabaseReference groupsRef = myRef.child("Groups").child(groupCode);
            groupsRef.setValue(new Group(groupCode, name, size, userId, userId, members));

            Map<String, Object> groupIdUpdate = new HashMap<>();
            groupIdUpdate.put("groupId", groupCode);

            myRef.child("Users").child(userId).updateChildren(groupIdUpdate);

            Toast.makeText(this, "Group created successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Fill out the fields!", Toast.LENGTH_SHORT).show();
        }

    }
}
