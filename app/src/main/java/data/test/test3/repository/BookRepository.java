package data.test.test3.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import data.test.test3.db.AppDatabase;
import data.test.test3.db.dao.BookDao;
import data.test.test3.db.entity.BookEntity;

public class BookRepository {

    private final BookDao bookDao;
    public BookRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        bookDao = appDatabase.bookDao();
    }

    public void createBook(List<BookEntity> bookEntityList) {
        bookDao.insertAllBooks(bookEntityList);
    }

    public void createBook(BookEntity book) {
        bookDao.insertBook(book);
    }

    public LiveData<List<BookEntity>> getAllBooks() {
        return bookDao.loadAllBooks();
    }

}
