package com.example.sandra.roomate_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class CodeGeneratorActivity extends AppCompatActivity {

    private TextView codeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_generator);
        setTitle("Code Generator");

        codeTextView = findViewById(R.id.code_textView);

        codeTextView.setText(generateAccessCode());
    }

    private String generateAccessCode(){
        String generatedCode = "";
        Random random = new Random();

        String alphabet = "1234567890abdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < 7; i++){
            char newChar = alphabet.charAt(random.nextInt(alphabet.length()));

            if (!Character.isDigit(newChar)){
                newChar = Character.toUpperCase(newChar);
            }

            generatedCode = generatedCode + newChar;
        }

        return generatedCode;
    }
}
