package com.example.guc_registration_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.guc_registration_system.Lecture.LectureLoginActivity;
import com.example.guc_registration_system.Student.StudentLoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView btn_student,btn_lecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_student = findViewById(R.id.btnStudent);
        btn_lecture = findViewById(R.id.btnLecture);



        btn_student.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view){
                                            Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
        );
        btn_lecture.setOnClickListener(new View.OnClickListener(){
                                        @Override
                                        public void onClick(View view){
                                            Intent intent = new Intent(getApplicationContext(), LectureLoginActivity.class);
                                            startActivity(intent);
                                        }
                                    }
        );
    }
}
