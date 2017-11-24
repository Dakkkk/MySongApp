package com.mobileallin.mysongapp.dagger.component;

import com.mobileallin.mysongapp.dagger.module.AppModule;
import com.mobileallin.mysongapp.presentation.presenter.SongsListPresenter;
import com.mobileallin.mysongapp.ui.activity.ItunesSongsListActivity;
import com.mobileallin.mysongapp.ui.fragment.ItunesSongsFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class})
public interface MySongAppComponent {

    void inject(ItunesSongsListActivity obj);

    void inject(SongsListPresenter obj);

    void inject(ItunesSongsFragment obj);


/*
    SongsRepository SongssRepository();
*/
}


