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

public class DashboardAnnouncement extends DialogFragment implements NameListener{
    private final static String ITEM = "item";
    private DatabaseReference Db;
    Announcement announcement;
    EditText content;
    EditText Spinner;
    Button saveButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef, userRef;
    private FirebaseDatabase mFirebaseDatabase;
    private String userId, userName;

    public static DashboardAnnouncement newInstance(Announcement dashboardItem) {
        Bundle args = new Bundle();
        args.putParcelable(ITEM, dashboardItem);
        DashboardAnnouncement fragment = new DashboardAnnouncement();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
            announcement =savedInstanceState.getParcelable(ITEM);
        if(getArguments()!=null && announcement ==null)
            announcement = getArguments().getParcelable(ITEM);
        setStyle(STYLE_NO_FRAME,getTheme());

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        userRef = FirebaseDatabase.getInstance().getReference("Users/" + userId);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.child("name").getValue().toString();
                onNameRetrieved(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void saveText(){
        Db = FirebaseDatabase.getInstance().getReference().child("Announcements");
        Announcement newTod= new Announcement();
        newTod.setContent(content.getText().toString());
        newTod.setCreatedBy(userName);
        Db.push().setValue(newTod);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITEM, announcement);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO for tablets use other layout
        View v = inflater.inflate(R.layout.dashboard_announcement,container,false);

        content = v.findViewById(R.id.description10);

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
        View view = inflater.inflate(R.layout.dashboard_announcement,null);
        ((TextView)view.findViewById(R.id.description10)).setText(announcement.getContent());


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

    @Override
    public void onNameRetrieved(String userName) {
        this.userName = userName;
    }
}