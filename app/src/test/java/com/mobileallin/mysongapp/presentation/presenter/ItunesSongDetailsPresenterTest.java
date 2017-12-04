package com.mobileallin.mysongapp.presentation.presenter;


import android.os.Bundle;

import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.ItunesSong;
import com.mobileallin.mysongapp.factory.ItunesSongsFactory;
import com.mobileallin.mysongapp.ui.view.BaseItunesDetailsView;
import com.mobileallin.mysongapp.utils.ArgumentKeys;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItunesSongDetailsPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @SuppressWarnings("CanBeFinal")
    @Mock
    BaseItunesDetailsView view;

    @SuppressWarnings("CanBeFinal")
    @Mock
    MySongAppComponent component;

    private ItunesSongDetailsPresenter presenter;

    private final ItunesSongsFactory itunesSongsFactory = new ItunesSongsFactory(1, "The Fake",
            "Fake", "2017", "Fake", "Fake", "Poland", "FakeUrl");

    private final ItunesSong itunesSongFaked = itunesSongsFactory.buildItunesSong();

    private Bundle fakeSongArgs;

    @Before
    public void setUp() {
        presenter = new ItunesSongDetailsPresenter(component);
        fakeSongArgs = Mockito.mock(Bundle.class);
        //noinspection UnnecessaryBoxing
        Mockito.doReturn(Long.valueOf(1)).when(fakeSongArgs).get(ArgumentKeys.ID);
        Mockito.doReturn("The Fake").when(fakeSongArgs).get(ArgumentKeys.TITLE);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.AUTHOR);
        Mockito.doReturn("2017").when(fakeSongArgs).get(ArgumentKeys.RELEASE_DATE);
        Mockito.doReturn("Poland").when(fakeSongArgs).get(ArgumentKeys.COUNTRY);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.GENRE_NAME);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.COLLECTION_NAME);
        Mockito.doReturn("FakeUrl").when(fakeSongArgs).get(ArgumentKeys.THUMBNAIL_URL);
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassSongToView() {

        ItunesSong itunesSongCreatedFromBundleArgs =
                presenter.createItunesSongFromBundle(fakeSongArgs);

        when(presenter.createItunesSongFromBundle(fakeSongArgs)).
                thenReturn(itunesSongFaked);

        presenter.showSongDetails(view, itunesSongCreatedFromBundleArgs);

        verify(view).showSongDetails(itunesSongCreatedFromBundleArgs);
    }
}