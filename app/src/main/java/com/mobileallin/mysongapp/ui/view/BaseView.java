package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(OneExecutionStateStrategy.class)
public interface BaseView extends MvpView {
    void displayNoSongs();

    void showLoading();

    void hideLoading();
}
