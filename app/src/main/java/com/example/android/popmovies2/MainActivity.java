package com.example.android.popmovies2;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Movie>>, MovieListAdapter.MovieOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_KEY = "MOVIE_KEY";
    private static final int MOVIE_LOADER_ID = 1;
    private static boolean PREFERENCES_CHANGED = false;
    private static final String API_KEY = BuildConfig.API_KEY;
    private AppDatabase mDatabase;
    private String mString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MovieListAdapter(this);
        mAdapter.setMovieList(new ArrayList<Movie>());
        mAdapter.setContext(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        mDatabase = AppDatabase.getInstance((getApplicationContext()));


        getMovies();

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    private void getFavorites(){
        final LiveData<Movie[]> mFavoriteList = mDatabase.movieDao.loadLiveMovies();
        mFavoriteList.observe(this, new Observer<Movie[]>() {
            @Override
            public void onChanged(@Nullable Movie[] movies) {
                if (mString.equals(getResources().getString(R.string.sort_by_favorites_value))){
                    mAdapter.setMovieList(Arrays.asList(movies));
                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
        PREFERENCES_CHANGED = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void getMovies(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){

            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);

        }else {
            Log.i(LOG_TAG, "No internet connection");

        }

    }

    public void resetAdapter(){
        mAdapter.setMovieList(new ArrayList<Movie>());
        mRecyclerView.setAdapter(mAdapter);
    }
    private Uri.Builder createUri(String queryString) {
        Uri baseUri = Uri.parse(MOVIE_BASE_URL);
        Uri.Builder builtURI = baseUri.buildUpon();

        builtURI.appendPath(queryString)
                .appendQueryParameter("api_key", API_KEY);

        return builtURI;
    }


    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPreferences.getString(getString(R.string.sort_by_key),
                getString(R.string.sort_by_default));

        Uri.Builder uri = createUri(sortBy);


        return new MovieLoader(this, uri.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        resetAdapter();
        if (!data.isEmpty() && data != null){
            mAdapter.setMovieList(data);

            mRecyclerView.setAdapter(mAdapter);

        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        resetAdapter();

    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MoviesDetailActivity.class);
        intent.putExtra(MOVIE_KEY, movie);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_CHANGED = true;

    }
}
