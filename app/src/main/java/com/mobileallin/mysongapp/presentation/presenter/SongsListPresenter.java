package com.mobileallin.mysongapp.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.Song;
import com.mobileallin.mysongapp.interactor.SongsInteractor;
import com.mobileallin.mysongapp.presentation.view.SongsListView;

import java.util.List;

import javax.inject.Inject;

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
        songsInteractor.updateSongs()
                .doOnSubscribe(result -> getViewState().showLoading())
                .doAfterTerminate(() -> getViewState().hideLoading())
                .subscribe(aLong -> {
                }, throwable -> Log.d(TAG, throwable.toString()), () -> {
                });

    }


/*    @Override
    public void attachView(SongsListView view) {
        super.attachView(view);

        //Dispose the observer to avoid memory leaks
        Disposable disposable = songsInteractor.subscribeToSongs()
                .subscribe(result -> {
                    this.songsList = result;
                    getViewState().showSongs(result);
                    Log.d("songsList", songsList.toString());
                });
    }*/

}
