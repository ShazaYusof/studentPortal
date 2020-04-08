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
import com.example.guc_registration_system.Student.MyCourseActivity;

import java.util.ArrayList;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyViewHolder> {

    Context context;
    ArrayList<CourseModel> courseList;


    public MyCourseAdapter(ArrayList<CourseModel> courseList) {
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mycourses, parent, false);

        context = parent.getContext();
        return new MyCourseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.courseID.setText(courseList.get(position).getCourseID());
        holder.courseName.setText(courseList.get(position).getCourseName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course_id = courseList.get(position).getCourseID();
                String course_name = courseList.get(position).getCourseName();

                Intent intent = new Intent(context, MyCourseActivity.class);

                intent.putExtra("course_id", course_id);
                intent.putExtra("course_name", course_name);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView courseID,courseName;

        //Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            courseID = (TextView) itemView.findViewById(R.id.courseID);
            courseName = (TextView) itemView.findViewById(R.id.courseName);

        }

    }


}
