package com.example.android.popmovies2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favorite movies")
public class Movie implements Parcelable{

    @PrimaryKey
    private int movieId;
    private String title;
    private String posterPath;
    private String synopsis;
    private double rating;
    private String releaseDate;


    public Movie(){}


    public Movie(int id, String movieTitle, String moviePoster, String movieSynopsis,
                 double movieRating, String movieReleaseDate){
        this.movieId = id;
        this.title = movieTitle;
        this.posterPath = moviePoster;
        this.synopsis = movieSynopsis;
        this.rating = movieRating;
        this.releaseDate = movieReleaseDate;


    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public static final Creator<Movie> CREATOR = new Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Ignore
    private Movie(Parcel in){
        movieId = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        synopsis = in.readString();
        releaseDate = in.readString();
        rating = in.readDouble();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(synopsis);
        dest.writeString(releaseDate);
        dest.writeDouble(rating);


    }
}
