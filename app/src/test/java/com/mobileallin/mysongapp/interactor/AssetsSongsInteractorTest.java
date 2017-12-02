/*
package com.mobileallin.mysongapp.interactor;

import android.content.Context;
import android.os.Parcel;
import android.test.mock.MockContext;

import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
import com.mobileallin.mysongapp.presentation.presenter.ItuneSongsPresenter;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.repositories.impl.ItunesSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;
import com.mobileallin.mysongapp.ui.view.ItunesSongsView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

*/
/**
 * Created by Dawid on 2017-12-02.
 *//*

public class AssetsSongsInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    AssetsSongsRepositoryImpl songsRepository;

    @Mock
    AssetsSongsView view;

    @Mock
    AssetsSongsInteractor assetsSongsInteractor;

    AssetsSongsPresenter presenter;

    Scheduler mainTestScheduler;

    long fakeId;

    AssetsSongFactory assetsSongFactory = new AssetsSongFactory(fakeId, "Fake",
            "Fake", "2017", "Fake", "Fake", "Poland");

    AssetsSong assetsSong = assetsSongFactory.buildAssetsSong();

    List<AssetsSong> MANY_SONGS = new ArrayList<>();

    private final List<AssetsSong> EMPTY_LIST = Collections.emptyList();

    Context context;

    @Before
    public void setUp() {
        long fakeId = (long) Math.random();
        Scheduler mainTestScheduler = Schedulers.trampoline();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        context = new MockContext();
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassSongsToView() {
        MANY_SONGS.add(assetsSong);
        MANY_SONGS.add(assetsSong);
        MANY_SONGS.add(assetsSong);

        System.out.print("many songs" + MANY_SONGS.toString());
        when(songsRepository.loadJSONFromAsset(context))
                .thenReturn(Maybe.<ItunesResponse>just((ItunesResponse) ITUNES_RESPONSE));

        //Calling on view because the method is called in interactor's loadSongs()...
        view.displaySongs(((ItunesResponse) ITUNES_RESPONSE).allItuneSongs());

        verify(view).displaySongs(((ItunesResponse) ITUNES_RESPONSE).allItuneSongs());
    }

    @Test
    public void shouldHandleNoSongsFound() throws InterruptedException {

        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
                .thenReturn(Maybe.empty());

        view.displayNoSongs();

        verify(view).displayNoSongs();
    }

    @Test
    public void shouldHandleError() {

        final Throwable error = new Throwable("Error");

        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
                .thenReturn(Maybe.error(error));

        view.displayError(error);

        verify(view).displayError(error);
    }

}*/
