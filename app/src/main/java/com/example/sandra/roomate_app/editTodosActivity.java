package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class editTodosActivity extends AppCompatActivity {
    Todo todo;
    EditText description;
    EditText assignee;
    EditText dueDate;
    CheckBox done;
    Button saveButton;
    Button cancelButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todos);


        description = (EditText)findViewById(R.id.descriptionEdit);
        assignee =  (EditText) findViewById(R.id.assigEdit);
        done = (CheckBox) findViewById(R.id.doneItem2);

        saveButton = (Button) findViewById(R.id.saveButton2);

        Intent i = getIntent();
        Todo dene = (Todo)i.getSerializableExtra("dataTodo");

       // Intent intent = getIntent();
        //String dataDTodo = intent.getStringExtra("dataTodo");

    //    Todo todoJ= dataDTodo;
      //  getIntent().getSerializableExtra("dataTodo");
        description.setText(dene.getDescription());

    }
}
