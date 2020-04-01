package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Model.StudentModel;
import com.example.guc_registration_system.R;
import com.example.guc_registration_system.UpdatePasswordActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentProfileActivity extends AppCompatActivity {

    private CircleImageView profilePic;
    private TextView profileID,profileName,profilePhone,changePassword,profileIC,profileFaculty,profileProgramme,profileSemester;
    private JustifiedTextView profileEmail,profileAddress;
    private Button edit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        profilePic = findViewById(R.id.ivProfilePic);
        profileName = findViewById(R.id.tvProfileName);
        profilePhone = findViewById(R.id.tvProfilePhone);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profileAddress = findViewById(R.id.tvProfileAddress);
        profileID = findViewById(R.id.tvProfileID);
        profileIC = findViewById(R.id.tvProfileIC);
        profileFaculty = findViewById(R.id.tvProfileFaculty);
        profileProgramme = findViewById(R.id.tvProfileProgramme);
        profileSemester = findViewById(R.id.tvProfileSemester);
        edit = findViewById(R.id.btnEdit);
        changePassword = findViewById(R.id.tvChangePassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseReference databaseReference = firebaseDatabase.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view){
                                        Intent intent = new Intent(getApplicationContext(), StudentUpdateProfileActivity.class);
                                        startActivity(intent);
                                    }
                                }
        );

        changePassword.setOnClickListener(new View.OnClickListener(){
                                              @Override
                                              public void onClick(View view){
                                                  Intent intent = new Intent(getApplicationContext(), UpdatePasswordActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentModel studentProfile = dataSnapshot.getValue(StudentModel.class);
                profileID.setText(studentProfile.getStudID());
                profileName.setText( studentProfile.getStudName());
                profileIC.setText(studentProfile.getStudPassportNo());
                profileFaculty.setText(studentProfile.getFaculty());
                profileProgramme.setText(studentProfile.getProgramme());
                profileSemester.setText(studentProfile.getStudSemester());
                profilePhone.setText(studentProfile.getStudPhone());
                profileEmail.setText(studentProfile.getStudEmail());
                profileAddress.setText(studentProfile.getStudAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StudentProfileActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
