package com.example.guc_registration_system.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guc_registration_system.Model.StudentCourseModel;
import com.example.guc_registration_system.R;

import java.util.ArrayList;

public class ListStudentAdapter extends  RecyclerView.Adapter<ListStudentAdapter.MyViewHolder>{

    Context context;
    ArrayList<StudentCourseModel> listStudents;


    public ListStudentAdapter(ArrayList<StudentCourseModel> listStudents) {
        //this.context = context;
        this.listStudents = listStudents;
    }

    @NonNull
    @Override
    public ListStudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_students, parent, false);

        context = parent.getContext();
        return new ListStudentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStudentAdapter.MyViewHolder holder, final int position) {

        holder.studID.setText(listStudents.get(position).getStudID());
        holder.studName.setText(listStudents.get(position).getStudentName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String studentId = listStudents.get(position).getStudID();
//                String studentName = listStudents.get(position).getStudentName();
//                String courseID = listStudents.get(position).getCourseID();
//                String semester  = listStudents.get(position).getSemester();
//
//
//            }
    }

    @Override
    public int getItemCount() {
        return listStudents.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studID, studName;

        //Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            studID = (TextView) itemView.findViewById(R.id.studID);
            studName = (TextView) itemView.findViewById(R.id.studName);

        }

    }
}
