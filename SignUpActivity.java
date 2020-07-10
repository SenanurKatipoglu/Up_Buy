package com.example.up_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


// CHECKBOX İŞARETLENMEDEN KAYIT OLMAMA ŞARTINI EKLE

public class SignUpActivity extends AppCompatActivity {
    EditText mfullname, musername, mpassword, mPhoneNumber,madmin;
    Button signUp_Bttn;
    CheckBox  cb2;
    Button kullanimKosuluBttn,gizlilikKosuluBttn;
    ImageView okisareti_sp;

    private FirebaseAuth mAuth;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        okisareti_sp=(ImageView)findViewById(R.id.okisareti_sp);
        mfullname = (EditText) findViewById(R.id.fullname);
        musername = (EditText) findViewById(R.id.username);
        mpassword = (EditText) findViewById(R.id.password);
        madmin=findViewById(R.id.admin);
        mPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        signUp_Bttn = (Button) findViewById(R.id.signUp_Bttn);

        cb2=(CheckBox)findViewById(R.id.cb2);

        gizlilikKosuluBttn=(Button) findViewById(R.id.gizlilikKosuluBttn);

        okisareti_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        gizlilikKosuluBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GizlilikKosuluActivity.class));
                finish();
            }
        });

        signUp_Bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUsers();
            }
        });


    }


    private boolean addUsers() {

        final String fullname = mfullname.getText().toString().trim();
        final String username = musername.getText().toString().trim();
        final String password = mpassword.getText().toString().trim();
        final String PhoneNumber = mPhoneNumber.getText().toString().trim();
        final String admin = madmin.getText().toString().trim();

        if (TextUtils.isEmpty(fullname)) {
            mfullname.setError("Ad Soyad giriniz");
            return false;
        }

        if (TextUtils.isEmpty(username)) {
            musername.setError("emailinizi giriniz");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            mpassword.setError(("Şifrenizi giriniz"));
            return false;
        }

        if (password.length() < 8 || password.length()>8 ) {
            mpassword.setError("Şifre 8 haneli olmalı");
            return false;
        }

        if (TextUtils.isEmpty(PhoneNumber)) {
            mPhoneNumber.setError("Telefon Numaranızı giriniz");
            return false;
        }

        if (PhoneNumber.length() < 11 || PhoneNumber.length() > 11) {
            mPhoneNumber.setError("Telefon numarası eksik veya fazla yazdınız");
            return false;
        }

        if (TextUtils.isEmpty(admin)) {
            mPhoneNumber.setError("user yazınız");
            return false;
        }


        if(!cb2.isChecked()){
           return false;
       }

        if (TextUtils.isEmpty(fullname) | TextUtils.isEmpty(username) | TextUtils.isEmpty(password) | TextUtils.isEmpty(PhoneNumber) | TextUtils.isEmpty(admin) ){
            return false;
        }

        mAuth.createUserWithEmailAndPassword(musername.getText().toString(), mpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                        Map<String, String> yeniUser = new HashMap<String, String>();
                        yeniUser.put("ad", mfullname.getText().toString());
                        yeniUser.put("şifre", mpassword.getText().toString());
                        yeniUser.put("telefonNo",mPhoneNumber.getText().toString());
                        yeniUser.put("email",musername.getText().toString());
                        yeniUser.put("admin",madmin.getText().toString());



                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Users")
                                .child(firebaseAuth.getCurrentUser().getUid())
                                .setValue(yeniUser);

                    Toast.makeText(SignUpActivity.this, "Kullanıcı oluşturuldu", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(SignUpActivity.this, "Kullanıcı oluşturulamadı" + task.getException().getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        return false;
    }


}
