package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.view.BaseSongDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import javax.inject.Inject;

@InjectViewState
public class ItunesSongDetailsPresenter extends MvpPresenter<BaseSongDetailsView> {

    @Inject
    Router router;

    private long songId;
    private int position;

    public ItunesSongDetailsPresenter(MySongAppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(this.getClass().getName());
        if (args != null){
            songId = args.getLong(ArgumentKeys.ID);
            position = args.getInt(ArgumentKeys.POSITION);
        }
        System.out.print("DetailsPresenter: " + songId + ", " + position);
    }
}
