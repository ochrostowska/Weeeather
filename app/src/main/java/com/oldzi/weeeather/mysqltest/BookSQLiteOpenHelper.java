package com.oldzi.weeeather.mysqltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Oldzi on 13.09.2016.
 */
public class BookSQLiteOpenHelper extends SQLiteOpenHelper {

    // DATABASE VERSION AND NAME
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BOOK_DB";

    public BookSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // TABLE AND COLUMNS NAMES
    private static final String TABLE_BOOK = "BOOK";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "TITLE";
    private static final String COLUMN_AUTHOR = "AUTHOR";

    private static final String[] COLUMNS = {COLUMN_ID,COLUMN_TITLE,COLUMN_AUTHOR};

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE BOOK ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "author TEXT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS BOOK");
        Log.d("MAMA", "table dropped");
        // create fresh books table
        this.onCreate(db);

    }

    //-----------------------------------------

    public void addBook(Book book){

        // log
        Log.d("addBook", book.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, book.getTitle()); // get title
        values.put(COLUMN_AUTHOR, book.getAuthor()); // get author


        // 3. insert
        // "INSERT INTO BOOK (TITLE, AUTHOR) VALUES (book.title, book.author)"

        db.insert(TABLE_BOOK, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Book getBookById(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        // SELECT ID, TITLE, AUTHOR FROM BOOK WHERE ID = id"
        Cursor cursor =
                db.query(TABLE_BOOK, // a. table
                        COLUMNS, // b. column names
                        COLUMN_ID+" = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));


        // 5. return book
        // log
        Log.d("getBookById", book.toString());
        return book;
    }

    public List<Book> getBookByTitle(String title){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        // SELECT ID, TITLE, AUTHOR FROM BOOK WHERE ID = id"
        Cursor cursor =
                db.query(TABLE_BOOK, // a. table
                        COLUMNS, // b. column names
                        COLUMN_TITLE+" like %?%", // c. selections
                        new String[] { title }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. go over each row, build book and add it to list
        List<Book> books = new LinkedList<Book>();
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        // log
        Log.d("getBookByTitle", books.toString());

        // 5. return book
        return books;
    }
    // Get All Books
    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();


        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_BOOK;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("MAMA", "" +db.getVersion());
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        //     log
        Log.d("getAllBooks", books.toString());

        // return books
        return books;
    }

    // Get Books Count
    public int countBooks() {

        int count = 0;
        // 1. build the query
        String query = "SELECT COUNT(*) FROM " + TABLE_BOOK;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        // 3. if we got results get the first one
        if (cursor != null){
            cursor.moveToFirst();

            // 4. build book object
            count = Integer.parseInt(cursor.getString(0));
        }

        // log
        Log.d("countBooks", count +"Book(s)");
        // return count
        return count;
    }


    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author

        // 3. updating row
        // "UPDATE BOOK SET TITLE = book.title, AUTHOR = book.author WHERE ID = book.id"
        int i = db.update(TABLE_BOOK, //table
                values, // column/value
                COLUMN_ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        // log
        Log.d("updateBook", book.toString());
        return i;

    }

    // Deleting single book
    public void deleteBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        // "DELETE BOOK WHERE ID = book.id"
        db.delete(TABLE_BOOK,
                COLUMN_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });

        // 3. close
        db.close();

        // log
        Log.d("deleteBook", book.toString());

    }
}