package com.ahmed.popularmovies.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmed.
 */
public class Movie {
    private int id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("release_date")
    private String releaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", voteAverage=" + voteAverage +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public String posterUrl(String baseUrl) {
        return baseUrl + posterPath;
    }
}