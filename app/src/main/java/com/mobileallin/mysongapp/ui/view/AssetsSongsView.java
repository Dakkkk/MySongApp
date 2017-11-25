package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Dawid on 2017-11-25.
 */
@StateStrategyType(OneExecutionStateStrategy.class)
public interface AssetsSongsView extends MvpView, BaseView {

/*
     Context getAssetsFragmentContext();
*/
}
