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
