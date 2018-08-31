package com.example.android.popmovies2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieTrailerAdapter extends
        RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerAdapterViewHolder> {
    private MovieTrailer[] mMovieTrailers;
    private final MovieTrailerOnClickHandler mClickHandler;
    private static final String PREVIEW_PATH = "https://img.youtube.com/vi/";
    private static final String DEFAULT_JPG = "/default.jpg";


    public interface MovieTrailerOnClickHandler{
        void onClick(MovieTrailer movieTrailerKey);
    }

    public MovieTrailerAdapter(MovieTrailerOnClickHandler clickHandler){
        this.mClickHandler = clickHandler;
    }


    @NonNull
    @Override
    public MovieTrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailer_preview, parent, false);
        return new MovieTrailerAdapterViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapterViewHolder holder, int position) {
        String previewUrl = PREVIEW_PATH +
                mMovieTrailers[position].getMovieTrailerKey() + DEFAULT_JPG;
        Picasso.with(holder.mTrailerPreview.getContext())
                .load(previewUrl)
                .into(holder.mTrailerPreview);


    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.length;
    }

    public void setMovieTrailers(MovieTrailer[] movieTrailers) {
        mMovieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {
        final ImageView mTrailerPreview;

        public MovieTrailerAdapterViewHolder(View itemView) {
            super(itemView);
            mTrailerPreview = itemView.findViewById(R.id.movie_trailer_preview);
            mTrailerPreview.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MovieTrailer movieTrailer = mMovieTrailers[getAdapterPosition()];
            mClickHandler.onClick(movieTrailer);

        }
    }
}
