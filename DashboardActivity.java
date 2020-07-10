package com.example.up_buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends MainActivity implements View.OnClickListener {  // AppCompatti   // Ürünleri koyucaz kategori olarak

      private DatabaseReference mReference;

     ImageView okisareti_goTeklif;
     //ImageView bildirimler;
     CardView  arabalar_category,sanat_category,antika_category,mucevher_category,destek_category;

    // Button expend_all_arabalar,expend_all_m,expend_all_gayrimenkul,expend_all_sanat,expend_all_muzik;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        okisareti_goTeklif = (ImageView) findViewById(R.id.oksareti_goTeklif);
        arabalar_category = (CardView) findViewById(R.id.arabalar_category);
        sanat_category = (CardView) findViewById(R.id.sanat_category);
        antika_category = (CardView) findViewById(R.id.antika_category);
        mucevher_category= (CardView) findViewById(R.id.mucevher_category);
        destek_category = (CardView) findViewById(R.id.destek_category);


        okisareti_goTeklif.setOnClickListener(this);
        arabalar_category.setOnClickListener(this);
        sanat_category.setOnClickListener(this);
       antika_category.setOnClickListener(this);
        mucevher_category.setOnClickListener(this);
        destek_category.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.oksareti_goTeklif: startActivity(new Intent(DashboardActivity.this,UserActivity.class));break;
            case R.id.arabalar_category:  startActivity(new Intent(DashboardActivity.this,ArabalarActivity.class)); break;
            case R.id.sanat_category: startActivity(new Intent(DashboardActivity.this, SanatActivity.class)); break;
            case R.id.antika_category: startActivity(new Intent(DashboardActivity.this, AntikaActivity.class)); break;
            case R.id.mucevher_category: startActivity(new Intent(DashboardActivity.this, MücevherActivity.class)); break;
            case R.id.destek_category: startActivity(new Intent(DashboardActivity.this,İletisimActivity.class)); break;
            default: break;
        }

    }
}

