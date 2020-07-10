package com.example.up_buy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MailActivity extends AppCompatActivity {
    TextView memailMh;
    EditText msubject,mmessageMh;
    Button msendMessage;
    ImageView okisareti_iA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

     memailMh=(TextView)findViewById(R.id.emailMh);
     msubject=(EditText)findViewById(R.id.subject);
     mmessageMh=(EditText)findViewById(R.id.messageMh);
     msendMessage=(Button)findViewById(R.id.sendMessage);
     okisareti_iA=(ImageView)findViewById(R.id.okisareti_iA);

       msendMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String emailMh=memailMh.getText().toString().trim();
               String subject=msubject.getText().toString().trim();
               String messageMh=mmessageMh.getText().toString().trim();

               okisareti_iA.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       startActivity(new Intent(getApplicationContext(),İletisimActivity.class));
                       finish();
                   }
               });

             sendEmail(emailMh,subject,messageMh);

           }
       });

    }
    private void sendEmail(String emailMh, String subject, String messageMh) {
        Intent emailIntent=new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailMh});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,messageMh);

        try {
            startActivity(Intent.createChooser(emailIntent,"Choose an Email Client"));

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}
