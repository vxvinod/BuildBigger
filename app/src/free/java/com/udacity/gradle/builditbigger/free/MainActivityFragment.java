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
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private PublisherInterstitialAd mPublisherInterstitialAd;

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
        Log.d("Outside", "Outside MainActivity Fragment");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.tellJoke);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mPublisherInterstitialAd = new PublisherInterstitialAd(getContext());
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());
        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                //process the joke Request
                progressBar.setVisibility(View.VISIBLE);
                fetchJoke();
                loadNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.i("MainFragment", "onAdFailedToLoad: ad Failed to load. Reloading...");
                loadNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                Log.i("MainFragment", "onAdLoaded: interstitial is ready!");
                super.onAdLoaded();
            }
        });

        loadNewInterstitial();


        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
       // setUpInterstitialAd();
        jokeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mPublisherInterstitialAd.isLoaded()){
                    mPublisherInterstitialAd.show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    fetchJoke();
                }
            }
        });
        progressBar.setVisibility(View.GONE);
        return root;

    }

    private void fetchJoke() {
        new EndpointAsyncTask().execute(getActivity());
        progressBar.setVisibility(View.GONE);
    }

    private void loadNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mPublisherInterstitialAd.loadAd(adRequest);
    }


//

}
