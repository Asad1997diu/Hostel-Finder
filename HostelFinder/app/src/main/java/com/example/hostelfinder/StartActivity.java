package com.example.hostelfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.hostelfinder.Model.Post;
import com.example.hostelfinder.Model.UserID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    private Button login, register;
    FirebaseUser firebaseUser;
    private Button user_login, user_register;
    List<UserID> users=new ArrayList<>();

   /* @Override
    protected void onStart() {
        super.onStart();
       // readUserKey();



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            if (users.contains(firebaseUser.getUid())) {
                startActivity(new Intent(StartActivity.this, AdminMainActivity.class));
            }
            else {
                startActivity(new Intent(StartActivity.this, UserMainActivity.class));
            }

            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        user_login = findViewById(R.id.login_user_id);
        user_register = findViewById(R.id.register_user_id);
        login = findViewById(R.id.login_admin_id);
        register = findViewById(R.id.register_admin_id);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, UserLogin.class));
                finish();
            }
        });
        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, UserRegister.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginAdmin.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterAdmin.class));
                finish();
            }
        });
    }

   /* private void readUserKey(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UserID userID = snapshot.getValue(UserID.class);

                    users.add(userID);
                    Log.d("Key", String.valueOf(users));
                    Log.d("Key2", String.valueOf(firebaseUser.getUid()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/
}