package com.mobileallin.mysongapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mobileallin.mysongapp.data.model.Song;

import java.util.List;

/**
 * Created by Dawid on 2017-10-11.
 */

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SongsListView extends MvpView {

    void showSongs(List<Song> list);

    void showLoading();

    void hideLoading();

}
