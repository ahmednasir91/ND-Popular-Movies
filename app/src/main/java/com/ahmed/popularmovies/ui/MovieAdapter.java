package com.ahmed.popularmovies.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ahmed.popularmovies.dto.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ahmed.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String THE_MOVIE_DB_BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context, List<Movie> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(getContext());
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso
                .with(getContext())
                .load(getItem(position).posterUrl(THE_MOVIE_DB_BASE_IMAGE_URL))
                .resize(450, 450)
                .into(imageView);

        return imageView;
    }
}
