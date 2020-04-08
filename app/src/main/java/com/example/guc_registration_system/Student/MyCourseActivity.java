package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Adapter.MyCourseAdapter;
import com.example.guc_registration_system.Model.CourseModel;
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
    MyCourseAdapter adapter;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

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

        man.setVisibility(View.VISIBLE);
        txtMoment.setVisibility(View.VISIBLE);

        courseID = getIntent().getStringExtra("course_id");
        courseName = getIntent().getStringExtra("course_name");

        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCourse");
        final Query queryRef = FirebaseDatabase.getInstance().getReference("StudentCourse").child(mAuth.getUid()).child("semester2");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseList = new ArrayList<CourseModel>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    CourseModel p = dataSnapshot1.getValue(CourseModel.class);
                    courseList.add(p);
                    man.setVisibility(View.GONE);
                    txtMoment.setVisibility(View.GONE);
                }
                adapter = new MyCourseAdapter(courseList);
                RecyclerBooking.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyCourseActivity.this, "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
