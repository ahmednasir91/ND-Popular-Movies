package com.ahmed.popularmovies.dto;

/**
 * Created by Ahmed.
 */
public class Response<T> {
    private int page;
    private T results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Response{" +
                "page=" + page +
                ", results=" + results +
                '}';
    }
}
