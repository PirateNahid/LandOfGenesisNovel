package com.piratenahid.lognovel;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.Book;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> stringArrayAdapter;
    SimpleAdapter simpleAdapter;
    DatabaseReference reference;
    Book book;
    FirebaseDatabase database;
    List<String> titleList, contentList;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot value: snapshot.getChildren()){
                    book = value.getValue(Book.class);
                    assert book != null;
                    titleList.add(book.getTitle());
                    contentList.add(book.getContent());
                }
                for (int i= 1; i <= titleList.size(); i++ ){
                    map = new HashMap<>();
                    map.put("title",book.getTitle());
                    map.put("subtitle","Chapter "+i);
                    arrayList.add(map);
                }

                listView.setAdapter(simpleAdapter);

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
        arrayList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter<>(this, R.layout.items, R.id.item_title, titleList);
        simpleAdapter = new SimpleAdapter((Context) this, arrayList,R.layout.items,new String[]{"title","subtitle"},new int[]{R.id.item_title,R.id.item_sub_title});
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