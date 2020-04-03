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
import android.widget.ToggleButton;

import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.Model.StudentCourseRegisterModel;
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
    Button btn_register;
    ToggleButton btn_enroll;
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

                Toast.makeText(getApplicationContext(), "prog "+userProfile.getProgramme()+ "year "+userProfile.getStudSemester(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "prog : "+userProfile.getProgramme());
                Log.d("TAG", "sem : "+userProfile.getStudSemester());

                final Query query = FirebaseDatabase.getInstance().getReference("University").child("Faculty Of Computing And Creative Technology").child("Programme")
                        .child(userProfile.getProgramme()).orderByChild("courseYear").equalTo(userProfile.getStudSemester());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        courseList = new ArrayList<CourseModel>();
                        //Log.d("TAG", "data : "+dataSnapshot.toString());

                        for(DataSnapshot data: dataSnapshot.getChildren()) {

                            Log.d("TAG", "each data : " + data.toString());

                            String courseID = data.child("courseID").getValue().toString();
                            String courseName =  data.child("courseName").getValue().toString();

                            Log.d("TAG", "courseID : "+data.toString());
                            Log.d("TAG", "courseID : "+data.child("courseID").getValue().toString());
                            Log.d("TAG", "courseName : "+data.child("courseName").getValue().toString());

                            courseList.add(new CourseModel(courseID,courseName));
                        }

                        adapter = new RegisterCourseAdapter(courseList);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                    }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterCourseActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    semester = tvSemester.getText().toString();
                    programmeName = tvProgramme.getText().toString();


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyCourse").child(semester);

                    StudentCourseRegisterModel course = new StudentCourseRegisterModel(courseName,courseID,semester,programmeName);
                    myref.setValue(course);
                    Toast.makeText(RegisterCourseActivity.this,"Your course for" + semester + "is registered!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(RegisterCourseActivity.this,StudentHomepageActivity.class));

            }
        });

    }


//    private void addCourse(StudentCourseRegisterModel course) {
//
//        semester = tvSemester.getText().toString();
//        programmeName = tvProgramme.getText().toString();
//        //courseID = data.child("courseID").getValue().toString();
//        //courseName =  data.child("courseName").getValue().toString();
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myref = database.getReference("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyCourse").child(semester);
//
//        String key = myref.getKey();
//        //course.setCourseDatabaseID(key);
//
//        //add post data to firebase database
//        myref.setValue(course).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                Toast.makeText(RegisterCourseActivity.this, "Your course registration are submitted", Toast.LENGTH_SHORT).show();
//                finish();
//                startActivity(new Intent(RegisterCourseActivity.this, StudentHomepageActivity.class));
//            }
//        });
//
//    }
}




