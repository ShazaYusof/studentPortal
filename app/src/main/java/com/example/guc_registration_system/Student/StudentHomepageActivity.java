package com.example.guc_registration_system.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.guc_registration_system.MainActivity;
import com.example.guc_registration_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentHomepageActivity extends AppCompatActivity {

    private Button btnProfile,btnRegister,btnCourse,btnSchedule;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);

        btnProfile = findViewById(R.id.buttonProfile);
        btnRegister = findViewById(R.id.registerCourse);
        btnSchedule = findViewById(R.id.buttonSchedule);
        btnCourse = findViewById(R.id.buttonSearch);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnProfile.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View view){
                                               Intent intent = new Intent(getApplicationContext(), StudentProfileActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );

        btnRegister.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View view){
                                               Intent intent = new Intent(getApplicationContext(), RegisterCourseActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );

//        btnSchedule.setOnClickListener(new View.OnClickListener(){
//                                           @Override
//                                           public void onClick(View view){
//                                               Intent intent = new Intent(getApplicationContext(), RegisterHomestayActivity.class);
//                                               startActivity(intent);
//                                           }
//                                       }
//        );
//
        btnCourse.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View view){
                                               Intent intent = new Intent(getApplicationContext(), MyCourseActivity.class);
                                               startActivity(intent);
                                           }
                                       }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.btnLogout:
                firebaseAuth.signOut();
                startActivity(new Intent(StudentHomepageActivity.this, MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
