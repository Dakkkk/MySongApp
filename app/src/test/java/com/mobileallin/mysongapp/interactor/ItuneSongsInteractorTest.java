package com.mobileallin.mysongapp.interactor;

import android.os.Parcel;

import com.mobileallin.mysongapp.data.model.ItunesResponse;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
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


public class ItuneSongsInteractorTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    ItunesSongsRepositoryImpl songsRepository;

    @Mock
    ItunesSongsView view;

    @Mock
    HttpClient client;

    @Mock
    ItunesSongsInteractor itunesSongsInteractor;

    Scheduler mainTestScheduler;

    long fakeId;

    ItunesSongsFactory itunesSongsFactory = new ItunesSongsFactory(fakeId, "Fake",
            "Fake", "2017", "Fake", "Fake", "Poland", "fake_url");

    ItunesSong itunesSong = itunesSongsFactory.buildItunesSong();

    public ItunesResponse ITUNES_RESPONSE;

    List<ItunesSong> MANY_SONGS = new ArrayList<>();

    @Before
    public void setUp() {
        @SuppressWarnings("UnusedAssignment") long fakeId = (long) Math.random();
        @SuppressWarnings("UnusedAssignment") Scheduler mainTestScheduler = Schedulers.trampoline();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
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

        //noinspection RedundantTypeArguments
        when(songsRepository.getSongs(client, mainTestScheduler, mainTestScheduler))
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
}