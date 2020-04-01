package com.example.guc_registration_system.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guc_registration_system.ForgotPasswordActivity;
import com.example.guc_registration_system.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText et_Email;
    private EditText et_password;
    private Button btn_LoginUser;
    private TextView tv_info,tv_reg;
    private TextView tv_forgotPass;
    private int counter = 3;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_Email = findViewById(R.id.etLoginName);
        et_password = findViewById(R.id.etLoginPassword);
        tv_info = findViewById(R.id.tvCounter);
        btn_LoginUser = findViewById(R.id.btnLoginUser);
        tv_forgotPass = findViewById(R.id.tvForgotPassword);
        tv_reg = findViewById(R.id.tvReg);

        tv_info.setText("No of attempts remaining: 3");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user !=null)
        {
            finish();
            startActivity(new Intent(StudentLoginActivity.this,StudentHomepageActivity.class));
        }

        btn_LoginUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(enter()) {
                    validate(et_Email.getText().toString(), et_password.getText().toString());
                }
            }
        });

        tv_forgotPass.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View view){
                                                 Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );

        tv_reg.setOnClickListener(new View.OnClickListener(){
                                             @Override
                                             public void onClick(View view){
                                                 Intent intent = new Intent(getApplicationContext(), StudentRegisterActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
        );
    }

    private void validate(String userName, String userPassword)
    {
        progressDialog.setMessage("Please wait until you are verified!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    //Toast.makeText(LoginActivity.this,"Login Successful!!",Toast.LENGTH_SHORT).show();
                    checkEmailVerification();

                }
                else{
                    Toast.makeText(StudentLoginActivity.this,"Login Failed!!",Toast.LENGTH_SHORT).show();
                    counter--;
                    tv_info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if(counter ==0){
                        btn_LoginUser.setEnabled(false);
                    }
                }
            }
        });
    }
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            finish();
            startActivity(new Intent(StudentLoginActivity.this,StudentHomepageActivity.class));

        }
        else{
            Toast.makeText(this,"Please verify your email!!",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }

    private Boolean enter(){
        Boolean result = false;

        String userPassword = et_password.getText().toString();
        String userEmail = et_Email.getText().toString();


        if(userPassword.isEmpty() || userEmail.isEmpty() ){

            Toast.makeText(this,"Please enter user email and password!", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;

    }
}
