package com.example.sandra.roomate_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EditDeleteMeeting extends AppCompatActivity {
    private FloatingActionButton deleteButton, editButton;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfMeetings = new ArrayList<>();
    private String name;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Meetings");
    private TextInputEditText room_name;
    private DatabaseReference Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_meeting);
        setTitle("Meetings");

       // add_room = (Button) findViewById(R.id.button);
        room_name = (TextInputEditText) findViewById(R.id.text);
        listView = (ListView) findViewById(R.id.dinamicList1);
        deleteButton = (FloatingActionButton) findViewById(R.id.floatingDeleteTodo1);
        editButton = (FloatingActionButton) findViewById(R.id.floatingEditTodo1);


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listOfMeetings);

        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);



        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                        editButton.setEnabled(true);
                    }
                });


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                listOfMeetings.clear();
                listOfMeetings.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void deleteItem10(View view) {
        listView.setItemChecked(selectedPosition, false);
        root.child(listOfMeetings.get(selectedPosition)).removeValue();

    }

    public void changeItem10(View view){
        try {
            listView.setItemChecked(selectedPosition, false);
            final String id= listOfMeetings.get(selectedPosition);


            Db = FirebaseDatabase.getInstance().getReference().child("Meetings/"+id);

            Db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String title= dataSnapshot.child("title").getValue().toString();
                    String createdBy=  dataSnapshot.child("createdBy").getValue().toString();
                    String date= dataSnapshot.child("date").getValue().toString();
                    String time= dataSnapshot.child("time").getValue().toString();
                    Meeting newTodo= new Meeting();
                    newTodo.setCreatedBy(createdBy);
                    newTodo.setTitle(title);
                    newTodo.setDate(date);
                    newTodo.setTime(time);

                    //Bundle bundle = new Bundle();
                    //bundle.putSerializable("dataTodo", newTodo);
                    //DashboardTodo fragment = new DashboardTodo();
                    Intent intent = new Intent(EditDeleteMeeting.this, editMeetingsActivity.class);
                    intent.putExtra("dataMeeting", (Serializable) newTodo);
                    intent.putExtra("meetingId",id);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
