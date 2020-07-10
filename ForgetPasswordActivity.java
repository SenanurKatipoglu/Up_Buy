package com.example.up_buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText musername;
    ProgressBar mprogressBar;
    TextView usernameActivity;
    Button resetPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        musername=(EditText)findViewById(R.id.username);
        mprogressBar=(ProgressBar)findViewById(R.id.progressBar);
        resetPassword=(Button)findViewById(R.id.resetPassword);
        usernameActivity=(TextView) findViewById(R.id.usernameActivity);

        //FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String email= musername.getText().toString();

                 FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this,"Email hesabınızı kontrol ediniz",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ForgetPasswordActivity.this,"Şifre sıfırlanmadı",Toast.LENGTH_SHORT).show();
                        }
                     }
                 });

             }
         });

       usernameActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });



    }
}
