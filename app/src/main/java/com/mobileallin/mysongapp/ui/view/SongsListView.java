package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Dawid on 2017-10-11.
 */

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SongsListView extends MvpView, BaseView {

    void displayError(Throwable error);

    void showLoading();

    void hideLoading();

}
