package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.AssetsSong;

/**
 * Created by Dawid on 2017-11-29.
 */

public interface BaseAssetsDetailsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSongDetails(AssetsSong assetsSong);
}
