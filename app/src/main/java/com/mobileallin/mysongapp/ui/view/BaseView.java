package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.ItunesSong;

import java.util.List;


@StateStrategyType(OneExecutionStateStrategy.class)
public interface BaseView extends MvpView {
    void displaySongs(List<ItunesSong> list);

    void displayNoSongs();
}
