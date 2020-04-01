package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Model.StudentModel;
import com.example.guc_registration_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedEditText;

import java.io.IOException;

public class StudentUpdateProfileActivity extends AppCompatActivity {

    private EditText userEmail,userPassport,userPhone,userID,userSemester;
    private TextView userFaculty,userProgramme;
    private JustifiedEditText userAddress,userName;
    private ImageView updateProfilePic;
    private Button saveButton;
    private FirebaseAuth firebaseAuth;
    String id,name,email,password,phone,passport,address,programme,semester;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        userID = findViewById(R.id.etID);
        userName=findViewById(R.id.etName);
        userEmail=findViewById(R.id.etEmail);
        userPhone=findViewById(R.id.etPhone);
        userPassport = findViewById(R.id.etPassport);
        userAddress = findViewById(R.id.etAddress);
        userFaculty = findViewById(R.id.etFaculty);
        userProgramme = findViewById(R.id.etProgramme);
        userSemester = findViewById(R.id.etSemester);
        saveButton=findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference databaseReference = firebaseDatabase.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentModel studentProfile = dataSnapshot.getValue(StudentModel.class);
                userID.setText(studentProfile.getStudID());
                userName.setText( studentProfile.getStudName());
                userPassport.setText(studentProfile.getStudPassportNo());
                userFaculty.setText(studentProfile.getFaculty());
                userProgramme.setText(studentProfile.getProgramme());
                userSemester.setText(studentProfile.getStudSemester());
                userPhone.setText(studentProfile.getStudPhone());
                userEmail.setText(studentProfile.getStudEmail());
                userAddress.setText(studentProfile.getStudAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StudentUpdateProfileActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateProfilePic);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = userID.getText().toString();
                String name = userName.getText().toString();
                String email = userEmail.getText().toString();
                String passport = userPassport.getText().toString();
                String phone = userPhone.getText().toString();
                String address = userAddress.getText().toString();
                String programme = userProgramme.getText().toString();
                String semester = userSemester.getText().toString();
                String faculty = userFaculty.getText().toString();


                StudentModel studentProfile = new StudentModel(id,name,email,passport,phone,address,faculty,programme,semester,FirebaseAuth.getInstance().getCurrentUser().getUid());

                final DatabaseReference databaseReference = firebaseDatabase.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                databaseReference.setValue(studentProfile);

                StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(StudentUpdateProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(StudentUpdateProfileActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(StudentUpdateProfileActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(StudentUpdateProfileActivity.this, StudentProfileActivity.class));

            }

        });
    }
}
