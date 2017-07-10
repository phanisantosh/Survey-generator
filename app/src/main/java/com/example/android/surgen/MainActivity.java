package com.example.android.surgen;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DashBoard");

        TextView generate = (TextView) findViewById(R.id.generatesurvey);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent generateIntent = new Intent(MainActivity.this,GenerateSurvey.class);
                startActivity(generateIntent);
            }
        });

        TextView take = (TextView) findViewById(R.id.takesurvey);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeIntent = new Intent(MainActivity.this,TakeSurvey.class);
                startActivity(takeIntent);
            }
        });

        TextView response = (TextView) findViewById(R.id.responses);
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent responseIntent = new Intent(MainActivity.this,Response.class);
                startActivity(responseIntent);
            }
        });

        TextView issue = (TextView) findViewById(R.id.report);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent response =  new Intent(Intent.ACTION_SEND);
                response.setData(Uri.parse("email"));
                String[] s={"abc@gmail.com","xyz@gmail.com"};
                response.putExtra(Intent.EXTRA_EMAIL,s);
                response.putExtra(Intent.EXTRA_SUBJECT,"Issue in Surgen");
                response.setType("message/rfc822");
                Intent chooser = Intent.createChooser(response,"Launch Email");
                startActivity(chooser);
            }
        });


    }
}
