package com.mobileallin.mysongapp.navigation;

import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;


public class Router {

    private WeakReference<INavigator> navigator;
    private HashMap<String, Bundle> argumentMap;

    public Router() {
        navigator = new WeakReference<>(command -> {
        });
        argumentMap = new HashMap<>();
    }

    public void attachToNavigator(INavigator navigator) {
        Log.d("attachNav", "called");
        this.navigator = new WeakReference<>(navigator);
        Log.d("attachNav", navigator.toString());
    }

    public void putCommand(Command command, String key, Bundle args) {
        Log.d(Router.class.getSimpleName(), "putCommand called");

        if (navigator.get() != null) {
            addArguments(key, args);
            Log.d(Router.class.getSimpleName(), navigator.get().toString());
            navigator.get().handleCommand(command);
        } else {
            Log.d(Router.class.getSimpleName(), "putCommand called navigator null");
        }

    }

    public Bundle getArguments(String key) {
        return key == null ? new Bundle() :
                argumentMap.containsKey(key) ? argumentMap.get(key) : new Bundle();
    }

    public Bundle removeArguments(String key) {
        return key == null ? new Bundle() :
                argumentMap.containsKey(key) ? argumentMap.remove(key) : new Bundle();
    }

    private void addArguments(String key, Bundle args) {
        if (key != null && args != null) {
            Log.d(Router.class.getSimpleName(), "addArguments: " + key + " " + args.getBundle(key));
            argumentMap.put(key, args);
        } else {
            Log.d(Router.class.getSimpleName(), "addArguments called key null");
        }

    }
}
