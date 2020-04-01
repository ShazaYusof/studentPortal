package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.Model.StudentModel;
import com.example.guc_registration_system.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterCourseActivity extends AppCompatActivity {

    private LinearLayout parentLinearLayout;

    TextView tvName, tvStudentID, tvAddress, tvProgramme, tvSemester;
    EditText etCourseID, etCourseName;
    Button btn_register, btn_enroll;
    String courseID, courseName,programmeName,semester;
    //public static final String programmeName = "programmeName", semester="semester";
    private FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    ArrayList<CourseModel> courseList;
    RegisterCourseAdapter adapter;
    ProgressBar progressBar;


    DatabaseReference dbCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);

        //parentLinearLayout = findViewById(R.id.parent_linear_layout);

        tvName = findViewById(R.id.studName);
        tvStudentID = findViewById(R.id.studID);
        tvAddress = findViewById(R.id.studAddress);
        tvProgramme = findViewById(R.id.studProgramme);
        tvSemester = findViewById(R.id.studSemester);
        btn_register = findViewById(R.id.btnRegister);
        btn_enroll = findViewById(R.id.btnEnroll);
        recyclerView = findViewById(R.id.RecyclerCourse);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentModel userProfile = dataSnapshot.getValue(StudentModel.class);
                tvName.setText(userProfile.getStudName());
                tvStudentID.setText(userProfile.getStudID());
                tvAddress.setText(userProfile.getStudAddress());
                tvProgramme.setText(userProfile.getProgramme());
                tvSemester.setText(userProfile.getStudSemester());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterCourseActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        programmeName = tvProgramme.getText().toString().trim();
        semester = tvSemester.getText().toString();


        dbCourse = FirebaseDatabase.getInstance().getReference().child("University");
        final Query query = FirebaseDatabase.getInstance().getReference("University").child("Faculty Of Computing And Creative Technology").child("Programme")
                .child(programmeName).equalTo(semester);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    if (data.child(programmeName).exists() && data.child(semester).exists()) {
                        courseList = new ArrayList<CourseModel>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            CourseModel p = dataSnapshot1.getValue(CourseModel.class);
                            courseList.add(p);

                        }
                        adapter = new RegisterCourseAdapter(courseList);
                        recyclerView.setAdapter(adapter);
                    }else{
                        Toast.makeText(RegisterCourseActivity.this, "TAKDOP", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisterCourseActivity.this, "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {

                    //DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Course").push();

                    CourseModel course = new CourseModel(courseID, courseName);
                    addCourse(course);
                }
            }
        });

    }

    private Boolean validate() {
        Boolean result = false;

        courseID = etCourseID.getText().toString();
        courseName = etCourseName.getText().toString();


        if (courseID.isEmpty() || courseName.isEmpty()) {
            Toast.makeText(this, "Please fill up courseID and course name!!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }


    private void addCourse(CourseModel course) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Course").push();

        String key = myref.getKey();
        //course.setCourseDatabaseID(key);

        //add post data to firebase database
        myref.setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(RegisterCourseActivity.this, "Your course registration are submitted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(RegisterCourseActivity.this, StudentHomepageActivity.class));
            }
        });

    }
}

//    public void onAddField(View v) {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View rowView = inflater.inflate(R.layout.add_course, null);
//        // Add the new row before the add field button.
//        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
//    }
//
//    public void onDelete(View v) {
//        parentLinearLayout.removeView((View) v.getParent());
//    }
//    public void clickDelete(View v) {
//        parentLinearLayout.removeView((View) v.getParent());
//    }

//    final Query query = FirebaseDatabase.getInstance().getReference("Faculty");
//
//            query.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            courseList= new ArrayList<CourseModel>();
//            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
//            {
//                CourseModel p = dataSnapshot1.getValue(CourseModel.class);
//                courseList.add(p);
//
//            }
//            adapter = new RegisterCourseAdapter(courseList);
//            recyclerView.setAdapter(adapter);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            Toast.makeText(RegisterCourseActivity.this, "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();
//
//        }
//    });


//    else if(tvSemester.equals("semester1/year2")){
//        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 2").child("Semester 1");
//
//
//    }
//            else if(tvSemester.equals("semester2/year2")){
//        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 2").child("Semester 2");
//
//
//    }
//            else if(tvSemester.equals("semester3/year2")){
//        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 2").child("Semester 3");
//
//
//    }
//            else if(tvSemester.equals("semester1/year3")){
//        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 3").child("Semester 1");
//
//
//    }
//            else if(tvSemester.equals("semester2/year3")){
//        Query query= FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 3").child("Semester 2");
//
//
//    }
//            else if(tvSemester.equals("semester3/year3"))
//    {
//        Query query = FirebaseDatabase.getInstance().getReference("Faculty").child("Faculty of Computing and Creative Technology").child("Programme")
//                .child("Bachelor of Computer Sciences (Hons)").child("Year 3").child("Semester 3");
//
//
//    }


