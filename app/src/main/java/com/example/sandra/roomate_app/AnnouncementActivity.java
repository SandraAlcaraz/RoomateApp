package com.example.sandra.roomate_app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AnnouncementActivity extends AppCompatActivity {

    private Button add_room, deleteButton, changeButton;
    private TextInputEditText room_name;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private String name;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Announcements");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        setTitle("Announcement");

        add_room = (Button) findViewById(R.id.button);
        room_name = (TextInputEditText) findViewById(R.id.texth);
        listView = (ListView) findViewById(R.id.list);
        deleteButton = (Button) findViewById(R.id.button4);
        changeButton = (Button) findViewById(R.id.button5);
        deleteButton.setEnabled(false);
        changeButton.setEnabled(false);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,list_of_rooms);

        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(room_name.getText().toString(),"");
                root.updateChildren(map);
            }
        });


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                        changeButton.setEnabled(true);
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
                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void deleteItem(View view) {
        listView.setItemChecked(selectedPosition, false);
        root.child(list_of_rooms.get(selectedPosition)).removeValue();
        deleteButton.setEnabled(false);
        changeButton.setEnabled(false);
    }

    public void changeItem(View view){
        try {
            listView.setItemChecked(selectedPosition, false);
            root.child(list_of_rooms.get(selectedPosition)).removeValue();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(room_name.getText().toString(),"");
            root.updateChildren(map);
            changeButton.setEnabled(false);
            deleteButton.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}