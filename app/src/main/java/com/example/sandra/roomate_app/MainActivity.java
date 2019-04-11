package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button logoutBtn, generateCodeBtn, listviewBtn;
    private TextView greetingLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        logoutBtn = findViewById(R.id.logout_btn);
        greetingLabel = findViewById(R.id.greeting_textView);

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

    public void goToRoomatelist(View v) {
        Intent intent = new Intent(this, RoomateListActivity.class);
        startActivity(intent);
    }

    public void goToCodeGeneration(View v){
        startActivity(new Intent(this, CodeGeneratorActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
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
