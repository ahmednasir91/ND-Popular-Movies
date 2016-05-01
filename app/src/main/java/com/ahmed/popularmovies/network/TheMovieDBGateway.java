package com.ahmed.popularmovies.network;


import com.ahmed.popularmovies.dto.Movie;
import com.ahmed.popularmovies.dto.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ahmed.
 */
public interface TheMovieDBGateway {

    @GET("/3/movie/popular")
    Call<Response<List<Movie>>> popularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/top_rated")
    Call<Response<List<Movie>>> topRatedMovies(@Query("api_key") String apiKey);

}
