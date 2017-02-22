package com.davidtiagoconceicao.androidmovies.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Scroll listener to allow infinite scroll.
 * <p>
 * Created by david on 22/02/17.
 */

class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;

    private boolean loading = true;

    private LinearLayoutManager linearLayoutManager;
    private final LoadMoreListener listener;

    LoadMoreScrollListener(
            LinearLayoutManager linearLayoutManager,
            LoadMoreListener listener) {

        this.linearLayoutManager = linearLayoutManager;
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 5;
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {

            listener.onLoadMore();

            loading = true;
        }
    }

    interface LoadMoreListener {
        void onLoadMore();
    }
}