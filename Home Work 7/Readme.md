
#  Screenshots
![Image 1](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%207/images/1.JPG)
![Image 2](https://github.com/schirumamilla/ITMD-555/blob/master/Home%20Work%207/images/2.JPG)
#  Activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context=".MainActivity">

   <ListView
       android:id="@+id/booksListView"
       android:layout_width="368dp"
       android:layout_height="495dp"
       tools:layout_editor_absoluteX="8dp"
       tools:layout_editor_absoluteY="8dp" />
</android.support.constraint.ConstraintLayout>

```
# MainActivity.java
```
package com.example.sathish.booksdatabase;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
//import android.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.RatingBar;


public class MainActivity extends AppCompatActivity {
   private Book book;
   ListView booksListView;
   SimpleCursorAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       book = new Book(this);
       book.open();
       Cursor cursor = book.getAllBooks();
       Log.v("books",cursor.toString());

       final String[] from = new String[] {Book.BookEntry.COLUMN_NAME_ID, Book.BookEntry.COLUMN_NAME_TITLE,Book.BookEntry.COLUMN_NAME_AUTHOR,Book.BookEntry.COLUMN_NAME_RATING};
       final int[] to = new int[] { R.id.bookId,R.id.bookTitle,R.id.bookAuthor,R.id.ratingBar};

       booksListView = (ListView) findViewById(R.id.booksListView);
       adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to, 0);
       adapter.notifyDataSetChanged();
       adapter.setViewBinder(new MyBinder());
       booksListView.setAdapter(adapter);
   }

   public class MyBinder implements SimpleCursorAdapter.ViewBinder {
       @Override
       public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
           if(view.getId() == R.id.ratingBar){
               String data = cursor.getString(columnIndex);
               float rating = Float.parseFloat(data);
               RatingBar ratingBar = (RatingBar) view;
               ratingBar.setRating(rating);
               return true;
           }
           return false;
       }
   }
}
```
# Row.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent">

   <android.support.constraint.ConstraintLayout
       android:layout_width="match_parent"
       android:layout_height="match_parent">

       <TextView
           android:id="@+id/bookTitle"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginLeft="16dp"
           android:layout_marginStart="16dp"
           android:layout_marginTop="8dp"
           android:text="TextView"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/bookId" />

       <TextView
           android:id="@+id/bookId"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginLeft="16dp"
           android:layout_marginStart="16dp"
           android:layout_marginTop="8dp"
           android:text="TextView"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="parent" />

       <TextView
           android:id="@+id/bookAuthor"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_marginEnd="24dp"
           android:layout_marginRight="24dp"
           android:layout_marginTop="8dp"
           android:text="TextView"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/bookTitle" />

       <RatingBar
           android:id="@+id/ratingBar"
           android:layout_width="237dp"
           android:layout_height="43dp"
           android:layout_marginLeft="16dp"
           android:layout_marginStart="16dp"
           android:isIndicator="true"
           android:numStars="5"
           android:rating="0"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toBottomOf="@+id/bookAuthor" />
   </android.support.constraint.ConstraintLayout>
</LinearLayout>
```
# Book.java
```
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

```
