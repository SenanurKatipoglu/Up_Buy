package com.example.up_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLS=6000;

    private ViewFlipper viewFlipper;
    private DatabaseReference databaseReference;
    private List<SlideModel> slideLists;
    private Button teklifVer,adminKontrol;
    TextView textView,textView2,textView3;
    EditText teklifim;

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();



    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final DatabaseReference databaseReference2 = firebaseDatabase.getReference("Users").child(
            user.getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        viewFlipper = findViewById(R.id.viewFlipper_slide_show);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        slideLists = new ArrayList<>();

        textView=findViewById(R.id.ürünFiyat);
        textView2=findViewById(R.id.tanım);
        textView3=findViewById(R.id.ürünadi);
        teklifVer=findViewById(R.id.teklifVerBttn);
        teklifim=findViewById(R.id.ürünTeklif);
        adminKontrol=findViewById(R.id.adminkontrol);

        textView.setText(getIntent().getStringExtra("ürünfiyat"));
        textView2.setText(getIntent().getStringExtra("tanım"));
        textView3.setText(getIntent().getStringExtra("ürünadi"));



            adminKontrol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Users user =new Users();
                            Ürün ürün=new Ürün();
                            user.setAdmin(dataSnapshot.child("admin").getValue().toString());
                            if (user.getAdmin().equalsIgnoreCase("admin"))
                            {

                                adminKontrol.setText("SATIŞ KAPANDI");
                                //-----
                               //-------
                                teklifVer.setEnabled(false);




                            }
                            else if (user.getAdmin().equalsIgnoreCase("user")){
                                Toast.makeText(getApplicationContext(),"Bunun için yetkiniz yoktur",Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }); }
            });

        // ürünle ilgili teklif verme

        teklifVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double fiyat=Double.parseDouble(textView.getText().toString());
                double teklifim2=Double.parseDouble(teklifim.getText().toString());

                if (teklifim2>fiyat)
                {
                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Users user=new Users();
                            user.setFullname(dataSnapshot.child("ad").getValue().toString());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    databaseReference.child("Kategori").child(getIntent().getStringExtra("kategori")).child("Ürün").child(getIntent().getStringExtra("tür")).child("ürünfiyat").setValue(teklifim.getText().toString());
                    Users user=new Users();
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Users user =new Users();
                            // verdiğim teklifin karşısına mail yazar
                            user.setFullname(dataSnapshot.child("email").getValue().toString());
                            databaseReference.child("Kategori").child(getIntent().getStringExtra("kategori")).child("Ürün").child(getIntent().getStringExtra("tür")).child("teklifveren").setValue(user.getFullname());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                  databaseReference.child("Kategori").child(getIntent().getStringExtra("kategori")).child("Ürün").child(getIntent().getStringExtra("tür")).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          textView.setText(dataSnapshot.child("ürünfiyat").getValue().toString());

                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });


                }else{
                    Toast.makeText(getApplicationContext(),"Teklifiniz değerden düşüktür",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        usingFirebaseDatabase();
    }

    private void usingFirebaseDatabase() {

        databaseReference.child("Kategori").child(getIntent().getStringExtra("kategori")).child("Ürün").child(getIntent().getStringExtra("tür")).child("images")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            slideLists.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                SlideModel model = snapshot.getValue(SlideModel.class);

                                slideLists.add(model);

                            }
                            Toast.makeText(ProductDetailsActivity.this, "All data fetched", Toast.LENGTH_SHORT).show();
                            usingFirebaseImages(slideLists);
                        } else {
                            Toast.makeText(ProductDetailsActivity.this, "No images in firebase", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ProductDetailsActivity.this, "NO images found \n" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

    private void usingFirebaseImages(List<SlideModel> slideLists) {
        for (int i = 0; i < slideLists.size(); i++) {
            String downloadImageUrl = slideLists.get(i).getImageUrl();
            flipImages(downloadImageUrl);
        }
    }

    public void flipImages(String imageUrl) {
        ImageView imageView = new ImageView(this);
        Picasso.get().load(imageUrl).into(imageView);

        viewFlipper.addView(imageView);

        viewFlipper.setFlipInterval(2500); // otomatik kaydırma metddu
        viewFlipper.setAutoStart(true);

        viewFlipper.startFlipping();
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }


}
