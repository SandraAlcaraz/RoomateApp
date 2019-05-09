package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditShopingList extends AppCompatActivity {
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
        setContentView(R.layout.activity_edit_shoping_list);
        description = (EditText)findViewById(R.id.descriptionEdit);
        assignee =  (EditText) findViewById(R.id.assigEdit);
        done = (CheckBox) findViewById(R.id.doneItem2);
        saveButton = (Button) findViewById(R.id.saveButton2);

        Intent i = getIntent();
        Shopping dene = (Shopping)i.getSerializableExtra("dataTodo");

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
        Db = FirebaseDatabase.getInstance().getReference().child("Shoppinglist");
        Shopping newTod= new Shopping();
        newTod.setDescription(description.getText().toString());
        newTod.setAssigneeName(assignee.getText().toString());
        newTod.setCreatedBy(userId);
        newTod.setFinished(done.isChecked());


        try {
            Db.child(idTarea).setValue(newTod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finishActivity(v);
    }

}
