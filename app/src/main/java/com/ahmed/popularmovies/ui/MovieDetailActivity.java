package com.ahmed.popularmovies.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmed.popularmovies.R;
import com.ahmed.popularmovies.dto.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie mCurrentMovie;

    @BindView(R.id.average_rating)
    TextView mAverageRatingTV;

    @BindView(R.id.synopsis)
    TextView mSynopsisTV;

    @BindView(R.id.release_date)
    TextView mReleaseDateTV;

    @BindView(R.id.movie_poster_iv)
    ImageView mMoviePosterIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        restoreState(savedInstanceState);

        initUI();
    }

    private void initUI() {
        setTitle(mCurrentMovie.getOriginalTitle());

        Picasso.with(MovieDetailActivity.this)
                .load(mCurrentMovie.posterUrl(MainActivity.THE_MOVIE_DB_BASE_IMAGE_URL))
                .resize(450, 450)
                .error(R.drawable.imageNotFound)
                .into(mMoviePosterIV);

        mAverageRatingTV.setText(String.format("%s/10", mCurrentMovie.getVoteAverage()));
        mSynopsisTV.setText(mCurrentMovie.getOverview());
        mReleaseDateTV.setText(mCurrentMovie.getReleaseDateFormatted());
    }

    private void restoreState(Bundle savedInstanceState) {
        if (getIntent().hasExtra(MainActivity.EXTRA_MOVIE)) {
            mCurrentMovie = (Movie) getIntent().getSerializableExtra(MainActivity.EXTRA_MOVIE);
        } else {
            mCurrentMovie = (Movie) savedInstanceState.getSerializable(MainActivity.EXTRA_MOVIE);
        }
    }
}
