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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dawid on 2017-11-25.
 */

public class AssetsSongsFragment extends MvpAppCompatFragment implements AssetsSongsView {


    @InjectPresenter
    AssetsSongsPresenter assetsSongsPresenter;

    AssetsSongsRepositoryImpl assetsSongsRepositoryImpl;

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
    AssetsSongsPresenter providePresenter() {
        //ToDo this shouldn't be initialized here, change this later on: inject or sth
        assetsSongsRepositoryImpl = new AssetsSongsRepositoryImpl();

        MySongAppComponent component = ((MySongApp) getActivity().getApplication()).getMySongsAppComponent();
        return new AssetsSongsPresenter(component, this, assetsSongsRepositoryImpl, getContext());
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
        View view = inflater.inflate(R.layout.fragment_assets_songs, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle(getString(R.string.assets_songs));
        songsAdapter = new SongsAdapter(getContext(), emptyListView);
/*
        songsAdapter.setItemClickListener(position -> songsListPresenter.enterDetailActivity(position));
*/
        int columns = getResources().getInteger(R.integer.snongs_list_columns_nr);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        songsRecyclerView.setLayoutManager(layoutManager);
        songsRecyclerView.setAdapter(songsAdapter);

        return view;
    }


    private void enableProgressBar(boolean enable) {
        int visibility = enable ? View.VISIBLE : View.GONE;
        progressBar.setVisibility(visibility);
       /* shield.setVisibility(visibility);*/
        shield.setVisibility(View.INVISIBLE);
    }

   /* @Override
    public Context getAssetsFragmentContext() {
        return this.getContext();
    }*/

    @Override
    public void displaySongs(List<Song> list) {
/*
        String a= assetsSongsRepositoryImpl.inputStreamToString(getContext().getResources().getAssets().open("file_name.json"));
*/

        songsAdapter.setItems(list);
        if (songsListState != null) {
            songsRecyclerView.getLayoutManager().onRestoreInstanceState(songsListState);
        }
    }

    @Override
    public void displayNoSongs() {

    }
}