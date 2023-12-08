package com.piratenahid.lognovel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReaderActivity extends AppCompatActivity {
    String title, content;
    TextView titleV, contentV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        titleV = findViewById(R.id.content_title_tv);
        contentV = findViewById(R.id.content_tv);
        titleV.setText(title);
        contentV.setText(content);
    }
}