package com.ramirez.flappyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flappyregandlogin-default-rtdb.firebaseio.com");
    private Button start;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        ImageView avatardef = findViewById(R.id.avatardef);
        ImageView pencil = findViewById(R.id.pencilavatar);
        ImageView bag = findViewById(R.id.bagavatar);
        ImageView cup = findViewById(R.id.cupavatar);
        ImageView ruler = findViewById(R.id.ruleravatar);
        ImageView hat = findViewById(R.id.hatavatar);
        ImageView eraser = findViewById(R.id.eraseravatar);
        start = (Button) findViewById(R.id.logout);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String curruser = intent.getStringExtra("USERNAME");
                databaseReference.child("user").child(curruser).child("avatar").setValue(avatardef.getBackground().toString());
                startActivity(new Intent(MainActivity.this,mission.class));
                Toast.makeText(MainActivity.this,"Welcome to FlappyQuiz!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.pen);
            }
        });
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.bag);
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.eraser);
            }
        });
        cup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.milk);
            }
        });
        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.hat);
            }
        });
        ruler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.ruler);
            }
        });
    }

}