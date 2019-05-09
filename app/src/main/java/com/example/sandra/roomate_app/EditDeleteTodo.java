package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EditDeleteTodo extends AppCompatActivity {
    private FloatingActionButton deleteButton, editButton;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfTodos = new ArrayList<>();
    private String name;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Tasks");
    private TextInputEditText room_name;
    private DatabaseReference Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_todo);
        setTitle("Tasks");

       // add_room = (Button) findViewById(R.id.button);
        room_name = (TextInputEditText) findViewById(R.id.texth);
        listView = (ListView) findViewById(R.id.dinamicList);
        deleteButton = (FloatingActionButton) findViewById(R.id.floatingDeleteTodo);
        editButton = (FloatingActionButton) findViewById(R.id.floatingEditTodo);


        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listOfTodos);

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
                listOfTodos.clear();
                listOfTodos.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void deleteItem(View view) {
        listView.setItemChecked(selectedPosition, false);
        root.child(listOfTodos.get(selectedPosition)).removeValue();

    }

    public void changeItem1(View view){
        try {
            listView.setItemChecked(selectedPosition, false);
            Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
            String id= listOfTodos.get(selectedPosition);
            String assigneeName= Db.child(id).child("assigneeName").toString();
            String createdBy=  Db.child(id).child("createdBy").toString();
            Boolean finished= Boolean.valueOf(Db.child(id).child("finished").toString());
            Todo newTodo= new Todo();
            newTodo.setFinished(finished);
            newTodo.setCreatedBy(createdBy);
            newTodo.setAssigneeName(assigneeName);

            //quitar
            newTodo.setDescription("Sandra Descrip");
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataTodo", newTodo);
            Log.wtf("EditDeleteTodo", newTodo.getDescription());
            Log.wtf("EditDeleteTodo", newTodo.getAssigneeName());
            DashboardTodo fragment = new DashboardTodo();
            Intent intent = new Intent(this, editTodosActivity.class);
            intent.putExtra("dataTodo", (Serializable) newTodo);
            startActivity(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
