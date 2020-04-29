package com.example.guc_registration_system.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.R;
import com.example.guc_registration_system.Student.ListAssignmentActivity;

import java.util.ArrayList;

public class CourseValidationAdapter extends RecyclerView.Adapter<CourseValidationAdapter.MyViewHolder> {

    Context context;
    ArrayList<CourseModel> courseList;


    public CourseValidationAdapter(ArrayList<CourseModel> courseList){
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseValidationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_course_validation, parent, false);

        context = parent.getContext();
        return new CourseValidationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseValidationAdapter.MyViewHolder holder, final int position) {

        holder.courseID.setText(courseList.get(position).getCourseID());
        holder.courseName.setText(courseList.get(position).getCourseName());
        holder.creditValue.setText(courseList.get(position).getCreditValue());

    }

    @Override
    public int getItemCount()

    {
        return courseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID,courseName,creditValue;

        //Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            courseID = (TextView) itemView.findViewById(R.id.courseID);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            creditValue = (TextView) itemView.findViewById(R.id.creditValue);

        }

    }
}
