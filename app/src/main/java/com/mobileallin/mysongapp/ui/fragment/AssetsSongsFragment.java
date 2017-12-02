package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobileallin.mysongapp.utils.Keys.ASSETS_SONG_ID;


public class AssetsSongsFragment extends MvpAppCompatFragment implements AssetsSongsView {

    private static final String ASSETS_SONGS_LIST_STATE = "assets_songs_state";
    AssetsSongsRepositoryImpl assetsSongsRepositoryImpl;
    private AssetsSongsAdapter assetsSongsAdapter;
    private ArrayList<AssetsSong> allAssetsSongs;
    private Parcelable songsListState;
    private static final String SONGS_LIST_STATE = "songs_list_state";

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
    @BindView(R.id.assets_search_panel)
    EditText assetsSearchPanel;

    @InjectPresenter
    AssetsSongsPresenter assetsSongsPresenter;

    @ProvidePresenter
    AssetsSongsPresenter providePresenter() {
        //ToDo this shouldn't be initialized here, change this later on: inject or sth
        assetsSongsRepositoryImpl = new AssetsSongsRepositoryImpl();

        MySongAppComponent component = ((MySongApp) getActivity().getApplication()).getMySongsAppComponent();
        return new AssetsSongsPresenter(component, this, assetsSongsRepositoryImpl, getContext());
    }

    public static AssetsSongsFragment newInstance(long songId) {
        Bundle args = new Bundle();
        args.putLong(ASSETS_SONG_ID, songId);
        AssetsSongsFragment f = new AssetsSongsFragment();
        f.setArguments(args);
        return f;
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
        assetsSongsAdapter = new AssetsSongsAdapter(emptyListView);
        allAssetsSongs = assetsSongsPresenter.getAssetsSongArrayList();
        assetsSongsAdapter.setItemClickListener(position -> assetsSongsPresenter.showDetails(position));
        int columns = getResources().getInteger(R.integer.snongs_list_columns_nr);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        songsRecyclerView.setLayoutManager(layoutManager);
        songsRecyclerView.setAdapter(assetsSongsAdapter);

        assetsSearchPanel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<AssetsSong> searchAssetsSongsResults =
                        assetsSongsPresenter.searchAssetsSong(charSequence.toString());
                assetsSongsPresenter.showAssetsSearchResults(searchAssetsSongsResults);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ASSETS_SONGS_LIST_STATE,
                songsRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    private void enableProgressBar(boolean enable) {
        int visibility = enable ? View.VISIBLE : View.GONE;
        progressBar.setVisibility(visibility);
       /* shield.setVisibility(visibility);*/
        shield.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayNoSongs() {
        Toast.makeText(getContext(), getString(R.string.songs_list_empty), Toast.LENGTH_LONG).show();
    }

    @Override
    public void displaySongs(ArrayList<AssetsSong> assetsSongs) {
        assetsSongsAdapter.setItems(assetsSongs);
        if (songsListState != null) {
            songsRecyclerView.getLayoutManager().onRestoreInstanceState(songsListState);
        }
    }

    @Override
    public void showLoading() {
        enableProgressBar(true);
    }

    @Override
    public void hideLoading() {
        enableProgressBar(false);
    }
}