package com.mobileallin.mysongapp.ui.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.Song;

import java.util.List;

/**
 * Created by Dawid on 2017-11-25.
 */

@StateStrategyType(OneExecutionStateStrategy.class)
public interface BaseView extends MvpView {
    void displaySongs(List<Song> list);

    void displayNoSongs();
}
