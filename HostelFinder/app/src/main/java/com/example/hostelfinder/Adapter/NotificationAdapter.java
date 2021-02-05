package com.example.hostelfinder.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hostelfinder.Fragments.PostDetailsFragment;
import com.example.hostelfinder.Fragments.ProfileFragment;
import com.example.hostelfinder.Model.Notification;
import com.example.hostelfinder.Model.User;
import com.example.hostelfinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.Viewholder>{
    private Context mContext;
    private List<Notification> mNotification;

    public NotificationAdapter() {
    }

    public NotificationAdapter(Context mContext, List<Notification> mNotification) {
        this.mContext = mContext;
        this.mNotification = mNotification;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.noti_item,parent,false);

        return new NotificationAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        Notification notification=mNotification.get(position);

        holder.txtname.setText(notification.getName());
        holder.occupation.setText(notification.getOccupation());
        holder.institute.setText(notification.getInstitute());
        holder.district.setText(notification.getDistrict());
        holder.phoneno.setText(notification.getPhone());
        holder.type.setText(notification.getRoom());
        holder.bed.setText(notification.getBed());
        holder.duration.setText(notification.getDuration());
        getUserInfo(holder.username,notification.getUserid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notification.isIspost()){

                    SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                    editor.putString("postid",notification.getPostid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment()).commit();

                }
                else {
                    SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                    editor.putString("profileid",notification.getUserid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mNotification.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public ImageView image_profile, post_image;
        public TextView username;
        public TextView txtname,occupation,institute,district,phoneno,type,bed,duration;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            username = itemView.findViewById(R.id.username);
            txtname = itemView.findViewById(R.id.name);
            occupation = itemView.findViewById(R.id.name);
            institute = itemView.findViewById(R.id.institute);
            district = itemView.findViewById(R.id.district);
            phoneno = itemView.findViewById(R.id.phone);
            type = itemView.findViewById(R.id.type);
            bed = itemView.findViewById(R.id.bed);
            duration = itemView.findViewById(R.id.duration);

        }
    }

    private void getUserInfo(final TextView username,String publisherid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(publisherid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
