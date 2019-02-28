package com.example.sandra.roomate_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    public void buttonTest(View v) {
        Intent intent = new Intent(this, listviewActivity.class);
        startActivity(intent);
    }
}
