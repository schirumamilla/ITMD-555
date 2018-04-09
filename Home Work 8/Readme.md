#  Screenshots
![Image 1](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%208/images/3.JPG)
![Image 2](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%208/images/4.JPG)
#  Activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context=".MainActivity">


   <Spinner
       android:id="@+id/bookSpinner"
       android:layout_width="324dp"
       android:layout_height="22dp"
       android:layout_marginEnd="24dp"
       android:layout_marginLeft="24dp"
       android:layout_marginRight="24dp"
       android:layout_marginStart="24dp"
       android:layout_marginTop="32dp"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>

```
# MainActivity.java
```
package com.example.sathish.bookdatabasesearch;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private Book book;
   SimpleCursorAdapter adapter;
   Spinner bookSpinner;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       book = new Book(this);
       book.open();
       Cursor cursor = book.getAllBooks();
       final String[] from = new String[] {Book.BookEntry.COLUMN_NAME_TITLE, Book.BookEntry.COLUMN_NAME_ID};
       final int[] to = new int[] {android.R.id.text1};

       bookSpinner = (Spinner) findViewById(R.id.bookSpinner);
       adapter = new SimpleCursorAdapter(this,  android.R.layout.simple_spinner_item, cursor, from, to, 0);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
       bookSpinner.setAdapter(adapter);

       bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

           public void onItemSelected(AdapterView parent, View view,
                                      int pos, long log) {

               Cursor c = (Cursor)parent.getItemAtPosition(pos);
               int id = c.getInt(c.getColumnIndexOrThrow(Book.BookEntry.COLUMN_NAME_ID));
               Cursor cursor1 = book.findBookById(id);
               String author = cursor1.getString(cursor1.getColumnIndex(Book.BookEntry.COLUMN_NAME_AUTHOR));
               String rating = cursor1.getString(cursor1.getColumnIndex(Book.BookEntry.COLUMN_NAME_RATING));
               Toast.makeText(parent.getContext(), "Author Name :: " + author + "\n Rating = "+ rating, Toast.LENGTH_SHORT).show();
           }

           public void onNothingSelected(AdapterView arg0) {
           }
       });
   }
}
```
# Book.java
```
package com.example.sathish.bookdatabasesearch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

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
           "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                   BookEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                   BookEntry.COLUMN_NAME_TITLE + " TEXT," +
                   BookEntry.COLUMN_NAME_AUTHOR + " TEXT,"+
                   BookEntry.COLUMN_NAME_RATING +" FLOAT )";

   private static final String SQL_INSERT_DATA = " INSERT INTO " + BookEntry.TABLE_NAME + "(" + BookEntry.COLUMN_NAME_TITLE +
           ", " + BookEntry.COLUMN_NAME_AUTHOR + ", "+ BookEntry.COLUMN_NAME_RATING +") VALUES " +
           "('Professional Android 4 Application Development', 'Reto Meier', '4'), "+
           "('Beginning Android 4 Application Development,', 'Wei Meng Lee', '3.5'),"+
           "('Programming Android', 'Wallace Jackson', '4.6'),"+
           "('Hello, Android', 'Wallace Jackson', '3.9');";

   private static final String SQL_DELETE_ENTRIES =
           "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

   public SQLiteDatabase open() throws SQLException {
       SqlHelper mDbHelper = new SqlHelper(context);
       db = mDbHelper.getWritableDatabase();
       return db;
   }
   //method to fetch all books.........
   public Cursor getAllBooks() {
       String[] columns = new String[] { BookEntry.COLUMN_NAME_ID, BookEntry.COLUMN_NAME_TITLE, BookEntry.COLUMN_NAME_AUTHOR, BookEntry.COLUMN_NAME_RATING };
       Cursor cursor = db.query(BookEntry.TABLE_NAME, columns, null, null, null, null, null);
       if (cursor != null) {
           cursor.moveToFirst();
       }
       Log.v("cursor",cursor.toString());
       return cursor;
   }

   public Cursor findBookById(int BookId){
       String[] column = new String [] {BookEntry.COLUMN_NAME_AUTHOR, BookEntry.COLUMN_NAME_RATING };
       Cursor cursor = db.query(BookEntry.TABLE_NAME, column, "_id=?", new String[]{Integer.toString(BookId)}, null,null,null );
       if (cursor != null) {
           cursor.moveToFirst();
       }
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
           return db.update(BookEntry.TABLE_NAME,values,BookEntry.COLUMN_NAME_ID+"=?",new String[] {  String.valueOf(book.BookId)});
       }

       // This method is not used........
       public void deleteBook(Book book) {
           db.delete(BookEntry.TABLE_NAME,BookEntry.COLUMN_NAME_ID+"="+book.BookId, null);
       }
   }
}
```
