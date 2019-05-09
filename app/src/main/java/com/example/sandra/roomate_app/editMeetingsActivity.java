package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editMeetingsActivity extends AppCompatActivity {
    Meeting meeting;
    EditText title, date, time;
    Button am, pm;

    private DatabaseReference Db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;
    private String idMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meetings);


        title = (EditText)findViewById(R.id.event2);
        date =  (EditText) findViewById(R.id.date2);
        time = (EditText) findViewById(R.id.time2);
        am = (Button) findViewById(R.id.saveButton40);
        pm = (Button) findViewById(R.id.cancelButton40);

        Intent i = getIntent();
        Meeting dene = (Meeting)i.getSerializableExtra("dataMeeting");

        idMeeting=(String) i.getSerializableExtra("meetingId");

        userId= dene.getCreatedBy();
        title.setText(dene.getTitle());
        date.setText(dene.getDate());
        time.setText(dene.getTime());


    }

    public void cancelEditMeeting(View v){
        finishActivity( v);
    }


    public void finishActivity(View v){

        // go back sending info        // use an intent!        Intent intent = new Intent();
        Intent intent= new Intent(this, MeetingsActivity.class);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void saveAM(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        Meeting newTod= new Meeting();
        newTod.setTitle(title.getText().toString());
        newTod.setDate(date.getText().toString());
        newTod.setTime(time.getText().toString() + " AM");

//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        //  Db.updateChildren(idTarea).;
        //Db.push().setValue(newTod);

        try {
            Db.child(idMeeting).setValue(newTod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity(v);
    }

    public void savePM(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        Meeting newTod= new Meeting();
        newTod.setTitle(title.getText().toString());
        newTod.setDate(date.getText().toString());
        newTod.setTime(time.getText().toString() + " PM");

//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        //  Db.updateChildren(idTarea).;
        //Db.push().setValue(newTod);

        try {
            Db.child(idMeeting).setValue(newTod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity(v);
    }

}
