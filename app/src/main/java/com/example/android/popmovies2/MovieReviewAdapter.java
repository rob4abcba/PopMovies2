package com.example.android.popmovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MovieReviewAdapter extends
        RecyclerView.Adapter<MovieReviewAdapter.MovieReviewAdapterViewHolder> {
    private List<MovieReview> mMovieReviews;
    private Context mContext;

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
        holder.mReviewContent.setText(mMovieReviews.get(position).getReviewContent());

    }

    @Override
    public int getItemCount() {
        if (null == mMovieReviews) return 0;
        return mMovieReviews.size();
    }

    public void setMovieReviews(List<MovieReview> movieReviews) { 
        mMovieReviews = movieReviews;
        notifyDataSetChanged();
    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder{
        public final TextView mReviewContent;

        public MovieReviewAdapterViewHolder(View view){
            super(view);
            mReviewContent = view.findViewById(R.id.movie_review_recyclerview);

        }
    }

    public void setContext(Context context){
        this.mContext = context;
    }
}
