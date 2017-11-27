package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.helper.AssertsSongsStringParser;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


@InjectViewState
public class AssetsSongsPresenter extends MvpPresenter<AssetsSongsView> {

    private static final String TAG = "AssetsSongsPresenter";
    private List<ItunesSong> songsList;
    private AssetsSongsView view;
    private AssetsSongsRepositoryImpl assetsRepository;
    private Context context;
    private ArrayList<AssetsSong> assetsSongArrayList;

    @Inject
    public AssetsSongsInteractor assetsSongsInteractor;

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

            if (song.author().toLowerCase().contains(s) ||
                    song.title().toLowerCase().startsWith(s) ||
                    song.releaseDate().toLowerCase().startsWith(s)) {
                assetsSearchList.add(song);
            }
        }
        Log.d("MySearch searchA..Song", assetsSearchList.toString());
        return assetsSearchList;
    }
}
