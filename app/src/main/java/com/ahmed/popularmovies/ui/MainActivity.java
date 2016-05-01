package com.ahmed.popularmovies.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ahmed.popularmovies.BuildConfig;
import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.dto.Movie;
import com.ahmed.popularmovies.dto.Response;
import com.ahmed.popularmovies.network.TheMovieDBGateway;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String THE_MOVIE_DB_URL = "http://api.themoviedb.org/";
    private static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_KEY; // Add API Key here

    private static final String TAG = MainActivity.class.getName();
    private static final String SELECTED_LIST = "com.ahmed.popularmovies.key.SELECTED_LIST";
    private static final String POPULAR_MOVIES_TAG = "popular-movies";
    private static final String TOP_RATED_MOVIES_TAG = "top-rated-movies";


    public static final String EXTRA_MOVIE = "com.ahmed.popularmovies.extra.MOVIE";
    public static final String THE_MOVIE_DB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    private TheMovieDBGateway mTheMovieDBGateway;
    private MovieAdapter mMovieAdapter;
    private List<Movie> movieList;
    private String currentList = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restoreState(savedInstanceState);

        initGateway();

        loadMovies();

        initUI();
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentList = savedInstanceState.getString(SELECTED_LIST);
        }
    }

    private void loadMovies() {
        if (TOP_RATED_MOVIES_TAG.equalsIgnoreCase(currentList)) {
            showTopRatedMovies();
        } else {
            showPopularMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SELECTED_LIST, currentList);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentList = savedInstanceState.getString(SELECTED_LIST);

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void initUI() {
        movieList = new ArrayList<>(0);
        mMovieAdapter = new MovieAdapter(MainActivity.this, movieList);

        GridView moviesGV = ((GridView) findViewById(R.id.movies_gv));
        moviesGV.setAdapter(mMovieAdapter);

        moviesGV.setOnItemClickListener(MainActivity.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
                showPopularMovies();
                break;
            case R.id.action_top_rated_movies:
                showTopRatedMovies();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTopRatedMovies() {
        currentList = TOP_RATED_MOVIES_TAG;

        Call<Response<List<Movie>>> responseCall = mTheMovieDBGateway.topRatedMovies(API_KEY);
        responseCall.enqueue(movieListDownloadedCallback);
    }

    private void showPopularMovies() {
        currentList = POPULAR_MOVIES_TAG;

        Call<Response<List<Movie>>> responseCall = mTheMovieDBGateway.popularMovies(API_KEY);
        responseCall.enqueue(movieListDownloadedCallback);
    }

    private Callback<Response<List<Movie>>> movieListDownloadedCallback = new Callback<Response<List<Movie>>>() {
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
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movieList.get(position));
        startActivity(intent);
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        setTitle(title);
    }
}
