package com.oldzi.weeeather.mysqltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oldzi.weeeather.R;

/**
 * Created by Oldzi on 13.09.2016.
 */
public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookSQLiteOpenHelper sqliteHelper = new BookSQLiteOpenHelper(this);

        // add BOOK
        Book b = new Book();

        b.setTitle("Android Programming: The Big Nerd Ranch Guide");
        b.setAuthor("Bill Phillips");
        sqliteHelper.addBook(b);

        // add another BOOK
        b = new Book();

        b.setTitle("Professional Android 4 Application Development");
        b.setAuthor("Reto Meier");
        sqliteHelper.addBook(b);


        // get BOOK by id
        Book book = sqliteHelper.getBookById(3);

        // get BOOK by title
        //
        //sqliteHelper.getBookByTitle("Android");

        // get All BOOKS
        sqliteHelper.getAllBooks();

        // get Number of BOOKS
        sqliteHelper.countBooks();

        // update BOOK (the one we got earlier by ID)
        book.setTitle("SRANIE");
        sqliteHelper.updateBook(book);

        // delete BOOK
        sqliteHelper.deleteBook(book);
        sqliteHelper.getAllBooks();

        // get Number of BOOKS
        sqliteHelper.countBooks();
    }
}
