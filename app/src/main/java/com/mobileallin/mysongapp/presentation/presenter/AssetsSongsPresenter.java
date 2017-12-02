package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.helper.AssertsSongsStringParser;
import com.mobileallin.mysongapp.helper.AssetsSongTitleComparator;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;


@InjectViewState
public class AssetsSongsPresenter extends MvpPresenter<AssetsSongsView> {

    private static final String TAG = "AssetsSongsPresenter";
    private AssetsSongsView view;
    private AssetsSongsRepositoryImpl assetsRepository;
    private Context context;
    private ArrayList<AssetsSong> allAssetsSongsArrayList;
    private ArrayList<AssetsSong> assetsSearchList;

    @Inject
    public AssetsSongsInteractor assetsSongsInteractor;

    @Inject
    Router router;

    //ToDo to many arguments, get rid of the context... IMPORTANT
    public AssetsSongsPresenter(MySongAppComponent component, AssetsSongsView view,
                                AssetsSongsRepositoryImpl assetsRepository, Context context) {
        component.inject(this);
        this.view = view;
        this.assetsRepository = assetsRepository;
        this.context = context;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void attachView(AssetsSongsView view) {
        super.attachView(view);
        sortAndParseAssetsSongs();
        view.showLoading();
        assetsSongsInteractor.loadSongs(view, allAssetsSongsArrayList);
    }

    //ToDo Check if it's OK
    public void sortAndParseAssetsSongs(){
        if (allAssetsSongsArrayList == null || allAssetsSongsArrayList.isEmpty()) {
            String songAssetsString = assetsRepository.loadJSONFromAsset(context);
            allAssetsSongsArrayList = parseToAssetsArrayList(songAssetsString);
            sortAssetsSongs(allAssetsSongsArrayList);
        }
    }

    public ArrayList<AssetsSong> parseToAssetsArrayList(String songAssetsString){
        AssertsSongsStringParser assertsSongsStringParser = new AssertsSongsStringParser();
        return assertsSongsStringParser.parseStringToAssetsSongList(songAssetsString);
    }

    public void sortAssetsSongs(ArrayList<AssetsSong> allAssetsSongsArrayList){
        Collections.sort(allAssetsSongsArrayList, new AssetsSongTitleComparator());
    }

    public ArrayList<AssetsSong> getAssetsSongArrayList() {
        return allAssetsSongsArrayList;
    }

    public void showAssetsSearchResults(ArrayList<AssetsSong> searchResponse) {
        Log.d("MySearch", "showAssetsSearchResults called!");
        //view.showSearchResult(searchResponse);
        view.showLoading();
        assetsSongsInteractor.loadSongs(view, searchResponse);
    }

    public ArrayList<AssetsSong> searchAssetsSong(String s) {
        assetsSearchList = new ArrayList<>();

        Log.d("searchA..AllSongs", allAssetsSongsArrayList.toString());

        for (AssetsSong song : allAssetsSongsArrayList) {
            //ToDo check id this can be done better (use startsWith or contains or...)
            if (song.author().toLowerCase().contains(s) ||
                    song.title().toLowerCase().startsWith(s) ||
                    String.valueOf(song.releaseDate()).toLowerCase().startsWith(s)) {
                assetsSearchList.add(song);
            }
        }
        Log.d("MySearch searchAS...", assetsSearchList.toString());
        return assetsSearchList;
    }

    public void showDetails(int position) {
        ArrayList<AssetsSong> currentSongsList;
        if (assetsSearchList != null && !assetsSearchList.isEmpty()) {
            Log.d("ASetarchList", "null or empty");
            currentSongsList = assetsSearchList;
        } else {
            currentSongsList = getAssetsSongArrayList();
        }

        Bundle args = new Bundle();
        args.putLong(ArgumentKeys.ID, currentSongsList.get(position).id());
        args.putString(ArgumentKeys.TITLE, currentSongsList.get(position).title());
        args.putString(ArgumentKeys.AUTHOR, currentSongsList.get(position).author());
        args.putString(ArgumentKeys.RELEASE_DATE, currentSongsList.get(position).releaseDate());
        args.putString(ArgumentKeys.YEAR, currentSongsList.get(position).year());
        args.putString(ArgumentKeys.FIRST, currentSongsList.get(position).first());
        args.putString(ArgumentKeys.PLAY_COUNT, currentSongsList.get(position).playCount());

        router.putCommand(Command.SHOW_ASSETS_SONG_DETAILS, AssetsSongDetailsPresenter.class.getName(), args);
        Log.d("showDetails, Command: ", args.getLong(ArgumentKeys.ID) + "");
        Log.d("showDetails, ID: ", args.getLong(ArgumentKeys.ID) + "");
    }
}
