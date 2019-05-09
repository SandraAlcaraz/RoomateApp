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
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DashboardMeeting extends DialogFragment {
    private final static String ITEM = "item";
    private DatabaseReference Db;
    Meeting meeting;
    EditText title, date, time;
    EditText dueDate;
    Button am, pm;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userId;

    public static DashboardMeeting newInstance(Meeting dashboardItem) {
        Bundle args = new Bundle();
        args.putParcelable(ITEM, dashboardItem);
        DashboardMeeting fragment = new DashboardMeeting();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            meeting =savedInstanceState.getParcelable(ITEM);
        if(getArguments()!=null && meeting ==null)
            meeting = getArguments().getParcelable(ITEM);
        setStyle(STYLE_NORMAL,getTheme());

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        myRef = mFirebaseDatabase.getReference().child("Users/" + userId);

    }



    public void saveAM(){
        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        Meeting newTod= new Meeting();
        newTod.setTitle(title.getText().toString());
        newTod.setDate(date.getText().toString());
        newTod.setTime(time.getText().toString() + " AM");
//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        Db.push().setValue(newTod);
        getActivity().onBackPressed();

    }

    public void savePM(){
        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        Meeting newTod= new Meeting();
        newTod.setTitle(title.getText().toString());
        newTod.setDate(date.getText().toString());
        newTod.setTime(time.getText().toString() + " PM");
//        newTod.setDueDate((Date) dueDate.getText());

        newTod.setCreatedBy(userId);
        Db.push().setValue(newTod);
        getActivity().onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM, meeting);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO for tablets use other layout
        View v = inflater.inflate(R.layout.dashboard_meeting,container,false);

        title = v.findViewById(R.id.event);
        date = v.findViewById(R.id.date);
        time = v.findViewById(R.id.time);
        dueDate = v.findViewById(R.id.dueDateItem20);

        am = v.findViewById(R.id.saveButton30);
        pm = v.findViewById(R.id.cancelButton30);

        am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAM();
            }
        });

        pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePM();
            }
        });

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DateFormat dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dashboard_meeting,null);
        ((TextView)view.findViewById(R.id.event)).setText(meeting.getTitle());
        String time = dateFormat.format(meeting.getDueDate());
        ((EditText)view.findViewById(R.id.dueDateItem20)).setText(time);


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
