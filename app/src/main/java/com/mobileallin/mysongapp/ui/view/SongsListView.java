package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


@StateStrategyType(OneExecutionStateStrategy.class)
public interface SongsListView extends MvpView, BaseView, SearchView {

    void displayError(Throwable error);

    void showLoading();

    void hideLoading();

}
