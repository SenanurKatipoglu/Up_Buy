package com.example.up_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class KullanimKosuluActivity extends AppCompatActivity {

   // ImageView okisareti_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanim_kosulu);

     /*   okisareti_sp=findViewById(R.id.okisareti_sp);

        okisareti_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                finish();
            }
        }); */
    }
}
