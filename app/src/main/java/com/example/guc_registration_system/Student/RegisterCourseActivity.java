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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import java.util.Arrays;
import java.util.List;

public class RegisterCourseActivity extends AppCompatActivity implements RegisterCourseAdapter.OnListListener{

    private LinearLayout parentLinearLayout;

    TextView tvName, tvStudentID, tvFaculty, tvProgramme, tvSemester,tvRemainder;
    EditText etCourseID, etCourseName;
    Button btn_register;
    ToggleButton btn_enroll;
    String courseID, courseName,programmeName,semester;
    //public static final String programmeName = "programmeName", semester="semester";
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    RecyclerView recyclerView;
    RegisterCourseAdapter adapter;
    ProgressBar progressBar;

    ArrayList<CourseModel> courseList = new ArrayList<CourseModel>();


    DatabaseReference dbCourse;

    String mySemester,myProgram,myFaculty,myStudentID;
    boolean exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_course);

        tvName = findViewById(R.id.studName);
        tvStudentID = findViewById(R.id.studID);
        tvFaculty = findViewById(R.id.studFaculty);
        tvProgramme = findViewById(R.id.studProgramme);
        tvSemester = findViewById(R.id.studSemester);
        btn_register = findViewById(R.id.btnRegister);
        btn_enroll = findViewById(R.id.btnEnroll);
        recyclerView = findViewById(R.id.RecyclerCourse);
        progressBar = findViewById(R.id.progressBar);
        tvRemainder = findViewById(R.id.tvNotes);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firebaseDatabase = FirebaseDatabase.getInstance();

        tvRemainder.setVisibility(View.GONE);


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

                mySemester = userProfile.getStudSemester();
                myFaculty = userProfile.getFaculty();
                myProgram = userProfile.getProgramme();
                myStudentID = userProfile.getStudID();

                Toast.makeText(getApplicationContext(), "prog "+userProfile.getProgramme()+ "year "+userProfile.getStudSemester(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "prog : "+userProfile.getProgramme());
                Log.d("TAG", "sem : "+userProfile.getStudSemester());

                final Query queryCourse = FirebaseDatabase.getInstance().getReference("StudentCourse").orderByChild("semester").equalTo(userProfile.getStudSemester());
                queryCourse.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(childSnapshot.child("studID").getValue().toString().equals(myStudentID)){
                                    exist = true;
                                }
                                final CourseModel courseModel = childSnapshot.getValue(CourseModel.class);


                            }

                            if(exist) {
                                tvRemainder.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                btn_register.setVisibility(View.GONE);
                            }
                            else {

                                final Query query = FirebaseDatabase.getInstance().getReference("University").child(myFaculty).child("Programme")
                                        .child(myProgram).orderByChild("courseYear").equalTo(mySemester);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        //Log.d("TAG", "data : "+dataSnapshot.toString());
                                        for(DataSnapshot data: dataSnapshot.getChildren()) {

                                            Log.d("TAG", "each data : " + data.toString());

                                            String courseID = data.child("courseID").getValue().toString();
                                            String courseName =  data.child("courseName").getValue().toString();
                                            int creditValue = Integer.parseInt(data.child("creditValue").getValue().toString());
                                            //String studId  = data.child("studId").getValue().toString();

                                            Log.d("TAG", "courseID : "+data.toString());
                                            Log.d("TAG", "courseID : "+data.child("courseID").getValue().toString());
                                            Log.d("TAG", "courseName : "+data.child("courseName").getValue().toString());

                                            courseList.add(new CourseModel(courseID,courseName,null,null,null,creditValue));
                                        }

                                        adapter = new RegisterCourseAdapter(courseList, RegisterCourseActivity.this);
                                        recyclerView.setAdapter(adapter);
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

                    }
                });



                mAuth = FirebaseAuth.getInstance();


            }

            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterCourseActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvRemainder.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(RegisterCourseActivity.this,"Your course for" + semester + "is registered!", Toast.LENGTH_SHORT).show();
                //finish();
                //startActivity(new Intent(RegisterCourseActivity.this,StudentHomepageActivity.class));

            }
        });

    }

    @Override
    public void onEnrollCLick(int position) {

        String studId =  tvStudentID.getText().toString();
        String studName = tvName.getText().toString();


        Toast.makeText(this, "courseID : "+courseList.get(position).courseName, Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //List<String> courseYear = Arrays.asList(mySemester.split("/"));
        List<String> courseYear = Arrays.asList(mySemester);
        //Log.d("TAG", "test sem :"+courseYear.get(0)+"test year : "+courseYear.get(1));
        Log.d("TAG", "test sem :"+courseYear.get(0));
        //String year = courseYear.get(0);
        String sem = courseYear.get(0);

        StudentCourseModel studentCourseModel = new StudentCourseModel(null,FirebaseAuth.getInstance().getCurrentUser().getUid(),courseList.get(position).getCourseID(),mySemester,studId,studName);
        DatabaseReference myref = database.getReference("StudentCourse");
        myref.push().setValue(studentCourseModel);
    }

    @Override
    public void onCancelClick(int position) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<String> courseYear = Arrays.asList(mySemester);
        //String year = courseYear.get(0);
        String sem = courseYear.get(0);
        DatabaseReference myref = database.getReference("StudentCourse").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(sem).child(courseList.get(position).getCourseID());
        myref.removeValue();
    }


}