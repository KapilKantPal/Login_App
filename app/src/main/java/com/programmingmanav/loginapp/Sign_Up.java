package com.programmingmanav.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity {

    protected ImageView iLB , iiLB;
    protected TextInputLayout email,pwd , phone_No , UserName;
    protected EditText mobile_no;
    protected ProgressBar bar;
    public FirebaseAuth mAuth;
    FirebaseDatabase db;
    LinearLayout sign_in_LinearLayout;
    SignInButton btn;
    DataBaseHolder obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initialisation
        iLB = (ImageView) findViewById(R.id.iLB);
        iiLB = (ImageView) findViewById(R.id.iiLB);

        email = (TextInputLayout)findViewById(R.id.email_SignUp);
        UserName = (TextInputLayout)findViewById(R.id.Name_SignUp);
        phone_No = (TextInputLayout) findViewById(R.id.PhoneNo_SignUp);
        pwd = (TextInputLayout)findViewById(R.id.pwd_SignUp);

        bar = (ProgressBar)findViewById(R.id.signIn_progressbar);
        mobile_no = (EditText) phone_No.getEditText();
        sign_in_LinearLayout = (LinearLayout)findViewById(R.id.sign_in_LinearLayout);

        db = FirebaseDatabase.getInstance();



        btn = (SignInButton) findViewById(R.id.google_signIn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up.this , googleSignIn.class);
                intent.putExtra("UserExist" , false);
                startActivity(intent);
            }
        });


        AnimateLBackground();
    }





    protected void AnimateLBackground(){
        iLB.animate().rotationX(180).setDuration(250);
        iiLB.animate().rotationX(180).setDuration(250);
    }

    public void SignUp(View view){
        String Email = email.getEditText().getText().toString().trim();
        String password = pwd.getEditText().getText().toString().trim();
        String User_name = UserName.getEditText().getText().toString().trim();
        String Phone_No = phone_No.getEditText().getText().toString().trim();
        obj = new DataBaseHolder(User_name, Email ,password );

        if(Email.isEmpty()){
            email.setError("Required");
        }else{
            if(password.isEmpty()){
                pwd.setError("Required");
            }else{
                if(UserName.getEditText().getText().toString().isEmpty()){
                    UserName.setError("Required");
                }else{
                    if(mobile_no.getText().toString().isEmpty()){
                        mobile_no.setError("Required");
                    }else{
                        goToHomePage(Email , password ,User_name , Phone_No  );
                    }
                }
            }
        }
    }

    private void goToHomePage(String Email , String password , String User_name , String Phone_No ) {


        bar.setAlpha(1);
        sign_in_LinearLayout.setAlpha(0.5F);
        sign_in_LinearLayout.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        try {
            mAuth.createUserWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                bar.setAlpha(0);
                                sign_in_LinearLayout.setAlpha(1);
                                sign_in_LinearLayout.setEnabled(true);
                                Log.i("SignUp" , "Success");
                                email.getEditText().setText("");
                                pwd.getEditText().setText("");
                                FirebaseUser userID = mAuth.getCurrentUser();
                                userID.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        DatabaseReference node = db.getReference("Users");
                                        node.child("PhoneNo "+Phone_No).setValue(obj);
                                        email.getEditText().setText("");
                                        UserName.getEditText().setText("");
                                        phone_No.getEditText().setText("");
                                        pwd.getEditText().setText("");
                                        Intent intent = new Intent(Sign_Up.this , Log_In.class);
                                        Toast.makeText(getApplicationContext(), "Verify Email before Login", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                        Log.i("Exception" , e.toString());
                                    }
                                });

                            } else {
                                sign_in_LinearLayout.setAlpha(1);
                                sign_in_LinearLayout.setEnabled(true);
                                bar.setAlpha(0);
                                email.getEditText().setText("");
                                pwd.getEditText().setText("");
                                Toast.makeText(getApplicationContext(), "Your are already registered\nor\ncheck you Internet Connection" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }catch (IllegalStateException e){
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    public void LogIn(View view) {
        startActivity(new Intent(getApplicationContext() , Log_In.class));
    }



}