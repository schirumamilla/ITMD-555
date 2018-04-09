package com.example.sathish.booksdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import static com.example.sathish.booksdatabase.Book.BookEntry.TABLE_NAME;

public class Book {
    private Context context;
    public Book(Context c){
        context = c;
    }

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "BookDB";
    public static final int DATABASE_VERSION = 1;

    /* Inner class that defines the table contents */
    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR= "author";
        public static final String COLUMN_NAME_RATING= "rating";
    }

    public String BookTitle, BookAuthor, BookRating;
    public int BookId;

    public void setBookId(int Id){
        this.BookId = Id;
    }

    public int getBookId(){
        return this.BookId;
    }
    public String getBookTitle(){
        return this.BookTitle;
    }

    public String getBookAuthor(){
        return this.BookAuthor;
    }

    public String getBookRating(){
        return this.BookRating;
    }

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    BookEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_TITLE + " TEXT," +
                    BookEntry.COLUMN_NAME_AUTHOR + " TEXT,"+
                    BookEntry.COLUMN_NAME_RATING +" FLOAT )";

    private static final String SQL_INSERT_DATA = " INSERT INTO " + TABLE_NAME + "(" + BookEntry.COLUMN_NAME_TITLE +
                                                    ", " + BookEntry.COLUMN_NAME_AUTHOR + ", "+ BookEntry.COLUMN_NAME_RATING +") VALUES " +
                                                    "('Professional Android 4 Application Development', 'Reto Meier', '4'), "+
                                                    "('Beginning Android 4 Application Development,', 'Wei Meng Lee', '3.5'),"+
                                                    "('Programming Android', 'Wallace Jackson', '4.6'),"+
                                                    "('Hello, Android', 'Wallace Jackson', '3.9');";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteDatabase open() throws SQLException{
        SqlHelper mDbHelper = new SqlHelper(context);
        db = mDbHelper.getWritableDatabase();
        return db;
    }
    //method to fetch all books.........
    public Cursor getAllBooks() {
        String[] columns = new String[] { BookEntry.COLUMN_NAME_ID, BookEntry.COLUMN_NAME_TITLE, BookEntry.COLUMN_NAME_AUTHOR, BookEntry.COLUMN_NAME_RATING };
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.v("cursor",cursor.toString());
        return cursor;
    }

    public class SqlHelper extends SQLiteOpenHelper {

        public SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
            db.execSQL(SQL_INSERT_DATA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        // This method is not used........
        public Book addBook(Book book){
            ContentValues values  = new ContentValues();
            values.put(BookEntry.COLUMN_NAME_TITLE,book.getBookTitle());
            values.put(BookEntry.COLUMN_NAME_AUTHOR,book.getBookAuthor());
            values.put(BookEntry.COLUMN_NAME_RATING,book.getBookRating());
            int insertid = (int) db.insert(BookEntry.TABLE_NAME,null,values);
            book.setBookId(insertid);
            Log.v("addBook","Book [id="+book.getBookId()+",title ="+ book.getBookTitle() +", author =" + book.getBookAuthor() + ", rating = "+ book.getBookRating()+"]");
            return book;
        }

        // This method is not used........
        public int updateBook(Book book){
            ContentValues values = new ContentValues();
            values.put(BookEntry.COLUMN_NAME_TITLE,book.BookTitle);
            values.put(BookEntry.COLUMN_NAME_AUTHOR,book.BookAuthor);
            values.put(BookEntry.COLUMN_NAME_RATING,book.BookRating);
            return db.update(TABLE_NAME,values,BookEntry.COLUMN_NAME_ID+"=?",new String[] {  String.valueOf(book.BookId)});
        }

        // This method is not used........
        public void deleteBook(Book book) {
            db.delete(TABLE_NAME,BookEntry.COLUMN_NAME_ID+"="+book.BookId, null);
        }
    }
}
