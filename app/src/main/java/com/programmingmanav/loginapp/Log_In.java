package com.programmingmanav.loginapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Log_In extends AppCompatActivity {

    protected ImageView iLB , iiLB;
    protected TextInputLayout Email,Pwd;
    protected ProgressBar bar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialisation
        iLB = (ImageView) findViewById(R.id.iLB);
        iiLB = (ImageView) findViewById(R.id.iiLB);
        Email = (TextInputLayout)findViewById(R.id.email_login);
        Pwd = (TextInputLayout)findViewById(R.id.pwd_login);
        bar = (ProgressBar)findViewById(R.id.Login_pbar);
        AnimateLBackground();

        mAuth = FirebaseAuth.getInstance();

    }

    protected void AnimateLBackground(){
        iLB.animate().rotationX(360).setDuration(250);
        iiLB.animate().rotationX(360).setDuration(250);
    }

    public void login(View view) {

        String email = Email.getEditText().getText().toString();
        String password = Pwd.getEditText().getText().toString();

        if(email.isEmpty()){
            Email.setError("Required");
        }else{
            if(password.isEmpty()){
                Pwd.setError("Required");
            }else{
                bar.setAlpha(1);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Log_In.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    bar.setAlpha(0);
                                    Email.getEditText().setText("");
                                    Pwd.getEditText().setText("");
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        Intent loginIntent = new Intent(getApplicationContext() , HomePage.class);
                                        startActivity(loginIntent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                                        Intent loginIntent = new Intent(getApplicationContext() , Log_In.class);
                                        startActivity(loginIntent);
                                        finish();
                                    }
                                } else {
                                    bar.setAlpha(0);
                                    Email.getEditText().setText("");
                                    Pwd.getEditText().setText("");
                                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        }



    }

    public void signup(View view) {
        startActivity(new Intent(Log_In.this , Sign_Up.class));
        finish();
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(Log_In.this , ForgetPassword.class));
    }

    public void loginWithGoogle(View view) {
        Intent intent = new Intent(Log_In.this , googleSignIn.class);
        startActivity(intent);
    }
}