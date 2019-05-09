package com.example.sandra.roomate_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardShopping extends DialogFragment {
    private final static String ITEM = "item";
    private DatabaseReference Db;
    Shopping shopping;
    EditText description;
    EditText Spinner;
    EditText dueDate;
    CheckBox done;
    Button saveButton;
    String name;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userId;

    public static DashboardShopping newInstance(Shopping dashboardItem) {
        Bundle args = new Bundle();
        args.putParcelable(ITEM, dashboardItem);
        DashboardShopping fragment = new DashboardShopping();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            shopping =savedInstanceState.getParcelable(ITEM);
        if(getArguments()!=null && shopping ==null)
            shopping = getArguments().getParcelable(ITEM);
        setStyle(STYLE_NO_FRAME,getTheme());

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("Users/" + userId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = (String) dataSnapshot.child("name").getValue();
                name = userName;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }



    public void saveText(){
        Db = FirebaseDatabase.getInstance().getReference().child("Shoppinglist");
        Shopping newTod= new Shopping();
        newTod.setDescription(description.getText().toString());
        newTod.setAssigneeName(name);
//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        newTod.setFinished(done.isChecked());
        Db.push().setValue(newTod);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM, shopping);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO for tablets use other layout
        View v = inflater.inflate(R.layout.dashboard_shopping,container,false);

        description = v.findViewById(R.id.description10);
        dueDate = v.findViewById(R.id.dueDateItem10);
        done = v.findViewById(R.id.doneItem10);

        saveButton = v.findViewById(R.id.saveButton20);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveText();
            }
        });
        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DateFormat dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dashboard_shopping,null);
        ((TextView)view.findViewById(R.id.description10)).setText(shopping.getDescription());
        String time = dateFormat.format(shopping.getDueDate());
        ((EditText)view.findViewById(R.id.dueDateItem10)).setText(time);
        ((CheckBox)view.findViewById(R.id.doneItem10)).setChecked(shopping.getFinished());


        return new AlertDialog.Builder(getActivity())
                .setView(view)
                //.setIcon(R.drawable.alert_dialog_icon)
                //.setTitle(title)
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
