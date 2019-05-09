package com.example.sandra.roomate_app;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity {

    private DatabaseReference Db;
    private ListView announcementsListView;
    Announcement [] announcementsArray;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        setTitle("Announcements");
        announcementsListView = (ListView) findViewById(R.id.announcementsListView);

        Db = FirebaseDatabase.getInstance().getReference().child("Announcements");

        Db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List notes = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    String createdBy = noteDataSnapshot.child("createdBy").getValue().toString();
                    String content = noteDataSnapshot.child("content").getValue().toString();

                    //Todo todoItem = noteDataSnapshot.getValue(Todo.class);
                    Announcement todoItem = new Announcement();
                    todoItem.setContent(content);
                    todoItem.setCreatedBy(createdBy);
                    notes.add(todoItem);
                }

                announcementsArray = new Announcement[notes.size()];
                for(int i=0;i<notes.size();i++){
                    announcementsArray[i]= (Announcement) notes.get(i);
                }
                adapter= getAnnouncementsAdapter(announcementsArray);
                adapter.notifyDataSetChanged();
                announcementsListView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(announcementsListView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }




    private SimpleAdapter getAnnouncementsAdapter(Announcement[] announcements){
        // create the item mapping

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("E, d  M");
        String[] from = {"content", "createdBy"};
        int[] to = {
                R.id.itemAnnouncementContent,
                R.id.itemAnnouncementCreatedBy,
                // R.id.itemTodoDueDate
        };
        // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        for(Announcement announcement : announcements){
            HashMap<String, String> map = new HashMap<>();
            map.put("content", announcement.getContent());
            map.put("createdBy",  announcement.getCreatedBy());
//          map.put("dueDate", simpleDateFormat.format(shopping.getDueDate()));
            fillMaps.add(map);
        }
        return new SimpleAdapter(this,fillMaps,R.layout.item_announcement,from,to);
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

    public void createAnnouncements(View v){
        Db = FirebaseDatabase.getInstance().getReference().child("Announcements");
        DashboardAnnouncement fragment = new DashboardAnnouncement();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.relative,  fragment).addToBackStack(null);
        transaction.commit();
    }

    public void homeButtonA(View v){
        Intent homeIntent= new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }
    public void toDoButtonA(View v){
        Intent toDoIntent= new Intent(this, ToDoActivity.class);
        startActivity(toDoIntent);
    }
    public void meetingsButtonA(View v){
        Intent meetingsIntent= new Intent(this, MeetingsActivity.class);
        startActivity(meetingsIntent);
    }
    public void announcementButtonA(View v){
        Intent announcementIntent= new Intent(this, AnnouncementActivity.class);
        startActivity(announcementIntent);
        Toast.makeText(this, "You are in Announcements", Toast.LENGTH_SHORT).show();
    }
    public void shoppingButtonA(View v){
        Intent shoppingIntent= new Intent(this, ShoppingActivity.class);
        startActivity(shoppingIntent);
    }

    public void userProfileButtonA(View v){
        Intent userProfileIntent = new Intent(this, UserProfileActivity.class);
        startActivity(userProfileIntent);
    }

    public void editAnnouncement(View v){
        Intent intent = new Intent(this, EditDeleteAnnouncement.class);
        startActivity(intent);
    }

}