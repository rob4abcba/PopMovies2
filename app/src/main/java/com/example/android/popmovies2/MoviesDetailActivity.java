package com.example.android.popmovies2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MoviesDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Movie>{

    private static final String MOVIE_KEY = "MOVIE_KEY";

    private ImageView mPoster;
    private TextView mRating;
    private TextView mReleaseDate;
    private TextView mSynopsis;
    private TextView mReview;
    private RelativeLayout mRelativeLayout;
    private LinearLayoutManager mReviewLayout;
    private RecyclerView mReviewRecyclerView;
    private MovieReviewAdapter mMovieReviewAdapter;
    private Button mButton;
    private AppDatabase mAppDatabase;
    private boolean favorite;
    private Movie mMovie;
    private static final int MOVIE_LOADER_ID = 50;
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        mMovie = (Movie) getIntent().getParcelableExtra(MOVIE_KEY);

        mPoster = (ImageView) findViewById(R.id.movie_poster_detail);
        mRating = (TextView) findViewById(R.id.movie_rating_detail);
        mReleaseDate = (TextView) findViewById(R.id.movie_release_date_detail);
        mSynopsis = (TextView) findViewById(R.id.movie_synopsis_detail);
        mReview = findViewById(R.id.moviereviewtext);

        mReviewRecyclerView = findViewById(R.id.movie_review_recyclerview);
        mReviewLayout = new LinearLayoutManager(this);
        mReviewRecyclerView.setLayoutManager(mReviewLayout);
        mMovieReviewAdapter = new MovieReviewAdapter();
        mReviewRecyclerView.setAdapter(mMovieReviewAdapter);

        Picasso.with(this).load(mMovie.getPosterPath()).into(mPoster);
        mRating.setText(Double.toString(mMovie.getRating())+" out of 10 stars");
        mReleaseDate.setText("     " + mMovie.getReleaseDate());
        mSynopsis.setText(mMovie.getSynopsis());

        mButton = findViewById(R.id.favorite_button);
        favorite = false;
        mAppDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        if (intent.hasExtra("Movie")){
            mMovie = intent.getExtras().getParcelable("Movie");
        }

        final int loaderId = MOVIE_LOADER_ID;

        final LoaderManager.LoaderCallbacks<Movie> callbacks = MoviesDetailActivity.this;
        final Bundle bundle = null;

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Movie> detailLoader = loaderManager.getLoader(loaderId);

        if (detailLoader==null){
            loaderManager.initLoader(loaderId,bundle,callbacks);
        }else {
            loaderManager.restartLoader(loaderId,bundle,callbacks);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = mMovie.getMovieId();
                String title = mMovie.getTitle();
                String posterPath = mMovie.getPosterPath();
                String synopsis = mMovie.getSynopsis();
                Double rating = mMovie.getRating();
                String releaseDate = mMovie.getReleaseDate();

                final Movie movie = new Movie(id,title,posterPath,synopsis,rating,releaseDate);

                if (favorite){
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAppDatabase.movieDao.deleteFavoriteMovie(movie);
                            favorite=false;
                            Toast.makeText(getApplicationContext(),
                                    "Movie removed from favories!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mAppDatabase.movieDao.insertFavoriteMovie(movie);
                            favorite=true;
                            Toast.makeText(getApplicationContext(),
                                    "Movie added to favorites!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                getSupportLoaderManager().initLoader(loaderId,bundle,callbacks);

            }
        });

    }




    @NonNull
    @Override
    public Loader<Movie> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Movie>(this) {
            Movie mMovieDetail = mMovie;


            @Nullable
            @Override
            public Movie loadInBackground() {
                int movieID = mMovieDetail.getMovieId();

                URL movieReviewUrl = NetworkUtils.createUrl(MOVIE_BASE_URL+movieID+"reviews"
                +"?api_key="+API_KEY);
                try {
                    getMovieReviews(movieReviewUrl);
                    setFavorite(movieID);
                    return mMovieDetail;
                } catch (Exception e) {
                    return null;
                }

            }

            private void setFavorite(int movieID) {
                if (movieID == mAppDatabase.movieDao.checkForFavorite(movieID)){
                    favorite=true;
                }else {
                    favorite=false;
                }
            }

            private void getMovieReviews(URL movieReviewUrl) throws IOException, JSONException {
                String reviewResponse;
                reviewResponse = NetworkUtils.getMovieInfo(movieReviewUrl);
                mMovieDetail.setMovieReview(NetworkUtils.reviewJson(reviewResponse));

            }
        };
    }



    @Override
    public void onLoadFinished(@NonNull Loader<Movie> loader, Movie data) {
        mMovie = data;
        data.getMovieReview();

    }



    @Override
    public void onLoaderReset(@NonNull Loader<Movie> loader) {

    }
}
