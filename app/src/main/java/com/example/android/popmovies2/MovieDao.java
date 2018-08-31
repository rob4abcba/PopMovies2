package com.example.android.popmovies2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM `favorite movies` order by title")
    LiveData<Movie[]> loadLiveMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFavoriteMovie(Movie movie);

    @Query("SELECT * FROM `favorite movies` WHERE movieId = :id")
    Movie loadMovieByMovieId(String id);

    @Query("SELECT movieId FROM `favorite movies` WHERE movieId = :id")
    long checkForFavorite(long id);

    @Delete
    void deleteFavoriteMovie(Movie movie);
}
