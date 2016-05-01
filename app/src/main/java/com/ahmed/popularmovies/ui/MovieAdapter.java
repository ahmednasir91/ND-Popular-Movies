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

        Picasso.with(getContext())
                .load(getItem(position).posterUrl(MainActivity.THE_MOVIE_DB_BASE_IMAGE_URL))
                .resize(450, 450)
                .into(imageView);

        return imageView;
    }


}
