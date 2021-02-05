package com.example.hostelfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {
    private Spinner spinner;
    private String cateString;
    String[] categoryName;
    private boolean firstclick = true;

    private Uri mImageUri;
    String miUrlOk = "";
    private StorageTask uploadTask;
    StorageReference storageRef;
    ImageView close, image_added;
    TextView post;
    EditText htl_name, htl_address, htl_number, htl_gender, htl_price,htl_email, htl_description, htl_seat_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        post = findViewById(R.id.post);
        image_added = findViewById(R.id.image_added);
        htl_name = findViewById(R.id.hotel_name);
        htl_address = findViewById(R.id.hotel_address);
        htl_number = findViewById(R.id.hotel_number);
        htl_gender = findViewById(R.id.hotel_gender);
        htl_seat_no = findViewById(R.id.hotel_seat_no);
        htl_price = findViewById(R.id.hotel_price);
        htl_email = findViewById(R.id.hotel_email);
        htl_description = findViewById(R.id.hotel_description);

        storageRef = FirebaseStorage.getInstance().getReference("Posts");

       // categoryName = getResources().getStringArray(R.array.category_name);
        //spinner = findViewById(R.id.spinnerid);


       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_sample_view, R.id.category_text_id, categoryName);
//        spinner.setAdapter(adapter);

       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (firstclick == true) {
                    firstclick = false;
                } else {
                    cateString = categoryName[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this, AdminMainActivity.class));
                finish();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                if (sharedPreferences.contains("category")) {
                    cateString = sharedPreferences.getString("category", "Data Not Found");
                }

                uploadImage_10();
            }
        });
// start picker to get image for cropping and then use the image in cropping activity
        CropImage.activity()
                .setAspectRatio(1, 1)
                .start(PostActivity.this);


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage_10() {


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Posting");
        pd.show();

        if (mImageUri != null) {
            final StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            uploadTask = fileReference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = (Uri) task.getResult();
                        miUrlOk = downloadUri.toString();

                        String gender = htl_gender.getText().toString().toUpperCase();
                        DatabaseReference reference_gender = FirebaseDatabase.getInstance().getReference(gender);
                       // DatabaseReference reference = FirebaseDatabase.getInstance().getReference(cateString);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

                        String postid = reference.push().getKey();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("postid", postid);
                        hashMap.put("postimage", miUrlOk);
                        hashMap.put("name", htl_name.getText().toString());
                        hashMap.put("address", htl_address.getText().toString().toUpperCase());
                        hashMap.put("number", htl_number.getText().toString());
                        hashMap.put("email", htl_email.getText().toString());
                        hashMap.put("seatno", htl_seat_no.getText().toString());
                        hashMap.put("price", htl_price.getText().toString());
                        hashMap.put("description", htl_description.getText().toString());
                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(postid).setValue(hashMap);
                        //reference_Post.child(postid).setValue(hashMap);
                        reference_gender.child(postid).setValue(hashMap);

                        pd.dismiss();

                        startActivity(new Intent(PostActivity.this, AdminMainActivity.class));
                        finish();

                    } else {
                        Toast.makeText(PostActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            Toast.makeText(PostActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            image_added.setImageURI(mImageUri);
        } else {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
