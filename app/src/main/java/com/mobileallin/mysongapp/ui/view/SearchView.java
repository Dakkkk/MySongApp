package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.ItunesResponse;


public interface SearchView {
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showSearchResult(ItunesResponse statusesItems);
}
