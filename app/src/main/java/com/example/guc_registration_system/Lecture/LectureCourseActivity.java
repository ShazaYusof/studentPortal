package com.example.guc_registration_system.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guc_registration_system.Adapter.LecturerCourseAdapter;
import com.example.guc_registration_system.Adapter.ListStudentAdapter;
import com.example.guc_registration_system.Model.LecturerCourseModel;
import com.example.guc_registration_system.Model.StudentCourseModel;
import com.example.guc_registration_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LectureCourseActivity extends AppCompatActivity {

    RecyclerView RecyclerCourses;
    ArrayList<LecturerCourseModel> courses;

    LecturerCourseAdapter adapter;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef,myRef,userRef;

    String courseName,courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_course);

        RecyclerCourses = findViewById(R.id.RecyclerCourses);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        RecyclerCourses.setLayoutManager( new LinearLayoutManager(this));
        RecyclerCourses.setHasFixedSize(true);

        courseID = getIntent().getStringExtra("course_id");
        courseName = getIntent().getStringExtra("course_name");

//        dbRef = firebaseDatabase.getReference("Lectures").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LectCourses");
//        final Query queryRef = dbRef.orderByChild("courseID").equalTo(courseID);

        final Query queryRef = FirebaseDatabase.getInstance().getReference("Lectures").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LectCourses");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courses = new ArrayList<LecturerCourseModel>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    LecturerCourseModel p = dataSnapshot1.getValue(LecturerCourseModel.class);
                    courses.add(p);


                }
                adapter = new LecturerCourseAdapter(courses);
                RecyclerCourses.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LectureCourseActivity.this, "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
