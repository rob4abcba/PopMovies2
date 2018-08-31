package com.example.android.popmovies2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieReviewAdapter extends
        RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {
    private MovieReview[] mMovieReviews;

    public MovieReviewAdapter(){}


    @NonNull
    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moviereviewlist_item, parent, false);
        return new MovieReviewAdapterViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapterViewHolder holder, int position) {
        holder.mReviewContent.setText(mMovieReviews[position].getReviewContent());

    }

    @Override
    public int getItemCount() {
        return mMovieReviews.length;
    }

    public void setMovieReviews(MovieReview[] movieReviews) {
        mMovieReviews = movieReviews;
        notifyDataSetChanged();
    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mReviewContent;

        public MovieReviewAdapterViewHolder(View view){
            super(view);
            mReviewContent = view.findViewById(R.id.movie_review_text);

        }
    }
}
