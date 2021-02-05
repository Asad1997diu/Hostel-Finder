package com.example.hostelfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserRegister extends AppCompatActivity {
    private EditText username, fullname, email, password,phone;
    private Button register;
    private TextView text_login;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ProgressDialog dialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        username = findViewById(R.id.username_id);
        fullname = findViewById(R.id.fullname_id);
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password_id);
        phone = findViewById(R.id.phone_id);
        register = findViewById(R.id.register_id);
        text_login = findViewById(R.id.text_login_id);

        firebaseAuth = FirebaseAuth.getInstance();
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserRegister.this, UserLogin.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(UserRegister.this);
                dialog.setMessage("Please wait...");
                dialog.show();
                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_phone = phone.getText().toString();
                String str_password = password.getText().toString();
                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_phone) || TextUtils.isEmpty(str_password)) {
                    dialog.dismiss();
                    Toast.makeText(UserRegister.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    dialog.dismiss();
                    Toast.makeText(UserRegister.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(str_username,str_fullname,str_email,str_phone,str_password);

                }
            }
        });
    }

    private void register(String username, String fullname, String email,String phone, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            String userid= firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                            HashMap<String,Object> hashMap =new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username.toLowerCase());
                            hashMap.put("fullname",fullname);
                            hashMap.put("phone",phone);
                            hashMap.put("bio","");
                            hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/hostel-finder-65cf1.appspot.com/o/photo.jpg.png?alt=media&token=5c863a2a-b660-48f5-96a2-35843964bb25");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        dialog.dismiss();
                                        Intent intent =new Intent(UserRegister.this,UserMainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(UserRegister.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}