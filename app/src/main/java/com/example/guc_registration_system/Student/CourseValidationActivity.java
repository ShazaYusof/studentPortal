package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.guc_registration_system.Adapter.CourseValidationAdapter;
import com.example.guc_registration_system.Adapter.MyCourseAdapter;
import com.example.guc_registration_system.Adapter.RegisterCourseAdapter;
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

public class CourseValidationActivity extends AppCompatActivity {

    TextView tvName, tvStudentID, tvFaculty, tvProgramme, tvSemester,tvRemainder;
    Button btn_print;

    String courseID, courseName,programmeName,semester;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    RecyclerView RecyclerBooking;
    ProgressBar progressBar;

    ArrayList<CourseModel> courseList;
    CourseValidationAdapter adapter;

    StudentModel myStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_validation);

        tvName = findViewById(R.id.studName);
        tvStudentID = findViewById(R.id.studID);
        tvFaculty = findViewById(R.id.studFaculty);
        tvProgramme = findViewById(R.id.studProgramme);
        tvSemester = findViewById(R.id.studSemester);
        btn_print = findViewById(R.id.btnPrint);
        RecyclerBooking = findViewById(R.id.RecyclerCourse);
        progressBar = findViewById(R.id.progressBar);
        tvRemainder = findViewById(R.id.tvNotes);

        RecyclerBooking.setHasFixedSize(true);
        RecyclerBooking.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btn_print.setVisibility(View.GONE);
        tvRemainder.setVisibility(View.VISIBLE);

        courseID = getIntent().getStringExtra("course_id");
        courseName = getIntent().getStringExtra("course_name");

        courseList = new ArrayList<CourseModel>();

        Log.d("TAG", "ab1 uid"+mAuth.getUid());

        DatabaseReference databaseReference = firebaseDatabase.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentModel userProfile = dataSnapshot.getValue(StudentModel.class);
                tvName.setText(userProfile.getStudName());
                tvStudentID.setText(userProfile.getStudID());
                tvFaculty.setText(userProfile.getFaculty());
                tvProgramme.setText(userProfile.getProgramme());
                tvSemester.setText(userProfile.getStudSemester());

                myStudent = dataSnapshot.getValue(StudentModel.class);

                Log.d("TAG", "ab1 student program : "+myStudent.getProgramme());

                courseID = getIntent().getStringExtra("course_id");
                courseName = getIntent().getStringExtra("course_name");

                courseList = new ArrayList<CourseModel>();

                Log.d("TAG", "ab1 uid"+mAuth.getUid());


                dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCourse");
                final Query queryRef = FirebaseDatabase.getInstance().getReference("StudentCourse").orderByChild("studentID").equalTo(mAuth.getUid());

                queryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists()){
                            tvRemainder.setVisibility(View.GONE);


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
                                        tvRemainder.setVisibility(View.GONE);
                                        btn_print.setVisibility(View.VISIBLE);
                                        adapter = new CourseValidationAdapter(courseList);
                                        RecyclerBooking.setAdapter(adapter);


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CourseValidationActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
