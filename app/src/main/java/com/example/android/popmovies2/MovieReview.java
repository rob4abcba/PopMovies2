package com.example.android.popmovies2;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable {
    private String mReviewContent;

    public MovieReview(){}

    public MovieReview(String reviewContent){
        mReviewContent = reviewContent;
    }

    private MovieReview(Parcel in){
        mReviewContent = in.readString();
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel in) {
            return new MovieReview(in);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mReviewContent);
    }
}
