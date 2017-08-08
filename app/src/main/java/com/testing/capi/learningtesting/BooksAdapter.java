package com.testing.capi.learningtesting;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by capi on 8/7/17.
 */

public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter(@NonNull Context context,@NonNull Book[] objects) {
        super(context, R.layout.li_book, objects);
    }

    public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, R.layout.li_book, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.li_book, parent, false);

        Book b = getItem(position);

        TextView bookTitleTV = v.findViewById(R.id.book_title);
        bookTitleTV.setText(b.getTitle());

        TextView bookAuthorTV = v.findViewById(R.id.book_author);
        bookAuthorTV.setText(b.getAuthor());

        return v;
    }
}
