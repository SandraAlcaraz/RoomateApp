package com.example.sandra.roomate_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.sandra.roomate_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MeetingsActivity extends AppCompatActivity {
    private DatabaseReference Db;
    private ListView meetingsListView;
    Meeting [] meetingsArray;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        setTitle("Meetings");
        meetingsListView= (ListView) findViewById(R.id.meetingListView);

        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        Todo todo1= new Todo("id1","lavar","0001","me", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Marua");

        Todo todo2= new Todo("id1","planchar","0001","me", Calendar.getInstance().getTime(),Calendar.getInstance().getTime(), "Juan");





        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List notes = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    String desAr=noteDataSnapshot.child("title").getValue().toString();
                    String assigAr=noteDataSnapshot.child("date").getValue().toString();
                    String createdAr=noteDataSnapshot.child("time").getValue().toString();

                    //Todo todoItem = noteDataSnapshot.getValue(Todo.class);
                    Meeting meetingItem= new Meeting();
                    meetingItem.setTitle(desAr);
                    meetingItem.setDate(assigAr);
                    meetingItem.setTime(createdAr);
                    notes.add(meetingItem);
                }

                meetingsArray= new Meeting[notes.size()];
                for(int i=0;i<notes.size();i++){
                    meetingsArray[i]= (Meeting) notes.get(i);
                }
                adapter= getMeetingsAdapter(meetingsArray);
                adapter.notifyDataSetChanged();
                meetingsListView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(meetingsListView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }




    private SimpleAdapter getMeetingsAdapter(Meeting[] meetings){
        // create the item mapping

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("E, d  M");
        String[] from = {"id", "title", "groupId", "date", "time"};
        int[] to = {
                R.id.itemMeetingId,
                R.id.itemMeetingTitle,
                R.id.itemMeetingGroupId,
                R.id.itemMeetingDate,
                R.id.itemMeetingTime
                // R.id.itemTodoDueDate
        };
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        for(Meeting meeting : meetings){
            HashMap<String, String> map = new HashMap<>();
            map.put("id", String.valueOf( meeting.getId()));
            map.put("title", meeting.getTitle());
            map.put("groupId", meeting.getGroupId());
            map.put("date",  meeting.getDate());
            map.put("time",  meeting.getTime());
//            map.put("dueDate", simpleDateFormat.format(shopping.getDueDate()));
            fillMaps.add(map);
        }
        return new SimpleAdapter(this,fillMaps,R.layout.item_meeting,from,to);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void createMeetings(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Meetings");
        DashboardMeeting fragment = new DashboardMeeting();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.relative,  fragment).addToBackStack(null);
        transaction.commit();
    }

}
