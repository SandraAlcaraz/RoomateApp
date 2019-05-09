package com.example.sandra.roomate_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardTodo extends DialogFragment {
    private final static String ITEM = "item";
    private DatabaseReference Db;
    Todo todo;
    EditText description;
    EditText Spinner;
    EditText dueDate;
    CheckBox done;
    Button saveButton;
    Button cancelButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userId;

    public static DashboardTodo newInstance(Todo dashboardItem) {
        Bundle args = new Bundle();
        args.putParcelable(ITEM,dashboardItem);
        DashboardTodo fragment = new DashboardTodo();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            todo =savedInstanceState.getParcelable(ITEM);
        if(getArguments()!=null && todo ==null)
            todo = getArguments().getParcelable(ITEM);
        setStyle(STYLE_NO_FRAME,getTheme());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

    }


    public void saveText(){
        Db = FirebaseDatabase.getInstance().getReference().child("Tasks");
        Todo newTod= new Todo();
        newTod.setDescription(description.getText().toString());
       newTod.setAssigneeName(Spinner.getText().toString());
//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        newTod.setFinished(done.isChecked());
        Db.push().setValue(newTod);
        getActivity().onBackPressed();
    }

    public void cancelO(){
        getActivity().onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM, todo);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO for tablets use other layout
        View v = inflater.inflate(R.layout.dashboard_todo,container,false);
        description = v.findViewById(R.id.description);
        Spinner = v.findViewById(R.id.assigneeItem);
        dueDate = v.findViewById(R.id.dueDateItem);
        done = v.findViewById(R.id.doneItem);

        saveButton = v.findViewById(R.id.saveButton2);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
        cancelButton = v.findViewById(R.id.cancelButton2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelO();
            }
        });
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateFormat dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dashboard_todo,null);
        ((TextView)view.findViewById(R.id.description)).setText(todo.getDescription());
         String time = dateFormat.format(todo.getDueDate());
        ((EditText)view.findViewById(R.id.dueDateItem)).setText(time);
        ((CheckBox)view.findViewById(R.id.doneItem)).setChecked(todo.getFinished());


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                //.setIcon(R.drawable.alert_dialog_icon)
               // .setTitle(title)
                /*.setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((FragmentAlertDialog)getActivity()).doPositiveClick();
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((FragmentAlertDialog)getActivity()).doNegativeClick();
                            }
                        }
                )*/
                .create();
    }

}
