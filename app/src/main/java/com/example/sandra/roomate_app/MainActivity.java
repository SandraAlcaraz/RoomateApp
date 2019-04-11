package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button logoutBtn, groupInfoButton, listviewBtn;
    private TextView greetingLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        logoutBtn = findViewById(R.id.logout_btn);
        greetingLabel = findViewById(R.id.greeting_textView);
        groupInfoButton = findViewById(R.id.groupInfo_button);

        String userName = getIntent().getStringExtra("user");

        if (userName!=null){
            greetingLabel.setText("Hello, " + getIntent().getStringExtra("user"));
        } else {
            greetingLabel.setText("Hey there!");
        }
    }

    public void sessionLogout(View v){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

//    public void goToCodeGeneration(View v){
//        startActivity(new Intent(this, CodeGeneratorActivity.class));
//    }

    public void showGroupInfo(View v){
        startActivity(new Intent(this, GroupInformationActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
    public void buttonTest(View v) {
        Intent intent = new Intent(this, listviewActivity.class);
        startActivity(intent);
    }
    public void homeButton(View v){
        Intent homeIntent= new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        Toast.makeText(this, "You are in HOME", Toast.LENGTH_SHORT).show();
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

}
