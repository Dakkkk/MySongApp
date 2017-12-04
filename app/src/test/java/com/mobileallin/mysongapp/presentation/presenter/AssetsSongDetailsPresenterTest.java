package com.mobileallin.mysongapp.presentation.presenter;


import android.os.Bundle;

import com.mobileallin.mysongapp.dagger.component.MySongAppComponent;
import com.mobileallin.mysongapp.data.model.AssetsSong;
import com.mobileallin.mysongapp.factory.AssetsSongFactory;
import com.mobileallin.mysongapp.ui.view.BaseAssetsDetailsView;
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

public class AssetsSongDetailsPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @SuppressWarnings("CanBeFinal")
    @Mock
    BaseAssetsDetailsView view;

    @SuppressWarnings("CanBeFinal")
    @Mock
    MySongAppComponent component;

    private AssetsSongDetailsPresenter presenter;

    private final AssetsSongFactory assetsSongFactory = new AssetsSongFactory(1, "The Fake",
            "Fake", "Fake", "Fake", "Fake", "Fake");

    private final AssetsSong assetsSongFaked = assetsSongFactory.buildAssetsSong();

    private Bundle fakeSongArgs;

    @Before
    public void setUp() {
        presenter = new AssetsSongDetailsPresenter(component);
        fakeSongArgs = Mockito.mock(Bundle.class);
        //noinspection UnnecessaryBoxing
        Mockito.doReturn(Long.valueOf(1)).when(fakeSongArgs).get(ArgumentKeys.ID);
        Mockito.doReturn("The Fake").when(fakeSongArgs).get(ArgumentKeys.TITLE);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.AUTHOR);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.RELEASE_DATE);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.FIRST);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.YEAR);
        Mockito.doReturn("Fake").when(fakeSongArgs).get(ArgumentKeys.PLAY_COUNT);
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassSongToView() {

        AssetsSong assetsSongCreatedFromBundleArgs =
                presenter.createAssetsSong(fakeSongArgs);

        when(presenter.createAssetsSong(fakeSongArgs)).
                thenReturn(assetsSongFaked);

        presenter.displaySongDetails(view, assetsSongCreatedFromBundleArgs);

        verify(view).showSongDetails(assetsSongCreatedFromBundleArgs);
    }
}