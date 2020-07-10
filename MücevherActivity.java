package com.example.up_buy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MücevherActivity extends AppCompatActivity {
    RecyclerView recyclerV;

    private DatabaseReference mReference;
    private ArrayList<Ürün> ürünList;
    private RecycleAdapter recycleAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mucevher);

        recyclerV=findViewById(R.id.recycleV);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerV.setLayoutManager(layoutManager);
        recyclerV.setHasFixedSize(true);

        mReference= FirebaseDatabase.getInstance().getReference();

        ürünList=new ArrayList<>();

        ClearAll();

        GetDataFromFirebase();
    }





    private void GetDataFromFirebase(){
        Query query=mReference.child("Kategori").child("02").child("Ürün");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Ürün ürün=new Ürün();
                    ürün.setKategori(snapshot.child("kategori").getValue().toString());
                    ürün.setTanım(snapshot.child("tanım").getValue().toString());
                    ürün.setÜrünfiyat(snapshot.child("ürünfiyatı").getValue().toString());
                    ürün.setImage(snapshot.child("image").getValue().toString());
                    ürün.setTitle(snapshot.child("ürünadi").getValue().toString());

                    ürünList.add(ürün);
                }
                recycleAdapter=new RecycleAdapter(getApplicationContext(),ürünList);
                recyclerV.setAdapter(recycleAdapter);
                recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void ClearAll(){

        if (ürünList !=null){
            ürünList.clear();

            if(recycleAdapter !=null){
                recycleAdapter.notifyDataSetChanged();
            }
        }
        ürünList=new ArrayList<>();
    }

    }

