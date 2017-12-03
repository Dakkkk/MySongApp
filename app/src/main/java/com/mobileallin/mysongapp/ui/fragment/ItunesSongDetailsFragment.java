package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.dagger.module.GlideApp;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.presentation.presenter.ItunesSongDetailsPresenter;
import com.mobileallin.mysongapp.ui.view.BaseItunesDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItunesSongDetailsFragment extends MvpAppCompatFragment implements BaseItunesDetailsView {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @InjectPresenter
    ItunesSongDetailsPresenter presenter;

    @ProvidePresenter
    ItunesSongDetailsPresenter providePresenter() {
        MySongAppComponent component = ((MySongApp) getActivity().getApplication())
                .getMySongsAppComponent();
        return new ItunesSongDetailsPresenter(component);
    }

    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_image)
    ImageView songImage;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_title)
    TextView songTitleTextView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_author)
    TextView songAuthorTextView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_release_date)
    TextView songDateTextView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_collection_name)
    TextView songCollectionTextView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_genre_name)
    TextView songGenreTextView;
    @SuppressWarnings({"WeakerAccess", "CanBeFinal"})
    @BindView(R.id.song_details_country)
    TextView songCountryTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_details, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(presenter.getSongTitle());
        return view;
    }

    @Override
    public void showSongDetails(ItunesSong itunesSong) {
        getActivity().setTitle(itunesSong.title());

        GlideApp.with(this)
                .load(itunesSong.thumbnailUrl())
                .placeholder(R.drawable.ic_music_video_black_48px)
                .error(R.drawable.ic_music_video_black_48px)
                .into(songImage);

        songTitleTextView.setText(itunesSong.title());
        songAuthorTextView.setText(itunesSong.author());
        songDateTextView.setText(itunesSong.releaseDate());
        songCollectionTextView.setText(itunesSong.collectionName());
        songGenreTextView.setText(itunesSong.genreName());
        songCountryTextView.setText(itunesSong.country());
    }
}
