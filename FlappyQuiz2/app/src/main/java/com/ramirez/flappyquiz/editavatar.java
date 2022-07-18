package com.ramirez.flappyquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Locale;

public class editavatar extends AppCompatActivity {
    EditText fullname, username, emaildefault;
    Button onward;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    AutoCompleteTextView preflang;
    ImageView avatardef,pen,bag,ruler,hat,eraser,milk;
    String[] languages={"Markup Language","Cascading Stylesheet","Python","PHP","SQL","Java","Javascript"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountseeting);
        setTitle("Edit Account");
        fullname = (EditText) findViewById(R.id.setfullname);
        username = (EditText) findViewById(R.id.setusername);
        preflang = (AutoCompleteTextView) findViewById(R.id.languagepick);
        emaildefault = (EditText) findViewById(R.id.emaildef);
        onward = (Button) findViewById(R.id.partial);

        avatardef = findViewById(R.id.avaratdefault);
        avatardef.setTag("avatar");
        ImageView pen = findViewById(R.id.pencil);
        ImageView bag = findViewById(R.id.bag);
        ImageView eraser = findViewById(R.id.eraser);
        ImageView ruler = findViewById(R.id.ruler);
        ImageView hat = findViewById(R.id.cap);
        ImageView milk = findViewById(R.id.milk);

        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.pen);
                avatardef.setTag("pen");
            }
        });
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.bag);
                avatardef.setTag("bag");
            }
        });
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.eraser);
                avatardef.setTag("eraser");
            }
        });
        ruler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.ruler);
                avatardef.setTag("ruler");
            }
        });
        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.hat);
                avatardef.setTag("hat");
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatardef.setImageResource(R.drawable.milk);
                avatardef.setTag("milk");
            }
        });
        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);
        preflang.setAdapter(adapter);
        preflang.setThreshold(1);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                emaildefault.setText(email.toLowerCase(Locale.ROOT).toString());
                fullname.setText(name);
//                username.setText(name);
                // Get username from user child
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String current = dataSnapshot.child(name).child("user").getValue(String.class);
                        String lang = dataSnapshot.child(name).child("prog").getValue(String.class);
                        username.setText(current);
                        preflang.setText(lang);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                Picasso.get().load(photoUrl).into(avatardef);
            }
        }
        onward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeProfile();
            }
        });
    }
    public void completeProfile() {
        // [START update_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uri;
        String fulln = fullname.getText().toString();
        if (avatardef.getTag() == "milk"){
            uri = "https://i.imgur.com/vbpTlYn.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "bag"){
            uri = "https://i.imgur.com/zK5Q3Kd.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "pencil"){
            uri = "https://i.imgur.com/V9LljKQ.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "ruler"){
            uri = "https://i.imgur.com/aMLz6i0.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "hat"){
            uri = "https://i.imgur.com/VmsAyCn.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "eraser"){
            uri = "https://i.imgur.com/xUdXMOf.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }

        if (avatardef.getTag() == "avatar"){
            uri = "https://i.imgur.com/1XbGgUw.png";
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fulln)
                    .setPhotoUri(Uri.parse(uri))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Update", "User profile Created.");
                                Intent intent
                                        = new Intent(editavatar.this,
                                        editaccount.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("proglang").setValue(preflang.getText().toString());
            databaseReference.child("user").child(String.valueOf(username.getText().toString())).child("username").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("user").setValue(username.getText().toString());
            databaseReference.child("user").child(String.valueOf(fullname.getText().toString())).child("prog").setValue(preflang.getText().toString());
        }
    }
}