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
