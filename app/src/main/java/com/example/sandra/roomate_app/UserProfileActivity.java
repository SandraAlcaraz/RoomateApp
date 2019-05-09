package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    private Button logoutBtn, groupInfoButton, roommatesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Profile Info");
        setContentView(R.layout.activity_user_profile);

        logoutBtn = findViewById(R.id.logout_btn);
        groupInfoButton = findViewById(R.id.groupInfo_button);
        roommatesBtn = findViewById(R.id.roommatesList_button);
    }

    public void sessionLogout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void showGroupInfo(View v) {
        startActivity(new Intent(this, GroupInformationActivity.class));
    }

    public void goToRoomatelist(View v) {
        Intent intent = new Intent(this, RoomateListActivity.class);
        startActivity(intent);
    }

    public void homeButton(View v){
        Intent homeIntent= new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }
    public void toDoButton(View v){
        Intent toDoIntent= new Intent(this, ToDoActivity.class);
        startActivity(toDoIntent);
    }
    public void meetingsButton(View v){
        Intent meetingsIntent= new Intent(this, MeetingsActivity.class);
        startActivity(meetingsIntent);
    }
    public void announcementButton(View v){
        Intent announcementIntent= new Intent(this, AnnouncementActivity.class);
        startActivity(announcementIntent);
    }
    public void shoppingButton(View v){
        Intent shoppingIntent= new Intent(this, ShoppingActivity.class);
        startActivity(shoppingIntent);
    }

    public void userProfileButton(View v){
        Intent userProfileIntent = new Intent(this, UserProfileActivity.class);
        startActivity(userProfileIntent);
        Toast.makeText(this, "You are in User Profile", Toast.LENGTH_SHORT).show();
    }
}
