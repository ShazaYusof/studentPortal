package com.example.guc_registration_system.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.guc_registration_system.Model.LecturerModel;
import com.example.guc_registration_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectureRegisterActivity extends AppCompatActivity {

    private EditText userPassword,userEmail,userPassport,userPhone,userID,userAddress,userName;
    private Spinner userProgramme;
    private Button SignupButton;
    private FirebaseAuth firebaseAuth;
    String id,name,email,password,phone,passport,address,programme;
    private FirebaseStorage firebaseStorage;
    private ImageView userProfilePic;
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
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_register);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerFaculty();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(LectureRegisterActivity.this, "Registration Failed!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void setupUIViews(){

        userID = findViewById(R.id.etID);
        userName=findViewById(R.id.etName);
        userPassword=findViewById(R.id.etPassword);
        userEmail=findViewById(R.id.etEmail);
        userPhone=findViewById(R.id.etPhone);
        userPassport = findViewById(R.id.etIC);
        userAddress = findViewById(R.id.etAddress);
        userProgramme = findViewById(R.id.spinnerProgramme);
        SignupButton=findViewById(R.id.btnReg);
        userProfilePic = findViewById(R.id.ivProfile);

    }

    private Boolean validate(){
        Boolean result = false;

        id = userID.getText().toString();
        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone = userPhone.getText().toString();
        passport = userPassport.getText().toString();
        address = userAddress.getText().toString();
        programme = userProgramme.getSelectedItem().toString();



        if(id.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || passport.isEmpty() || address.isEmpty() ||
                programme.isEmpty() || imagePath == null){

            Toast.makeText(this,"Please enter all the details!!", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;

    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(LectureRegisterActivity.this,"Successfully Registered,Verification email sent!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        //startActivity(new Intent(SignupActivity.this,HomeActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LectureRegisterActivity.this,"Verification email has'nt been sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        DatabaseReference myRef =FirebaseDatabase.getInstance().getReference("Lectures").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Lecture Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LectureRegisterActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(LectureRegisterActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });
        LecturerModel lectureProfile = new LecturerModel(id,name,programme,passport,email,phone,address,FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.setValue(lectureProfile);

    }

    private  void  spinnerFaculty() {

        List<String> listStatus = new ArrayList<>();
        listStatus.add("Faculty Of Computing & Creative Technology");
        listStatus.add("Faculty Of Geospatial & Real Estate");
        listStatus.add("Faculty Of Architecture & Environmental Design");
        listStatus.add("Faculty Of Hospitality & Lifestyle");
        listStatus.add("Faculty Of Bioeconomic & Health Sciences");
        listStatus.add("Faculty Of Human Development");
        listStatus.add("IQRA Business School & PostGraduate");
        listStatus.add("Centre Of Foundation");


        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listStatus);

        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        userProgramme.setAdapter(adapterStatus);

    }
}
