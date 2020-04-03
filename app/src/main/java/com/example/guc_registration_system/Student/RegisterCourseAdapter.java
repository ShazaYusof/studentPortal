package com.example.guc_registration_system.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterCourseAdapter extends RecyclerView.Adapter<RegisterCourseAdapter.CourseViewHolder> {

    Context context;
    ArrayList<CourseModel> courseList;

    public RegisterCourseAdapter(ArrayList<CourseModel> courseList){
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_course, parent, false);
        context = parent.getContext();
        return new RegisterCourseAdapter.CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, final int position) {
        //CourseModel course = courseList.get(position);
        holder.textViewCourseID.setText(courseList.get(position).getCourseID());
        holder.textViewCourseName.setText(courseList.get(position).getCourseName());

    }
    @Override
    public int getItemCount() {

        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCourseID, textViewCourseName;


        public CourseViewHolder( View itemView) {
            super(itemView);

            textViewCourseID = itemView.findViewById(R.id.courseID);
            textViewCourseName= itemView.findViewById(R.id.courseName);
        }
    }


}
