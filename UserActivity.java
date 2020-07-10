package com.example.up_buy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity{

     private static final int GALLERY_INTENT_CODE=1023;
       TextView username,PhoneNumber,fullname;

       TextView logout;
       TextView sifreDegistirme;
       TextView  profildegisText;
       TextView  name;
       CircularImageView profilePicture;

      public Uri imageUri;

      private StorageReference mstorageReference;

       FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

       private FirebaseAuth mAuth=FirebaseAuth.getInstance();
       private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
             final DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(
            user.getUid());

       //private DatabaseReference mReference;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_informantion);

        profilePicture=findViewById(R.id.profilePicture);

        logout=findViewById(R.id.logout);
        sifreDegistirme=findViewById(R.id.sifreDegistirme);
        profildegisText=findViewById(R.id.profildegisText);
        username=findViewById(R.id.username);
        fullname=findViewById(R.id.fullname);
        PhoneNumber=findViewById(R.id.PhoneNumber);
        //username=findViewById(R.id.username);
        //PhoneNumber=findViewById(R.id.PhoneNumber);
       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               fullname.setText(dataSnapshot.child("ad").getValue().toString());
               Users user=new Users();
               user.setFullname(dataSnapshot.child("ad").getValue().toString());
               username.setText(dataSnapshot.child("email").getValue().toString());
               PhoneNumber.setText(dataSnapshot.child("telefonNo").getValue().toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        mAuth=FirebaseAuth.getInstance();

        mstorageReference=FirebaseStorage.getInstance().getReference();

        //mReference= FirebaseDatabase.getInstance().getReference();

       StorageReference profileRef= mstorageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
             Picasso.get().load(uri).into(profilePicture);
            }
        });


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();

            }
        });

        Toast.makeText(UserActivity.this,"Kullanıcı",Toast.LENGTH_SHORT).show();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation_view);
        bottomNavigationView.setSelectedItemId(R.id.action_profil);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.action_profil:
                        break;
                }return true;
            }
        });

       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();

                Toast.makeText(UserActivity.this,"Çıkış yapıldı",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

       sifreDegistirme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),SifreDegistirmeActivity.class));
           }
       });

    }
       private void choosePicture(){
           Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           startActivityForResult(openGalleryIntent,1000);

       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode==Activity.RESULT_OK){

                Uri imageUri=data.getData();
                //profilePicture.setImageURI(imageUri);

                uploadPicture(imageUri);
            }
        }
    }

    private void uploadPicture(Uri imageUri) {

       final StorageReference fileRef=mstorageReference.child("Users/"+mAuth.getCurrentUser().getUid()+"profile.jpg");
       fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Picasso.get().load(uri).into(profilePicture);
                   }
               });
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(UserActivity.this,"Resim yüklenmedi",Toast.LENGTH_SHORT).show();
           }
       }) ;



    }


}



