package com.mobileallin.mysongapp.presentation.presenter;

import android.content.Context;

import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.AssetsSongsView;

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

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AssetsSongsPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    AssetsSongsRepositoryImpl songsRepository;

    @SuppressWarnings("CanBeFinal")
    @Mock
    AssetsSongsView view;

    @SuppressWarnings("CanBeFinal")
    @Mock
    AssetsSongsInteractor assetsSongsInteractor;

    @SuppressWarnings("CanBeFinal")
    @Mock
    MySongAppComponent component;

    @SuppressWarnings({"CanBeFinal", "unused"})
    private Scheduler mainTestScheduler;

    @SuppressWarnings("CanBeFinal")
    private List<AssetsSong> MANY_SONGS = new ArrayList<>();

    @SuppressWarnings("CanBeFinal")
    @Mock
    Context context;

    private final AssetsSongFactory assetsSongFactory = new AssetsSongFactory(3, "Fake",
            "Fake", "2017", "Fake", "Fake", "Poland");

    @SuppressWarnings("CanBeFinal")
    private AssetsSong assetsSong = assetsSongFactory.buildAssetsSong();

    private final List<AssetsSong> EMPTY_LIST = Collections.emptyList();

    private AssetsSongsPresenter presenter;


    @Before
    public void setUp() {
        @SuppressWarnings("UnusedAssignment") final Scheduler mainTestScheduler = Schedulers.trampoline();
        presenter = new AssetsSongsPresenter(component, view, context, mainTestScheduler);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
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

        final Scheduler mainTestScheduler = Schedulers.trampoline();

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.just(MANY_SONGS));

        presenter.loadAssetsSongs(mainTestScheduler, mainTestScheduler, assetsSongsInteractor);

        verify(view).displaySongs((ArrayList<AssetsSong>) MANY_SONGS);
    }

    @Test
    public void shouldHandleNoSongsFound() throws InterruptedException {

        final Scheduler mainTestScheduler = Schedulers.trampoline();

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.just(EMPTY_LIST));

        presenter.loadAssetsSongs(mainTestScheduler, mainTestScheduler, assetsSongsInteractor);

        verify(view).displayNoSongs();
    }

    @Test
    public void shouldHandleError() {

        final Throwable error = new Throwable();

        final Scheduler mainTestScheduler = Schedulers.trampoline();

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.error(error));

        presenter.loadAssetsSongs(mainTestScheduler, mainTestScheduler, assetsSongsInteractor);

        verify(view).displayError();
    }
}
