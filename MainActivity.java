package com.example.up_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN =101 ;
    EditText musername;
    EditText mpassword;
    Button mloginBttn;
    TextView mforget_password;
    TextView msignUp;
    SignInButton GoogleBttn;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth;
    DatabaseReference mReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
       mReference= FirebaseDatabase.getInstance().getReference();


        musername = (EditText) findViewById(R.id.username);
        mpassword = (EditText) findViewById(R.id.password);
        mloginBttn = (Button) findViewById(R.id.loginBttn);
        msignUp=(TextView) findViewById(R.id.signUp);
        mforget_password = (TextView) findViewById(R.id.forget_password);
        GoogleBttn=findViewById(R.id.GoogleBttn);


        mloginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = musername.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    musername.setError("emailinizi giriniz");

                }
                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("password giriniz");
                    return;
                }
                if (password.length() < 8) {
                    mpassword.setError("password 8 haneli olmalı");
                    return;
                }

                mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //String phoneNumber= mAuth.getCurrentUser().getPhoneNumber();
                            Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                            startActivity(intent); // SignUp ı anasayfaya yönlendir.

                        } else {
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        } }}); }});

        msignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });

        mforget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),ForgetPasswordActivity.class));
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              signIn();
            }
        }); }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign başarılı ise, authenticate kısmına yerleşir.
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //başarısız ise error mesajı alınır
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        // AuthCredential google oturum açma kimlik doğrulama sağlayıcısını temsil eder.
        //idtoken google oturum açma sdk sından alınan bir google oturum açma kimlik no su gibi düşünüelebilr.

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Başarılı bir şekilde oturum açın, kullanıcı arayüzünü oturum açan kullanıcı bilgileriyle güncelleyin
                            FirebaseUser user = mAuth.getCurrentUser();
                            Users user2=new Users();
                            user2.setUsername(user.getEmail());
                            Toast.makeText(MainActivity.this,user.getEmail()+user.getDisplayName(),Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            //eğer giriş başarılı değil se error mesajı verilir.
                           Toast.makeText(MainActivity.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
        startActivity(intent);
    }




}











