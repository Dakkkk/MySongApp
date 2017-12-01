package com.mobileallin.mysongapp.interactor;


import javax.inject.Inject;

public class SongDetailsInteractor {

    @Inject
    ItunesSongsInteractor mainInteractor;

    public SongDetailsInteractor() {

    }

    //ToDo Use interactor for getting the song for details?
  /*  public ItunesSong getSong(long id) {
      return mainInteractor.getChosenItunesSong(id);
    }*/
}
