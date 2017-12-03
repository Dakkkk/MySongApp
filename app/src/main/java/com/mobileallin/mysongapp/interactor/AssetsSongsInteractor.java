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


public class AssetsSongsInteractor {

    private final AssetsSongsRepositoryImpl assetsRepository;

    public AssetsSongsInteractor(AssetsSongsRepositoryImpl assetsRepository,
                                 @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler
                                 ){
        this.assetsRepository = assetsRepository;
    }

    public void loadSongs(AssetsSongsView assetsSongsView, ArrayList<AssetsSong> assetsSongs) {
        Log.d("loadSongs", "called");
        assetsSongsView.hideLoading();
        assetsSongsView.displaySongs(assetsSongs);
    }

    public Single<List<AssetsSong>> getParsedSongs(Context context) {
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
