package com.example.android.popmovies2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
    private MovieReviewAdapter mMovieReviewAdapter;
    private Movie mMovie;
    private static final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);

        Movie mMovie = (Movie) getIntent().getParcelableExtra(MOVIE_KEY);

        mPoster = (ImageView) findViewById(R.id.movie_poster_detail);
        mRating = (TextView) findViewById(R.id.movie_rating_detail);
        mReleaseDate = (TextView) findViewById(R.id.movie_release_date_detail);
        mSynopsis = (TextView) findViewById(R.id.movie_synopsis_detail);


        Picasso.with(this).load(mMovie.getPosterPath()).into(mPoster);
        mRating.setText(Double.toString(mMovie.getRating())+" out of 10 stars");
        mReleaseDate.setText("     " + mMovie.getReleaseDate());
        mSynopsis.setText(mMovie.getSynopsis());
        mReview = findViewById(R.id.movie_review_text);

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

                URL movieReviewUrl = NetworkUtils.createUrl(MOVIE_BASE_URL+movieID+"reviews");
                try {
                    getMovieReviews(movieReviewUrl);
                    return mMovieDetail;
                } catch (Exception e) {
                    return null;
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
