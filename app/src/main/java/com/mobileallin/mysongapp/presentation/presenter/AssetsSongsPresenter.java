package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.helper.AssertsSongsStringParser;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.navigation.Command;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import java.util.ArrayList;

import javax.inject.Inject;


@InjectViewState
public class AssetsSongsPresenter extends MvpPresenter<AssetsSongsView> {

    private static final String TAG = "AssetsSongsPresenter";
    private AssetsSongsView view;
    private AssetsSongsRepositoryImpl assetsRepository;
    private Context context;
    private ArrayList<AssetsSong> assetsSongArrayList;

    @Inject
    public AssetsSongsInteractor assetsSongsInteractor;

    @Inject
    Router router;

    //ToDo to many arguments, get rid of the context...
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
        String songAssetsString = assetsRepository.loadJSONFromAsset(context);

        //ToDo move from here!
        AssertsSongsStringParser assertsSongsStringParser = new AssertsSongsStringParser();
        assetsSongArrayList = assertsSongsStringParser.parseStringToAssetsSongList(songAssetsString);
        assetsSongsInteractor.loadSongs(view, assetsSongArrayList);
    }

    public ArrayList<AssetsSong> getAssetsSongArrayList() {
        return assetsSongArrayList;
    }

    public void showAssetsSearchResults(ArrayList<AssetsSong> searchResponse) {
        Log.d("MySearch", "showAssetsSearchResults called!");
        //view.showSearchResult(searchResponse);
        assetsSongsInteractor.loadSongs(view, searchResponse);
    }

    public ArrayList<AssetsSong> searchAssetsSong(String s) {
        ArrayList<AssetsSong> assetsSearchList = new ArrayList<>();

        for (AssetsSong song : assetsSongArrayList) {
            //ToDo check id this can be done better (use startsWith or contains or...)
            if (song.author().toLowerCase().contains(s) ||
                    song.title().toLowerCase().startsWith(s) ||
                    song.releaseDate().toLowerCase().startsWith(s)) {
                assetsSearchList.add(song);
            }
        }
        Log.d("MySearch searchA..Song", assetsSearchList.toString());
        return assetsSearchList;
    }

    //ToDo clear asssets before put?
    public void showDetails(int position) {
        Bundle args = new Bundle();
        args.putLong(ArgumentKeys.ID, getAssetsSongArrayList().get(position).id());
        args.putString(ArgumentKeys.TITLE, getAssetsSongArrayList().get(position).title());
        args.putString(ArgumentKeys.AUTHOR, getAssetsSongArrayList().get(position).author());
        args.putString(ArgumentKeys.RELEASE_DATE, getAssetsSongArrayList().get(position).releaseDate());

        router.putCommand(Command.SHOW_ASSETS_SONG_DETAILS, AssetsSongDetailsPresenter.class.getName(), args);
        Log.d("showDetails, Command: ", args.getLong(ArgumentKeys.ID) + "");
        Log.d("showDetails, ID: ", args.getLong(ArgumentKeys.ID) + "");
    }
}
