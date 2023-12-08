package com.piratenahid.lognovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.Book;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> stringArrayAdapter;
    DatabaseReference reference;
    Book book;
    FirebaseDatabase database;
    List<String> titleList, contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot value: snapshot.getChildren()){
                    book = value.getValue(Book.class);
                    assert book != null;
                    titleList.add(book.getTitle());
                    contentList.add(book.getContent());
                    Log.d(book.getContent(), "onDataChange: "+ book.getTitle());
                }
                listView.setAdapter(stringArrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, ReaderActivity.class);
                        String string_title = titleList.get(i);
                        String string_content = contentList.get(i);
                        intent.putExtra("title", string_title);
                        intent.putExtra("content", string_content);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init(){
        Log.d("TAG", "init: ");
        listView = findViewById(R.id.MainListView);
        database = DatabaseUtil.getDatabase();
        reference = database.getReference();
        book = new Book();
        titleList = new ArrayList<>();
        contentList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(this, R.layout.items, R.id.item_title, titleList);
    }

    public static class DatabaseUtil {
        private static FirebaseDatabase mDatabase;

        /* access modifiers changed from: private */
        public static FirebaseDatabase getDatabase() {
            if (mDatabase == null) {
                FirebaseDatabase instance = FirebaseDatabase.getInstance();
                mDatabase = instance;
                instance.setPersistenceEnabled(true);
            }
            return mDatabase;
        }
    }

}