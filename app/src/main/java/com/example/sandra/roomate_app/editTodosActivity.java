package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editTodosActivity extends AppCompatActivity {
    Todo todo;
    EditText description;
    EditText assignee;
    EditText dueDate;
    CheckBox done;
    Button saveButton;
    Button cancelButton;
    private DatabaseReference Db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;
    private String idTarea;
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

        idTarea=(String) i.getSerializableExtra("todoId");

        userId= dene.getCreatedBy();
        description.setText(dene.getDescription());
        assignee.setText(dene.getAssigneeName());
        done.setChecked(dene.getFinished());


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
        Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
        Todo newTod= new Todo();
        newTod.setDescription(description.getText().toString());
        newTod.setAssigneeName(assignee.getText().toString());

//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        newTod.setFinished(done.isChecked());
      //  Db.updateChildren(idTarea).;
        //Db.push().setValue(newTod);

        try {
            Db.child(idTarea).setValue(newTod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity(v);
    }

}
