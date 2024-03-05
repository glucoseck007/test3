package data.test.test3.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import data.test.test3.db.model.Book;

@Entity (tableName = "BOOK")
public class BookEntity implements Book {

    @PrimaryKey
    @NonNull
    private int id;
    private String title;
    private String author;

    public BookEntity() {};

    public BookEntity(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }
}
