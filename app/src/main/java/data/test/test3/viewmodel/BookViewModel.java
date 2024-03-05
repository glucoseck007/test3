package data.test.test3.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import data.test.test3.db.entity.BookEntity;
import data.test.test3.repository.BookRepository;

public class BookViewModel extends AndroidViewModel {

    private final LiveData<List<BookEntity>> allBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);

        BookRepository repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return allBooks;
    }

}
