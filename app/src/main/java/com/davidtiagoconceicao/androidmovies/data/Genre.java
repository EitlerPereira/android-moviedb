package com.davidtiagoconceicao.androidmovies.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Response for genre list.
 * <p>
 * Created by david on 22/02/17.
 */

@AutoValue
public abstract class Genre implements Parcelable{

    public abstract long id();

    public abstract String name();

    public static Genre create(long id, String name) {
        return new AutoValue_Genre(id, name);
    }
}
