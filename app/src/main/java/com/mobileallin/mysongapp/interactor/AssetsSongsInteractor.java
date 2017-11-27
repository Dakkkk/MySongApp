package com.mobileallin.mysongapp.interactor;

import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

import java.util.ArrayList;


public class AssetsSongsInteractor {

    public void loadSongs(AssetsSongsView assetsSongsView, ArrayList<AssetsSong> assetsSongs) {
        assetsSongsView.displaySongs(assetsSongs);
    }
}
