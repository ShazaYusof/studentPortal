package com.example.guc_registration_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.guc_registration_system.Lecture.LectureLoginActivity;
import com.example.guc_registration_system.Student.StudentLoginActivity;

public class LoginUserActivity extends AppCompatActivity {

    private ImageView iv_LogStudent,iv_LogLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        iv_LogStudent = findViewById(R.id.ivStudLogin);
        iv_LogLecture = findViewById(R.id.ivLectLogin);

        iv_LogStudent.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view){
                                             Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );

        iv_LogLecture.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View view){
                                                Intent intent = new Intent(getApplicationContext(), LectureLoginActivity.class);
                                                startActivity(intent);
                                            }
                                        }
        );
    }
}
