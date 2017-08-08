package com.testing.capi.learningtesting;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by capi on 8/8/17.
 */

public class BookTitleMatcher extends TypeSafeMatcher<Book> {

    private String bookTitle;

    public BookTitleMatcher(String bookTitle){
        this.bookTitle = bookTitle;
    }


    @Override
    protected boolean matchesSafely(Book item) {
        if (item.getTitle() != null){
            return item.getTitle().equals(bookTitle);
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with title: ");
        description.appendText(bookTitle);
    }

    public static BookTitleMatcher matchesBookTitle(String bookTitle){
        return new BookTitleMatcher(bookTitle);
    }
}
