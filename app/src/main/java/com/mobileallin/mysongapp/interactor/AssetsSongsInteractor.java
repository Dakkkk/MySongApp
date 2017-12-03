package com.mobileallin.mysongapp.interactor;

import android.content.Context;
import android.util.Log;

import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.helper.AssetsSongsStringParser;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public class AssetsSongsInteractor {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private AssetsSongsStringParser assetsSongsStringParser;

    private AssetsSongsRepositoryImpl assetsRepository;

    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    public AssetsSongsInteractor(AssetsSongsRepositoryImpl assetsRepository, @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
                                 ){
        this.assetsRepository = assetsRepository;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }


    public void loadSongs(AssetsSongsView assetsSongsView, ArrayList<AssetsSong> assetsSongs) {
        Log.d("loadSongs", "called");
        assetsSongsView.hideLoading();
        assetsSongsView.displaySongs(assetsSongs);
    }

   /*     compositeDisposable.add(assetsSongsStringParser.parseStringToAssetsSongList(songAssetsString)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribeWith(new DisposableSingleObserver<ItunesResponse>() {
                    @Override
                    public void onSuccess(@NonNull ItunesResponse songs) {
                        allItunesSongs = convertToItuneSongsList(songs.allItuneSongs());
                        view.hideLoading();
                        view.displaySongs(allItunesSongs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.displayError(e);
                    }
                }));
        return null;
    }*/


    public Single<List<AssetsSong>> getParsedSongs(Context context) {
/*
        Log.d("songAssetsString1", songAssetsString.toString());
*/
        AssetsSongsStringParser assetsSongsStringParser = new AssetsSongsStringParser();
        return Single.fromCallable(() -> {
            try {
                List<AssetsSong> assetsSongs;
                String songAssetsString = assetsRepository.loadJSONFromAsset(context);
                System.out.println("Thread db: " + Thread.currentThread().getId());
                assetsSongs = assetsSongsStringParser.parseStringToAssetsSongList(songAssetsString);
                Log.d("songAssetsStringToAss", assetsSongs.toString());
                return assetsSongs;
            } catch (Exception e) {
                Log.d("songAssetsString1", e.toString());
                throw new RuntimeException();
            }
        });
    }

}
