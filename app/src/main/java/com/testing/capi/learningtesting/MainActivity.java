package com.testing.capi.learningtesting;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.dsarhoya.dsyutils.BusFactory;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter adapter;
    private ListView list;
    private View progress;

    private TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        adapter = new BooksAdapter(this, new ArrayList<Book>());
        list = (ListView) findViewById(R.id.books_list);
        list.setAdapter(adapter);

        emptyMessage = (TextView) findViewById(R.id.empty_message);
        progress = findViewById(R.id.progress);

        new BooksWSReader().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusFactory.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusFactory.getBus().unregister(this);
    }

    @Subscribe
    public void onBooksReceived(Book[] books){
        Log.d(getClass().getSimpleName(), "Books received");

        adapter.clear();
        adapter.addAll(Arrays.asList(books));
        adapter.notifyDataSetChanged();

        if (books.length > 0){
            emptyMessage.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }else{
            emptyMessage.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void requestFailed(Exception e){
        progress.setVisibility(View.GONE);
        Toast.makeText(this, R.string.network_error, Toast.LENGTH_LONG).show();
    }
}
