package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokeandroidlib.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private InterstitialAd mInterstitialAd;

//    public void setLoadJoke(String loadJoke) {
//        this.loadJoke = loadJoke;
//    }

    public String loadJoke = null;
    private ProgressBar progressBar;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Paid", "Outside MainActivity Fragment");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.tellJoke);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        // setUpInterstitialAd();
        jokeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                fetchJoke();
            }
        });
        progressBar.setVisibility(View.GONE);
        return root;

    }

    private void fetchJoke() {
        new EndpointAsyncTask().execute(getActivity());
        progressBar.setVisibility(View.GONE);
    }


}
