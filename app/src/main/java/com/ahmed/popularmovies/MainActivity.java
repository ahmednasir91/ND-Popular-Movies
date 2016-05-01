package com.ahmed.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.ahmed.popularmovies.dto.Movie;
import com.ahmed.popularmovies.dto.Response;
import com.ahmed.popularmovies.network.TheMovieDBGateway;
import com.ahmed.popularmovies.ui.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String THE_MOVIE_DB_URL = "http://api.themoviedb.org/";
    private static final String API_KEY = ""; // Add API Key here

    private static final String TAG = MainActivity.class.getName();

    private TheMovieDBGateway mTheMovieDBGateway;
    private MovieAdapter mMovieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGateway();

        loadMovies();

        initUI();
    }

    private void initUI() {
        movieList = new ArrayList<>(0);
        mMovieAdapter = new MovieAdapter(MainActivity.this, movieList);

        GridView moviesGV = ((GridView) findViewById(R.id.movies_gv));
        moviesGV.setAdapter(mMovieAdapter);
    }

    private void loadMovies() {
        Call<Response<List<Movie>>> responseCall = mTheMovieDBGateway.popularMovies(API_KEY);
        responseCall.enqueue(new Callback<Response<List<Movie>>>() {
            @Override
            public void onResponse(Call<Response<List<Movie>>> call, retrofit2.Response<Response<List<Movie>>> response) {
                showMoviesList(response.body().getResults());

                Log.i(TAG, "Done");
            }

            @Override
            public void onFailure(Call<Response<List<Movie>>> call, Throwable t) {
                Log.e(TAG, "There was an error downloading movies list.");
                Toast.makeText(MainActivity.this, "An error occured downloading list.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showMoviesList(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        mMovieAdapter.notifyDataSetChanged();
    }

    private void initGateway() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(THE_MOVIE_DB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTheMovieDBGateway = retrofit.create(TheMovieDBGateway.class);
    }


}
