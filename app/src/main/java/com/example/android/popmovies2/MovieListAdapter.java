package com.example.android.popmovies2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private List<Movie> mMovieList;
    private Context mContext;
    private final MovieOnClickHandler mOnClickHandler;


    public interface MovieOnClickHandler{
        void onClick(Movie movie);
    }

    public MovieListAdapter(MovieOnClickHandler clickHandler){
        this.mOnClickHandler = clickHandler;
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.movielist_item, parent,false);

        return new MovieViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(mContext)
                .load(movie.getPosterPath())
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements
    View.OnClickListener{
        public final ImageView mMovieImageView;


        public MovieViewHolder(View mItemView) {
            super(mItemView);
            mMovieImageView = (ImageView) mItemView.findViewById(R.id.movie_poster);
            mItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mOnClickHandler.onClick(mMovieList.get(pos));
        }
    }





    public void setMovieList(List<Movie> movieList){
        this.mMovieList = movieList;
        notifyDataSetChanged();
    }

    public void setContext(Context context){
        this.mContext = context;
    }
}
