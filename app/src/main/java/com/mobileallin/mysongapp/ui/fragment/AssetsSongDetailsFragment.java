package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongDetailsPresenter;
import com.mobileallin.mysongapp.ui.view.BaseAssetsDetailsView;
import com.mobileallin.mysongapp.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AssetsSongDetailsFragment extends MvpAppCompatFragment implements BaseAssetsDetailsView {

    @InjectPresenter
    AssetsSongDetailsPresenter presenter;

    @ProvidePresenter
    AssetsSongDetailsPresenter providePresenter() {
        MySongAppComponent component = ((MySongApp) getActivity().getApplication()).getMySongsAppComponent();
        return new AssetsSongDetailsPresenter(component, this);
    }

    @BindView(R.id.song_details_title)
    TextView songTitleTextView;
    @BindView(R.id.song_details_author)
    TextView songAuthorTextView;
    @BindView(R.id.song_details_release_date)
    TextView songDateTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null) {
            presenter.init(args.getLong(Keys.ITUNE_SONG_ID));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_details, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(presenter.getSongTitle());
        return view;
    }

    @Override
    public void showSongDetails(AssetsSong assetsSong) {
        getActivity().setTitle(assetsSong.title());
        Log.d("showAssetsSongDetails: ", assetsSong.toString());
        songTitleTextView.setText(assetsSong.title());
        songAuthorTextView.setText(assetsSong.author());
        songDateTextView.setText(String.valueOf(assetsSong.releaseDate()));
    }
}