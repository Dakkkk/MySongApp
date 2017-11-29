package com.mobileallin.mysongapp.ui.view;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.ItunesSong;

public interface BaseItunesDetailsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSongDetails(ItunesSong itunesSong);

}
