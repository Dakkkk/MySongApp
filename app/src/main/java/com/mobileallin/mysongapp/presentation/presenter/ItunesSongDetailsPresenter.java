package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.ui.view.BaseSongDetailsView;
import com.mobileallin.mysongapp.utils.Keys;

import javax.inject.Inject;

@InjectViewState
public class ItunesSongDetailsPresenter extends MvpPresenter<BaseSongDetailsView> {

    @Inject
    Router router;

    private long id;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(getClass().getName());
        id = args.getLong(Keys.ID);
    }
}
