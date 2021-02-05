package com.example.hostelfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hostelfinder.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static java.security.AccessController.getContext;

public class BookNowActivity extends AppCompatActivity {
    private EditText name, occupation, institute, district, phone, room, bed, duration;
    private Button book;
    String postid;
    String publisherid;

    String email;
    FirebaseUser firebaseUser;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        name = findViewById(R.id.name_id);
        occupation = findViewById(R.id.occupation_id);
        institute = findViewById(R.id.institute_id);
        district = findViewById(R.id.district_id);
        phone = findViewById(R.id.phone_no_id);
        room = findViewById(R.id.type_id);
        bed = findViewById(R.id.bed_no_id);
        duration = findViewById(R.id.duration_id);
        book = findViewById(R.id.book_id);

        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        publisherid = intent.getStringExtra("publisherid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//email
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null){
                    return;
                }
                User user = dataSnapshot.getValue(User.class);

                email=user.getEmail();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(BookNowActivity.this);
                dialog.setMessage("Please wait...");
                dialog.show();
                String str_name = name.getText().toString();
                String str_occupation = occupation.getText().toString();
                String str_institute = institute.getText().toString();
                String str_district = district.getText().toString();
                String str_phone = phone.getText().toString();
                String str_room = room.getText().toString();
                String str_bed = bed.getText().toString();
                String str_duration = duration.getText().toString();

                String body=str_name+"\n"+str_occupation+"\n"+str_institute+"\n"+str_district+"\n"+str_phone+"\n"+str_room+"\n"+str_bed+"\n"+str_duration;

               Intent mailIntent=new Intent(Intent.ACTION_VIEW);
                Uri data=Uri.parse("mailto:?subject="+"Booking Information"+"&body="+body+"&to="+email);
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent,"Choose an email client"));

                dialog.dismiss();
                name.setText("");
                occupation.setText("");
                institute.setText("");
                district.setText("");
                phone.setText("");
                room.setText("");
                bed.setText("");
                duration.setText("");


            }
        });

    }


}