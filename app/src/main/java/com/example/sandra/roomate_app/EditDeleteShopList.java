package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditDeleteShopList extends AppCompatActivity {
    private FloatingActionButton deleteButton, editButton;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfTodos = new ArrayList<>();
    private String name;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Shoppinglist");
    private TextInputEditText room_name;
    private DatabaseReference Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_shop_list);
        listView = (ListView) findViewById(R.id.dinamicList);
        deleteButton = (FloatingActionButton) findViewById(R.id.floatingDeleteShop);
        editButton = (FloatingActionButton) findViewById(R.id.floatingEditShop);

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

    public void changeItem2(View view){
        try {
            listView.setItemChecked(selectedPosition, false);
            final String id= listOfTodos.get(selectedPosition);


            Db = FirebaseDatabase.getInstance().getReference().child("Shoppinglist/"+id);

            Db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String assigneeName= dataSnapshot.child("assigneeName").getValue().toString();
                    String createdBy=  dataSnapshot.child("createdBy").getValue().toString();
                    Boolean finished= Boolean.valueOf(dataSnapshot.child("finished").toString());
                    String description= dataSnapshot.child("description").getValue().toString();


                    Shopping shop= new Shopping();
                    shop.setFinished(finished);
                    shop.setCreatedBy(createdBy);
                    shop.setAssigneeName(assigneeName);
                    shop.setDescription(description);


                    Intent intent = new Intent(EditDeleteShopList.this, EditShopingList.class);
                    intent.putExtra("dataTodo", (Serializable) shop);
                    intent.putExtra("todoId",id);
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
