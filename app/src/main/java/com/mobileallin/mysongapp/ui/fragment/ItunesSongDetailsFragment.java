package com.mobileallin.mysongapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.mobileallin.mysongapp.R;

import butterknife.ButterKnife;

/**
 * Created by Dawid on 2017-11-27.
 */

public class ItunesSongDetailsFragment extends MvpAppCompatFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_details, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle(getString(R.string.song_details));

        return view;
    }
}
