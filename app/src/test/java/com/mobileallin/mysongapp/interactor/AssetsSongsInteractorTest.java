package com.mobileallin.mysongapp.interactor;

import android.content.Context;
import android.test.mock.MockContext;

import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.presentation.presenter.AssetsSongsPresenter;
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

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AssetsSongsInteractorTest {

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

    @Mock
    AssetsSongsPresenter presenter;

    @SuppressWarnings({"unused", "CanBeFinal"})
    long fakeId;

    private final AssetsSongFactory assetsSongFactory = new AssetsSongFactory(fakeId, "Fake",
            "Fake", "2017", "Fake", "Fake", "Poland");

    private AssetsSong assetsSong = assetsSongFactory.buildAssetsSong();

    @SuppressWarnings("CanBeFinal")
    private List<AssetsSong> MANY_SONGS = new ArrayList<>();

    private final List<AssetsSong> EMPTY_LIST = Collections.emptyList();

    private Context context;

    @Before
    public void setUp() {
        @SuppressWarnings("UnusedAssignment") long fakeId = (long) Math.random();
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

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.just(MANY_SONGS));

        presenter.loadAssetsSongs();

        view.displaySongs((ArrayList<AssetsSong>) MANY_SONGS);

        verify(view).displaySongs((ArrayList<AssetsSong>) MANY_SONGS);
    }

    @Test
    public void shouldHandleNoSongsFound() throws InterruptedException {

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.just(EMPTY_LIST));

        presenter.loadAssetsSongs();

        view.displayNoSongs();

        verify(view).displayNoSongs();
    }

    @Test
    public void shouldHandleError() {

        final Throwable error = new Throwable("Error");

        when(assetsSongsInteractor.getParsedSongs(context))
                .thenReturn(Single.error(error));

        presenter.loadAssetsSongs();

        view.displayError();

        verify(view).displayError();
    }
}
