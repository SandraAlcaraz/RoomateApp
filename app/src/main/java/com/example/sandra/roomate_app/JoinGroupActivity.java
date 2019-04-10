package com.example.sandra.roomate_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinGroupActivity extends Activity {

    private Button joinGroupButton;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        joinGroupButton = findViewById(R.id.join_button);
        user = getIntent().getStringExtra("user");
    }

    public void joinGroup(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void createGroup(View v){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }
}
