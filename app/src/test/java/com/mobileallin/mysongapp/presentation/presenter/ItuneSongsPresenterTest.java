package com.mobileallin.mysongapp.presentation.presenter;

import android.os.Parcel;

import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
import com.mobileallin.mysongapp.interactor.ItunesSongsInteractor;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.impl.ItunesSongsRepositoryImpl;
import com.mobileallin.mysongapp.ui.view.ItunesSongsView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class ItuneSongsPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @SuppressWarnings("CanBeFinal")
    @Mock
    ItunesSongsRepositoryImpl songsRepository;

    @SuppressWarnings("CanBeFinal")
    @Mock
    ItunesSongsView view;

    @SuppressWarnings("CanBeFinal")
    @Mock
    HttpClient client;

    @Mock
    ItunesSongsInteractor itunesSongsInteractor;

    @SuppressWarnings("CanBeFinal")
    @Mock
    MySongAppComponent component;

    @SuppressWarnings("unused")
    public ItunesResponse ITUNES_RESPONSE;

    @SuppressWarnings("CanBeFinal")
    private List<ItunesSong> MANY_SONGS = new ArrayList<>();

    @SuppressWarnings({"CanBeFinal", "unused"})
    private Scheduler mainTestScheduler;

    private ItunesSongsPresenter presenter;

    private final ItunesSongsFactory itunesSongsFactory = new ItunesSongsFactory(3, "Fake",
            "Fake", "2017", "Fake", "Fake", "Poland", "fake_url");

    private final ItunesSong itunesSong = itunesSongsFactory.buildItunesSong();


    @Before
    public void setUp() {
        @SuppressWarnings("UnusedAssignment") Scheduler mainTestScheduler = Schedulers.trampoline();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        presenter = new ItunesSongsPresenter(component, view);
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassSongsToView() {
        MANY_SONGS.add(itunesSong);
        MANY_SONGS.add(itunesSong);
        MANY_SONGS.add(itunesSong);

        ItunesResponse ITUNES_RESPONSE = new ItunesResponse() {
            @Override
            public int resultsNumber() {
                return 3;
            }

            @Override
            public List<ItunesSong> allItuneSongs() {
                return MANY_SONGS;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        };

        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
                .thenReturn(Maybe.just(ITUNES_RESPONSE));

        presenter.displaySongs(ITUNES_RESPONSE.allItuneSongs());

        verify(view).displaySongs(ITUNES_RESPONSE.allItuneSongs());
    }

    @Test
    public void shouldHandleNoSongsFound() throws InterruptedException {

        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
                .thenReturn(Maybe.empty());

        presenter.displayNoSongs();

        verify(view).displayNoSongs();
    }

    @Test
    public void shouldHandleError() {

        final Throwable error = new Throwable("Error");

        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
                .thenReturn(Maybe.error(error));

        presenter.displayError(error);

        verify(view).displayError(error);
    }
}