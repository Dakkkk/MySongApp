package com.mobileallin.mysongapp.dagger.module;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.mobileallin.mysongapp.dagger.ApplicationContext;
import com.mobileallin.mysongapp.dagger.IoScheduler;
import com.mobileallin.mysongapp.dagger.UiScheduler;
import com.mobileallin.mysongapp.interactor.AssetsSongsInteractor;
import com.mobileallin.mysongapp.interactor.ItunesSongsInteractor;
import com.mobileallin.mysongapp.navigation.Router;
import com.mobileallin.mysongapp.network.AutoValueGsonFactory;
import com.mobileallin.mysongapp.network.HttpClient;
import com.mobileallin.mysongapp.repositories.ItunesSongsRepository;
import com.mobileallin.mysongapp.repositories.impl.AssetsSongsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    //ToDo Split this into a couple of separate modules

    private final Context context;
    private static final String SHARED_PREFS_NAME = "app_preferences";

    public AppModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return context;
    }

    @IoScheduler
    @Singleton
    @Provides
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @UiScheduler
    @Singleton
    @Provides
    public Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }


    @Singleton
    @Provides
    public ItunesSongsRepository provideItunesSongsRepository() {
        return new com.mobileallin.mysongapp.repositories.impl.ItunesSongsRepositoryImpl();
    }

    @Singleton
    @Provides
    public AssetsSongsRepositoryImpl provideAssetsSongsRepository() {
        return new AssetsSongsRepositoryImpl();
    }

    @Singleton
    @Provides
    public ItunesSongsInteractor provideSongsInteractor(ItunesSongsRepository itunesSongsRepository, HttpClient client,
                                                        @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler) {
        return new ItunesSongsInteractor(itunesSongsRepository, client, ioScheduler, uiScheduler);
    }

    @Singleton
    @Provides
    public AssetsSongsInteractor provideAssetsSongsInteractor() {
        return new AssetsSongsInteractor();
    }

    @Singleton
    @Provides
    public HttpClient provideClient() {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create())
                        .create());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpClient.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory) //or use GsonConverterFactory.create()
                .build();

        return retrofit.create(HttpClient.class);
    }

    @Singleton
    @Provides
    public SharedPreferences providePreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    Router provideRouter(){
        return new Router();
    }

}
