package data.test.test3.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import data.test.test3.db.entity.BookEntity;

@Dao
public interface BookDao {

    @Insert
    void insertAllBooks(List<BookEntity> bookEntities);

    @Insert
    void insertBook(BookEntity book);

    @Query("SELECT * FROM BOOK")
    LiveData<List<BookEntity>> loadAllBooks();

    @Query("DELETE FROM BOOK")
    void deleteAllBooks();

}
