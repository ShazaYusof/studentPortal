package com.example.guc_registration_system.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guc_registration_system.Model.LectureModel;
import com.example.guc_registration_system.Model.StudentModel;
import com.example.guc_registration_system.R;
import com.example.guc_registration_system.Student.StudentUpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uncopt.android.widget.text.justify.JustifiedEditText;

import java.io.IOException;

public class LectureUpdateProfileActivity extends AppCompatActivity {

    private EditText userPassword,userEmail,userPassport,userPhone,userID,userProgramme;
    private JustifiedEditText userAddress,userName;
    private ImageView updateProfilePic;
    private Button saveButton;
    private FirebaseAuth firebaseAuth;
    String id,name,email,password,phone,passport,address,programme;
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
        setContentView(R.layout.activity_lecture_update_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        userID = findViewById(R.id.etID);
        userName=findViewById(R.id.etName);
        userPassword=findViewById(R.id.etPassword);
        userEmail=findViewById(R.id.etEmail);
        userPhone=findViewById(R.id.etPhone);
        userPassport = findViewById(R.id.etPassport);
        userAddress = findViewById(R.id.etAddress);
        userProgramme = findViewById(R.id.etProgramme);
        saveButton=findViewById(R.id.btnSave);
        updateProfilePic = findViewById(R.id.ivProfileUpdate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DatabaseReference databaseReference = firebaseDatabase.getReference("Lectures").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LectureModel lectureProfile = dataSnapshot.getValue(LectureModel.class);
                userID.setText(lectureProfile.getLectID());
                userName.setText( lectureProfile.getLectName());
                userPassport.setText(lectureProfile.getLectPassport());
                userProgramme.setText(lectureProfile.getLectProgramme());
                userPhone.setText(lectureProfile.getLectPhone());
                userEmail.setText(lectureProfile.getLectEmail());
                userAddress.setText(lectureProfile.getLectAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LectureUpdateProfileActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
