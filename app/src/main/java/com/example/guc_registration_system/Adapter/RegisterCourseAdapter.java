package com.example.guc_registration_system.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guc_registration_system.Model.CourseModel;
import com.example.guc_registration_system.R;

import java.util.ArrayList;

public class RegisterCourseAdapter extends RecyclerView.Adapter<RegisterCourseAdapter.CourseViewHolder> {

    Context context;
    ArrayList<CourseModel> courseList;
    private OnListListener onListListener;

    public RegisterCourseAdapter(ArrayList<CourseModel> courseList, OnListListener onListListener){
        this.courseList = courseList;
        this.onListListener = onListListener;
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
        holder.textViewCreditValue.setText(courseList.get(position).getCreditValue());

    }
    @Override
    public int getItemCount() {

        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewCourseID, textViewCourseName,textViewCreditValue;
        ToggleButton toggleButton;


        public CourseViewHolder( View itemView) {
            super(itemView);
            textViewCourseID = itemView.findViewById(R.id.courseID);
            textViewCourseName= itemView.findViewById(R.id.courseName);
            toggleButton = itemView.findViewById(R.id.btnEnroll);
            textViewCreditValue = itemView.findViewById(R.id.creditValue);
            toggleButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            boolean on = ((ToggleButton)v).isChecked();

            if(on) {
                onListListener.onEnrollCLick(getAdapterPosition());
            } else {
                onListListener.onCancelClick(getAdapterPosition());
            }

        }
    }

    public interface OnListListener {
        void onEnrollCLick (int position);
        void onCancelClick (int position);
    }


}
