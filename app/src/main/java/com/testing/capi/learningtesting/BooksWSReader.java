package com.testing.capi.learningtesting;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import cl.dsarhoya.dsyutils.ws.DSYRequest;

/**
 * Created by capi on 8/7/17.
 */

public class BooksWSReader extends DSYRequest<Book[]> {

    public BooksWSReader(){
        setDebugging(true);
    }

    @Override
    protected String getURL() {
        return APIConf.API_URL + "api/books";
    }

    @Override
    protected Class<Book[]> getResponseClass() {
        return Book[].class;
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    protected HttpHeaders getHttpHeader() {
        return null;
    }
}
