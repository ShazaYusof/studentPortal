package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Adapter.MyCourseAdapter;
import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.Model.StudentCourseModel;
import com.example.guc_registration_system.Model.StudentModel;
import com.example.guc_registration_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCourseActivity extends AppCompatActivity {

    RecyclerView RecyclerBooking;
    TextView txtMoment;
    ImageView man;
    ArrayList<CourseModel> courseList;
    ArrayList<StudentCourseModel> studentCourseList;
    MyCourseAdapter adapter;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    StudentModel myStudent;

    String courseID,courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerBooking = findViewById(R.id.RecyclerBooking);
        progressBar = findViewById(R.id.progressBar);
        man = findViewById(R.id.sadMan);
        txtMoment = findViewById(R.id.moment);

        mAuth = FirebaseAuth.getInstance();

        RecyclerBooking.setHasFixedSize(true);
        RecyclerBooking.setLayoutManager( new LinearLayoutManager(this));

        man.setVisibility(View.GONE);
        txtMoment.setVisibility(View.GONE);

        courseID = getIntent().getStringExtra("course_id");
        courseName = getIntent().getStringExtra("course_name");

        courseList = new ArrayList<CourseModel>();

        Log.d("TAG", "ab1 uid"+mAuth.getUid());

        DatabaseReference studentRef = FirebaseDatabase.getInstance().getReference("Students").child(mAuth.getUid());
        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myStudent = dataSnapshot.getValue(StudentModel.class);

                Log.d("TAG", "ab1 student program : "+myStudent.getProgramme());


                dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCourse");
                final Query queryRef = FirebaseDatabase.getInstance().getReference("StudentCourse").orderByChild("studentID").equalTo(mAuth.getUid());

                queryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists()){
                            man.setVisibility(View.VISIBLE);
                            txtMoment.setVisibility(View.VISIBLE);
                        }

                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                            StudentCourseModel sc = data.getValue(StudentCourseModel.class);
                            Log.d("TAG", "ab1 sc semester : "+sc.getSemester());
                            Log.d("TAG", "ab1 sc course : "+sc.getCourseID());

                            if(sc.getSemester().equals(myStudent.getStudSemester())) {

                                Log.d("TAG", "ab1 same semester : "+myStudent.getStudSemester());

                                final Query query = FirebaseDatabase.getInstance().getReference("Course").child(sc.getCourseID());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Log.d("TAG", "ab1 data : "+dataSnapshot.toString());
                                        CourseModel courseModel = dataSnapshot.getValue(CourseModel.class);
                                        Log.d("TAG", "ab1 course model name "+courseModel.getCourseName());
                                        courseModel.setCourseID(dataSnapshot.getKey());
                                        courseList.add(courseModel);
                                        man.setVisibility(View.GONE);
                                        txtMoment.setVisibility(View.GONE);
                                        adapter = new MyCourseAdapter(courseList);
                                        RecyclerBooking.setAdapter(adapter);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }






//                    CourseModel p = child.getValue(CourseModel.class);
//                    p.setCourseID(child.getKey());
//                    courseList.add(p);
//                    man.setVisibility(View.GONE);
//                    txtMoment.setVisibility(View.GONE);
//                adapter = new MyCourseAdapter(courseList);
//                RecyclerBooking.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MyCourseActivity.this, "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
