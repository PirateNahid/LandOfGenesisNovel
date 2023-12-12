package com.piratenahid.lognovel;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ReaderActivity extends AppCompatActivity {
    String title, content;
    TextView titleV, contentV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        getWindow().setStatusBarColor(Color.WHITE);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        titleV = findViewById(R.id.content_title_tv);
        contentV = findViewById(R.id.content_tv);
        titleV.setText(title);
        contentV.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
    }
}