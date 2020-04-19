package com.example.guc_registration_system.Lecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.Adapter.ListStudentAdapter;
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

public class ListStudentFragmentActivity extends Fragment {

    RecyclerView RecyclerStudent;
    ArrayList<StudentCourseModel> students;

    ListStudentAdapter adapter;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef,myRef,userRef;

    public ListStudentFragmentActivity(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.activity_list_student_fragment, container, false);


        RecyclerStudent = view.findViewById(R.id.RecyclerStudents);
        progressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        RecyclerStudent.setLayoutManager( new LinearLayoutManager(getContext()));
        RecyclerStudent.setHasFixedSize(true);

        Intent intent = getActivity().getIntent();

        String courseID = intent.getStringExtra("course_id");
        String courseName = intent.getStringExtra("course_name");

        dbRef = FirebaseDatabase.getInstance().getReference().child("StudentCourse");
        final Query queryRef = dbRef.orderByChild("courseID").equalTo(courseID);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students = new ArrayList<StudentCourseModel>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    StudentCourseModel p = dataSnapshot1.getValue(StudentCourseModel.class);
                    students.add(p);

                }
                adapter = new ListStudentAdapter(students);
                RecyclerStudent.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "There is an error occur. Please try again.", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }




}
