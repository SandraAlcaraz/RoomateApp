package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAnnouncementActivity extends AppCompatActivity implements NameListener{
    Announcement announcement;
    EditText content;
    Button saveButton;
    Button cancelButton;
    private DatabaseReference Db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef, userRef;
    private String userId, userName;
    private String idAnnouncement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todos);

        content = (EditText)findViewById(R.id.contentEdit);
        saveButton = (Button) findViewById(R.id.saveButton2);

        Intent i = getIntent();
        Announcement dene = (Announcement) i.getSerializableExtra("dataTodo");

        idAnnouncement=(String) i.getSerializableExtra("todoAnnouncement");

        userId= dene.getCreatedBy();
        content.setText(dene.getContent());

        userRef = FirebaseDatabase.getInstance().getReference("Users/" + userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("name").getValue().toString();
                onNameRetrieved(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cancelEditTodo(View v){
        finishActivity( v);
    }


    public void finishActivity(View v){

        // go back sending info        // use an intent!        Intent intent = new Intent();
        Intent intent= new Intent(this, ToDoActivity.class);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void saveText(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Announcements");
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setContent(content.getText().toString());
        newAnnouncement.setCreatedBy(userName);

        try {
            Db.child(idAnnouncement).setValue(newAnnouncement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity(v);
    }

    @Override
    public void onNameRetrieved(String userName) {
        this.userName = userName;
    }
}
