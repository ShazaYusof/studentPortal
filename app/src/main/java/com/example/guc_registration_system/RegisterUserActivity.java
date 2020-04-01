package com.example.guc_registration_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.guc_registration_system.Lecture.LectureRegisterActivity;
import com.example.guc_registration_system.Student.StudentRegisterActivity;

public class RegisterUserActivity extends AppCompatActivity {

    private ImageView iv_RegStudent,iv_RegLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        iv_RegStudent = findViewById(R.id.ivStudReg);
        iv_RegLecture = findViewById(R.id.ivLectReg);

        iv_RegStudent.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View view){
                                                 Intent intent = new Intent(getApplicationContext(), StudentRegisterActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );

        iv_RegLecture.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View view){
                                                 Intent intent = new Intent(getApplicationContext(), LectureRegisterActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );
    }
}
