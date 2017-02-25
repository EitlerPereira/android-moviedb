package com.davidtiagoconceicao.androidmovies.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.commons.DateFormatUtil;
import com.davidtiagoconceicao.androidmovies.commons.GenreUtil;
import com.davidtiagoconceicao.androidmovies.data.Genre;
import com.davidtiagoconceicao.androidmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity for displaying movie details.
 * <p>
 * Created by david on 25/02/17.
 */

public final class DetailsActivity extends AppCompatActivity {

    private static final String MOVIE_EXTRA = "MOVIE_EXTRA";

    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.details_image)
    ImageView imageView;

    @BindView(R.id.details_title_text)
    TextView titleText;

    @BindView(R.id.details_genre_label_text)
    TextView genresLabelText;

    @BindView(R.id.details_genres_text)
    TextView genresListText;

    @BindView(R.id.details_release_text)
    TextView releaseDateText;

    @BindView(R.id.details_overview_text)
    TextView overviewText;

    public static void startForMovie(Movie movie, Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(
                new ClickListener(this));

        Movie movie = getIntent()
                .getExtras()
                .getParcelable(MOVIE_EXTRA);

        bindView(movie);
    }

    private void bindView(Movie movie) {

        String posterPath = movie.posterPath();
        if (posterPath != null) {
            Picasso.with(this)
                    .load(posterPath)
                    .fit()
                    .centerInside()
                    .into(imageView);
        }

        String title = movie.title();
        toolbar.setTitle(title);

        titleText.setText(title);

        List<Genre> genres = movie.genres();

        assert genres != null;
        String quantityString = getResources()
                .getQuantityString(R.plurals.genre_plural, genres.size());

        genresLabelText.setText(quantityString);

        genresListText.setText(
                GenreUtil.createGenresSpannable(this, genres));

        releaseDateText.setText(
                DateFormatUtil.formatDate(movie.releaseDate()));

        overviewText.setText(movie.overview());
    }

    private static final class ClickListener implements View.OnClickListener {
        private final DetailsActivity detailsActivity;

        ClickListener(DetailsActivity detailsActivity) {

            this.detailsActivity = detailsActivity;
        }

        @Override
        public void onClick(View v) {
            detailsActivity.onBackPressed();
        }
    }
}
