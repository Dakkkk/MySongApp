package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.presentation.presenter.ItuneSongsPresenter;
import com.mobileallin.mysongapp.ui.view.SongsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 2017-11-24.
 */

public class ItunesSongsFragment extends MvpAppCompatFragment implements SongsListView {


    @InjectPresenter
    ItuneSongsPresenter ituneSongsPresenter;

    private SongsAdapter songsAdapter;

    @BindView(R.id.songs_list)
    RecyclerView songsRecyclerView;
    @BindView(R.id.empty_view)
    TextView emptyListView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.shield)
    FrameLayout shield;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshView;

    private Parcelable songsListState;
    private static final String SONGS_LIST_STATE = "songs_list_state";

    @ProvidePresenter
    ItuneSongsPresenter providePresenter() {
        MySongAppComponent component = ((MySongApp) getActivity().getApplication()).getMySongsAppComponent();
        return new ItuneSongsPresenter(component, this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            songsListState = savedInstanceState.getParcelable(SONGS_LIST_STATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs_list, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(getString(R.string.itunes_songs));

        songsAdapter = new SongsAdapter(getContext(), emptyListView);
/*
        songsAdapter.setItemClickListener(position -> songsListPresenter.enterDetailActivity(position));
*/
        int columns = getResources().getInteger(R.integer.snongs_list_columns_nr);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        emptyListView.setVisibility(View.GONE);
        songsRecyclerView.setLayoutManager(layoutManager);
        songsRecyclerView.setAdapter(songsAdapter);

        return view;
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(getContext(), "Error!, Couldn't get the Itunes songs! Message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        enableProgressBar(true);
    }

    @Override
    public void hideLoading() {
        enableProgressBar(false);
    }

    private void enableProgressBar(boolean enable) {
        int visibility = enable ? View.VISIBLE : View.GONE;
        progressBar.setVisibility(visibility);
       /* shield.setVisibility(visibility);*/
        shield.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displaySongs(List<Song> list) {
        songsAdapter.setItems(list);
        if (songsListState != null) {
            songsRecyclerView.getLayoutManager().onRestoreInstanceState(songsListState);
        }
    }

    @Override
    public void displayNoSongs() {
        Toast.makeText(getContext(), "OOOps, there are no Itunes Songs", Toast.LENGTH_SHORT).show();
    }
}

