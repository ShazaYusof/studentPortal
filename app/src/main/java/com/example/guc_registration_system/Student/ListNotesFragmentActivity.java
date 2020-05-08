package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.guc_registration_system.Model.AssignmentModel;
import com.example.guc_registration_system.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListNotesFragmentActivity extends Fragment {

    ListView fileList;
    DatabaseReference databaseReference;
    List<AssignmentModel> assignmentList;
    String courseID;

    public ListNotesFragmentActivity(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_list_notes_fragment, container, false);

        fileList = view.findViewById(R.id.listFile);
        assignmentList = new ArrayList<>();

        final Intent intent = getActivity().getIntent();
        String courseName = intent.getStringExtra("course_name");
        courseID = intent.getStringExtra("course_id");

        viewAllAssignments();

        fileList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AssignmentModel assignmentModel = assignmentList.get(position);

                Intent intent1 = new Intent();
                intent1.setData(Uri.parse(assignmentModel.getUrl()));
                startActivity(intent);
            }
        });

        return view;

    }

    private  void viewAllAssignments(){
        final Intent intent = getActivity().getIntent();
        //String courseName = intent.getStringExtra("course_name");
        final String courseID = intent.getStringExtra("course_id");

        Query query = FirebaseDatabase.getInstance().getReference("Notes").orderByChild("courseCode").equalTo(courseID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    AssignmentModel data = postSnapshot.getValue(AssignmentModel.class);
                    assignmentList.add(data);

                }
                String[] list = new String[assignmentList.size()];

                for(int i=0; i<list.length; i++){
                    list[i] = assignmentList.get(i).getFileName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
                fileList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
