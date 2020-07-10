package com.example.up_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SifreDegistirmeActivity extends AppCompatActivity {
       EditText musername;
       Button sifreDegistirmeBttn;
       TextView backtoHomePage;

       FirebaseAuth mAuth;
       DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistirme);

        musername=findViewById(R.id.username);
        sifreDegistirmeBttn=findViewById(R.id.sifreDegistirmeBttn);
        backtoHomePage=findViewById(R.id.backtoHomePage);

        sifreDegistirmeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= musername.getText().toString();

                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SifreDegistirmeActivity.this,"Email hesabınızı kontrol ediniz",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(SifreDegistirmeActivity.this,"Şifre sıfırlanmadı",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        backtoHomePage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),UserActivity.class));
               finish();
           }
       });
    }



}
