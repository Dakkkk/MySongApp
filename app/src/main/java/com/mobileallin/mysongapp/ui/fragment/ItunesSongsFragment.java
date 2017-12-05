package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.presentation.presenter.ItunesSongsPresenter;
import com.mobileallin.mysongapp.ui.view.ItunesSongsView;
import com.mobileallin.mysongapp.ui.view.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItunesSongsFragment extends MvpAppCompatFragment implements ItunesSongsView,
        SearchView {

    private static final String ITUNES_SONGS_LIST_STATE = "itunes_songs_state";
    private Parcelable songsListState;
    private static final String SONGS_LIST_STATE = "songs_list_state";
    private SongsAdapter songsAdapter;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.songs_list)
    RecyclerView songsRecyclerView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.empty_view)
    TextView emptyListView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.shield)
    FrameLayout shield;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.itunes_search_panel)
    EditText itunesSearchPanel;

    @Inject
    Router router;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @InjectPresenter
    ItunesSongsPresenter itunesSongsPresenter;

    @ProvidePresenter
    ItunesSongsPresenter providePresenter() {
        MySongAppComponent component = ((MySongApp) getActivity().getApplication())
                .getMySongsAppComponent();
        return new ItunesSongsPresenter(component, this);
    }

    public static ItunesSongsFragment newInstance() {
        return new ItunesSongsFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs_list, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(getString(R.string.itunes_songs));

        songsAdapter = new SongsAdapter(getContext(), emptyListView);
        int columns = getResources().getInteger(R.integer.snongs_list_columns_nr);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        emptyListView.setVisibility(View.GONE);
        songsRecyclerView.setLayoutManager(layoutManager);
        songsRecyclerView.setAdapter(songsAdapter);

        swipeRefreshView.setOnRefreshListener(() -> {
            songsListState = null;
            itunesSongsPresenter.forceLoadSongs();
            hideSwipeRefresh();
        });

        itunesSearchPanel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itunesSongsPresenter.searchItunesSong(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        songsAdapter.setItemClickListener(position -> itunesSongsPresenter.showDetails(position));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ITUNES_SONGS_LIST_STATE,
                songsRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(getContext(), getResources().getString(R.string.error_getting_itune_songs) +
                e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void hideSwipeRefresh() {
        swipeRefreshView.setRefreshing(false);
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
    public void displaySongs(List<ItunesSong> list) {
        songsAdapter.setItems(list);
        if (songsListState != null) {
            songsRecyclerView.getLayoutManager().onRestoreInstanceState(songsListState);
        }
    }

    @Override
    public void displayNoSongs() {
        Toast.makeText(getContext(), getResources().getString(R.string.server_response_no_body),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchResult(ItunesResponse searchResponse) {
        songsAdapter.setItems(searchResponse.allItuneSongs());
    }
}
