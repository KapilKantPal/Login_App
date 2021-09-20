package com.programmingmanav.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPassword extends AppCompatActivity {

    LinearLayout EmailSent_LinerLayout , sendEMail_Linear_layout;
    TextInputLayout EmailForget;
    String EmailForgetSting;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EmailForget = (TextInputLayout) findViewById(R.id.email_forgot);
        EmailSent_LinerLayout = (LinearLayout)findViewById(R.id.EmailSent_LinerLayout);
        sendEMail_Linear_layout = (LinearLayout)findViewById(R.id.sendEMail_Linear_layout);
        auth = FirebaseAuth.getInstance();

    }

    public void backToLogin(View view) {
        Intent inteant = new Intent(ForgetPassword.this , Log_In.class);
        sendEMail_Linear_layout.setTranslationY(0);
        EmailSent_LinerLayout.setTranslationY(2000);
        startActivity(inteant);

    }

    public void ForgotPassword(View view) {
        EmailForgetSting = EmailForget.getEditText().getText().toString();
        if(EmailForgetSting.isEmpty()){
            EmailForget.setError("Required");
        }else{
            auth.sendPasswordResetEmail(EmailForgetSting).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendEMail_Linear_layout.setTranslationY(-2000);
                        EmailSent_LinerLayout.setTranslationY(0);
                    }else{

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}