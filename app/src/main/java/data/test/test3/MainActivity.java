package data.test.test3;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import data.test.test3.adapter.BookAdapter;
import data.test.test3.db.AppDatabase;
import data.test.test3.db.entity.BookEntity;
import data.test.test3.repository.BookRepository;
import data.test.test3.viewmodel.BookViewModel;

public class MainActivity extends AppCompatActivity {

    private BookViewModel bookViewModel;
    private BookAdapter bookAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(new ArrayList<>());
        recyclerView.setAdapter(bookAdapter);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(this, bookEntities -> {
            if (bookEntities != null) {
                bookAdapter.updateData(bookEntities);
            }
        });
    }

}