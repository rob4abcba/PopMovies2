package com.example.android.popmovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;


public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String mSortBy;



    public MovieLoader(@NonNull Context context, String sortBy) {
        super(context);
        mSortBy = sortBy;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        if (mSortBy==null) return null;

        return NetworkUtils.extractMovies(mSortBy);
    }
}
