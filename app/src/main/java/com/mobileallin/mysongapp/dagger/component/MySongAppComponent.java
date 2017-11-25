package com.mobileallin.mysongapp.dagger.component;

import com.mobileallin.mysongapp.dagger.module.AppModule;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.ItuneSongsPresenter;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.ui.activity.ItunesSongsListActivity;
import com.mobileallin.mysongapp.ui.fragment.AssetsSongsFragment;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface MySongAppComponent {

    void inject(ItunesSongsListActivity obj);

    void inject(ItuneSongsPresenter obj);

    void inject(ItunesSongsFragment obj);

    void inject(AssetsSongsFragment obj);

    void inject(AssetsSongsPresenter obj);

    ItunesSongsRepository ItunesSongsRepository();
    AssetsSongsRepositoryImpl AssetsSongsRepository();
}


