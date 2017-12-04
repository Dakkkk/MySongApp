package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;
import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.helper.AssetsSongTitleComparator;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;
import com.mobileallin.mysongapp.utils.Keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class AssetsSongsPresenter extends MvpPresenter<AssetsSongsView> {

    private final AssetsSongsView view;
    private final Context context;
    private ArrayList<AssetsSong> allAssetsSongsArrayList;
    private ArrayList<AssetsSong> assetsSearchList;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final Scheduler mainScheduler;

    @SuppressWarnings("WeakerAccess")
    @Inject
    public AssetsSongsInteractor assetsSongsInteractor;

    @SuppressWarnings("WeakerAccess")
    @Inject
    Router router;

    public AssetsSongsPresenter(MySongAppComponent component, AssetsSongsView view,
                                Context context,
                                Scheduler mainScheduler) {
        component.inject(this);
        this.view = view;
        this.context = context;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void attachView(AssetsSongsView view) {
        super.attachView(view);
        view.showLoading();
        loadFormattedAssetsSongs();
        loadSongs(view, allAssetsSongsArrayList);
        view.hideLoading();
    }

    public void loadSongs(AssetsSongsView assetsSongsView, ArrayList<AssetsSong> assetsSongs) {
        assetsSongsView.displaySongs(assetsSongs);
    }

    @Override
    public void detachView(AssetsSongsView view) {
        super.detachView(view);
        unsubscribe();
    }

    private void unsubscribe() {
        compositeDisposable.clear();
    }

    private void loadFormattedAssetsSongs() {
        if (allAssetsSongsArrayList == null || allAssetsSongsArrayList.isEmpty()) {
            loadAssetsSongs();
        }
    }

    public void loadAssetsSongs() {
        compositeDisposable.add(assetsSongsInteractor.getParsedSongs(context)
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<AssetsSong>>() {
                    @Override
                    public void onSuccess(List<AssetsSong> songList) {
                        allAssetsSongsArrayList = (ArrayList<AssetsSong>) songList;
                        sortAssetsSongs(allAssetsSongsArrayList);
                        if (songList.isEmpty()) {
                            view.displayNoSongs();
                        } else {
                            view.displaySongs((ArrayList<AssetsSong>) songList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayError();
                    }
                }));
    }

    private void sortAssetsSongs(ArrayList<AssetsSong> allAssetsSongsArrayList) {
        Collections.sort(allAssetsSongsArrayList, new AssetsSongTitleComparator());
    }

    private ArrayList<AssetsSong> getAssetsSongArrayList() {
        return allAssetsSongsArrayList;
    }

    public void showAssetsSearchResults(ArrayList<AssetsSong> searchResponse) {
        loadSongs(view, searchResponse);
    }

    public ArrayList<AssetsSong> searchAssetsSong(String s) {
        assetsSearchList = new ArrayList<>();
        //ToDo resolve this warning
        for (AssetsSong song : allAssetsSongsArrayList) {
            if (song.author().toLowerCase().contains(s) ||
                    song.title().toLowerCase().contains(s) ||
                    String.valueOf(song.releaseDate()).toLowerCase().contains(s)) {
                assetsSearchList.add(song);
            }
        }
        return assetsSearchList;
    }

    public void showDetails(int position) {
        ArrayList<AssetsSong> currentSongsList;
        if (assetsSearchList != null && !assetsSearchList.isEmpty()) {
            currentSongsList = assetsSearchList;
        } else {
            currentSongsList = getAssetsSongArrayList();
        }
        putDetailArgsToBundle(currentSongsList, position);
    }

    private void putDetailArgsToBundle(ArrayList<AssetsSong> currentSongsList, int position) {
        Bundle args = new Bundle();
        args.putLong(ArgumentKeys.ID, currentSongsList.get(position).id());
        args.putString(ArgumentKeys.TITLE, currentSongsList.get(position).title());
        args.putString(ArgumentKeys.AUTHOR, currentSongsList.get(position).author());
        args.putString(ArgumentKeys.RELEASE_DATE, currentSongsList.get(position).releaseDate());
        args.putString(ArgumentKeys.YEAR, currentSongsList.get(position).year());
        args.putString(ArgumentKeys.FIRST, currentSongsList.get(position).first());
        args.putString(ArgumentKeys.PLAY_COUNT, currentSongsList.get(position).playCount());
        args.putString(Keys.IS_ASSETS_SONG, Keys.IS_ASSETS_SONG);
        router.putCommand(Command.SHOW_ASSETS_SONG_DETAILS,
                AssetsSongDetailsPresenter.class.getName(), args);
    }

    public void forceLoadSongs() {
        loadSongs(view, allAssetsSongsArrayList);
    }
}
