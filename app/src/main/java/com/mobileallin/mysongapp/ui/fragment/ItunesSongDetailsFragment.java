package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mobileallin.mysongapp.MySongApp;
import com.mobileallin.mysongapp.R;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.presentation.presenter.ItunesSongDetailsPresenter;
import com.mobileallin.mysongapp.ui.view.BaseSongDetailsView;

import butterknife.ButterKnife;

/**
 * Created by Dawid on 2017-11-27.
 */

public class ItunesSongDetailsFragment extends MvpAppCompatFragment implements BaseSongDetailsView {


    @InjectPresenter
    ItunesSongDetailsPresenter presenter;

    @ProvidePresenter
    ItunesSongDetailsPresenter providePresenter() {
        MySongAppComponent component = ((MySongApp) getActivity().getApplication()).getMySongsAppComponent();
        return new ItunesSongDetailsPresenter(component);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_details, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(getString(R.string.song_details));

        return view;
    }

    @Override
    public void showChosenRecipeDetails(ItunesSong itunesSong) {

    }
}
