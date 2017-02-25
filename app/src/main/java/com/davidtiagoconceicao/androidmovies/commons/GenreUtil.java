package com.davidtiagoconceicao.androidmovies.commons;

import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;

import com.davidtiagoconceicao.androidmovies.R;
import com.davidtiagoconceicao.androidmovies.data.Genre;

import java.util.List;

/**
 * Utility class for genres.
 * <p>
 * Created by david on 25/02/17.
 */

public final class GenreUtil {

    private static final String SEPARATOR = " ";

    public static CharSequence createGenresSpannable(
            Context context,
            List<Genre> genres) {

        int accentColor;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            accentColor = context.getResources()
                    .getColor(
                            R.color.accent,
                            context.getTheme());
        } else {
            //noinspection deprecation
            accentColor = context.getResources()
                    .getColor(R.color.accent);
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        for (Genre genre : genres) {

            String name = genre.name().toUpperCase();
            name = SEPARATOR + name + SEPARATOR;

            SpannableString styledString = new SpannableString(name);

            styledString.setSpan(
                    new BackgroundColorSpan(accentColor),
                    0,
                    name.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            spannableStringBuilder.append(styledString);
            spannableStringBuilder.append(SEPARATOR);
        }

        return spannableStringBuilder;
    }
}
