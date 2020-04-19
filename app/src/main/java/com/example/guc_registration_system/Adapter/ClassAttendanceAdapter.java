package com.example.guc_registration_system.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.guc_registration_system.Lecture.AttendanceStudentFragmentActivity;
import com.example.guc_registration_system.Lecture.ListStudentFragmentActivity;

public class ClassAttendanceAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public ClassAttendanceAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ListStudentFragmentActivity tab1 = new ListStudentFragmentActivity();
                return tab1;
            case 1:
                AttendanceStudentFragmentActivity tab2 = new AttendanceStudentFragmentActivity();
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
