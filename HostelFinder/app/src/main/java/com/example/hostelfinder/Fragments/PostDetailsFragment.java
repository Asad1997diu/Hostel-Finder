package com.example.hostelfinder.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hostelfinder.BookNowActivity;
import com.example.hostelfinder.Model.Post;
import com.example.hostelfinder.Model.User;
import com.example.hostelfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostDetailsFragment extends Fragment {
    String postid, publisherid;
    private Button profile_button, call_button,book_now,map;
    String phone_number, post_publisher_value;
    private ImageView post_image;

    private ImageView fav_btn;
    private TextView post_name,post_address,post_number,post_gender,post_seatno, post_description, post_price;

    private FirebaseUser firebaseUser;

    private static final int REQUEST_CALL = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_post_details, container, false);


        SharedPreferences preferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        postid = preferences.getString("postid", "none");
        publisherid = preferences.getString("publisherid", "none");


        // value = getArguments().getString("YourKey");

        map = view.findViewById(R.id.map_id);
        post_image = view.findViewById(R.id.details_post_image);
        post_name = view.findViewById(R.id.details_name);
        post_address = view.findViewById(R.id.details_address);
        post_number = view.findViewById(R.id.details_number);
        post_gender = view.findViewById(R.id.details_gender);
        post_seatno = view.findViewById(R.id.details_seatno);
        post_price = view.findViewById(R.id.details_price);
        post_description = view.findViewById(R.id.details_description);
        //profile_button = view.findViewById(R.id.profile_id);

        call_button = view.findViewById(R.id.call_author_id);
        book_now = view.findViewById(R.id.book_id);
        fav_btn = view.findViewById(R.id.save);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       // publisherid=firebaseUser.getUid();

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+post_address.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//set package to "com.google.android.apps.maps" so that only google maps is opened.
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav_btn.getTag() != null && fav_btn.getTag().equals("save")) {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(postid).setValue(true);

                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid()).child(postid).removeValue();

                }
            }
        });


        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), BookNowActivity.class);
                intent.putExtra("postid", postid);
                intent.putExtra("publisherid", publisherid);
                startActivity(intent);


            }
        });

       /* profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", publisherid);
                editor.apply();
                ((FragmentActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();


            }
        });*/

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });


        readPosts();
        userInfo();
        isSaved(postid, fav_btn);


        return view;
    }



    private void makePhoneCall() {

        if (phone_number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                
            } else {
                String dial = "tel:" + phone_number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

            }

        } else {
            Toast.makeText(getContext(), "Phone Number isn't Valid", Toast.LENGTH_SHORT).show();
        }

    }

    private void readPosts() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);

                Glide.with((getContext())).load(post.getPostimage()).into(post_image);

                if (post.getName()!=null) {
                    post_name.setText(post.getName());
                }
                if (post.getAddress()!=null) {
                    post_address.setText(post.getAddress());
                }
                if (post.getNumber()!=null) {
                    post_number.setText(post.getNumber());
                }
                if (post.getGender()!=null) {
                    post_gender.setText(post.getGender());
                }
                if (post.getSeatno()!=null) {
                    post_seatno.setText(post.getSeatno());
                }
                if (post.getPrice()!=null) {
                    post_price.setText(post.getPrice());
                }
                if (post.getDescription()!=null) {
                    post_description.setText(post.getDescription());
                }
                if (post.getPublisher()!=null) {
                    publisherid=post.getPublisher();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void isSaved(final String postid, final ImageView imageView) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postid).exists()) {
                    imageView.setImageResource(R.drawable.ic_like_red);
                    imageView.setTag("saved");
                } else {
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    imageView.setTag("save");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (getContext() == null) {
                    return;
                }
                User user = dataSnapshot.getValue(User.class);
                if (user.getPhone() != null) {
                    phone_number = user.getPhone().trim();
                } else {
                    phone_number = "0000";
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}