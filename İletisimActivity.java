package com.example.up_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class İletisimActivity extends AppCompatActivity {

    TextView mailSupport;
    ImageView okisareti_dA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);

        okisareti_dA=(ImageView)findViewById(R.id.okisareti_dA);
        mailSupport=(TextView)findViewById(R.id.mailSupport);


        okisareti_dA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                finish();
            }
        });

        mailSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MailActivity.class));
                finish();
            }
        });



    }
}
