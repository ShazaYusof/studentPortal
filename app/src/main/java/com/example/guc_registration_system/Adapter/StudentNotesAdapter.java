package com.example.guc_registration_system.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.guc_registration_system.Lecture.AttendanceStudentFragmentActivity;
import com.example.guc_registration_system.Lecture.ListStudentFragmentActivity;
import com.example.guc_registration_system.Student.ListAssignmentFragmentActivity;
import com.example.guc_registration_system.Student.ListNotesFragmentActivity;

public class StudentNotesAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public StudentNotesAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ListNotesFragmentActivity tab1 = new ListNotesFragmentActivity();
                return tab1;
            case 1:
                ListAssignmentFragmentActivity tab2 = new ListAssignmentFragmentActivity();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
