package com.mobileallin.mysongapp.dagger.component;

import com.mobileallin.mysongapp.dagger.module.AppModule;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongDetailsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.ItuneSongsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.ItunesSongDetailsPresenter;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.activity.AssetsSongDetailsActivity;
import com.mobileallin.mysongapp.ui.activity.SongDetailsActivity;
import com.mobileallin.mysongapp.ui.activity.SongsListActivity;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

import javax.inject.Singleton;

import dagger.Component;


@SuppressWarnings("unused")
@Singleton
@Component(modules = {AppModule.class})
public interface MySongAppComponent {

    void inject(SongsListActivity obj);

    void inject(SongDetailsActivity obj);

    void inject(AssetsSongDetailsActivity obj);

    void inject(ItuneSongsPresenter obj);

    void inject(ItunesSongsFragment obj);

    void inject(AssetsSongsFragment obj);

    void inject(AssetsSongsPresenter obj);

    void inject(ItunesSongDetailsPresenter obj);

    void inject(AssetsSongDetailsPresenter obj);

    ItunesSongsRepository ItunesSongsRepository();

    AssetsSongsRepositoryImpl AssetsSongsRepository();

    Router Router();
}


