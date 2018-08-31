package com.example.android.popmovies2;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {
    private String mMovieTrailerId;
    private String mMovieTrailerKey;
    private String mMovieTrailerName;

    public MovieTrailer(){}

    protected MovieTrailer(Parcel in) {
        mMovieTrailerId = in.readString();
        mMovieTrailerKey = in.readString();
        mMovieTrailerName = in.readString();
    }

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

    public String getMovieTrailerId() {
        return mMovieTrailerId;
    }

    public void setMovieTrailerId(String movieTrailerId) {
        mMovieTrailerId = movieTrailerId;
    }

    public String getMovieTrailerKey() {
        return mMovieTrailerKey;
    }

    public void setMovieTrailerKey(String movieTrailerKey) {
        mMovieTrailerKey = movieTrailerKey;
    }

    public String getMovieTrailerName() {
        return mMovieTrailerName;
    }

    public void setMovieTrailerName(String movieTrailerName) {
        mMovieTrailerName = movieTrailerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieTrailerId);
        dest.writeString(mMovieTrailerKey);
        dest.writeString(mMovieTrailerName);
    }
}
