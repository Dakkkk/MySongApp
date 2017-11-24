package com.mobileallin.mysongapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.interactor.SongsInteractor;
import com.mobileallin.mysongapp.presentation.view.SongsListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Class responsible for showing the data in the main view
 */
@InjectViewState
public class SongsListPresenter extends MvpPresenter<SongsListView> {

    private static final String TAG = "SongsListPresenter";
    private List<Song> songsList;
    private SongsListView view;

    @Inject
    SongsInteractor songsInteractor;

    public SongsListPresenter(MySongAppComponent component, SongsListView view) {
        component.inject(this);
        this.view = view;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

    }

    @Override
    public void attachView(SongsListView view) {
        super.attachView(view);

        //Dispose the observer to avoid memory leaks
        Disposable disposable = songsInteractor.loadSongs(view);
    }

}
