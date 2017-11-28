package com.mobileallin.mysongapp.interactor;


import com.mobileallin.mysongapp.data.model.ItunesSong;

import javax.inject.Inject;

public class SongDetailsInteractor {

    @Inject
    ItunesSongsInteractor mainInteractor;

    public SongDetailsInteractor() {

    }

    public ItunesSong getSong(long id) {
      return mainInteractor.getChosenItunesSong(id);
    }

}