package com.mobileallin.mysongapp.dagger.component;

import com.mobileallin.mysongapp.dagger.module.AppModule;
import com.mobileallin.mysongapp.presentation.presenter.SongsListPresenter;
import com.mobileallin.mysongapp.ui.activity.ItunesSongsListActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface MySongAppComponent {

    void inject(ItunesSongsListActivity obj);

    void inject(SongsListPresenter obj);

/*
    SongsRepository SongssRepository();
*/
}


