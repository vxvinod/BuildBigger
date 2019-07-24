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
        Log.d("Outside", "Outside MainActivity Fragment");
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Button jokeButton = (Button) root.findViewById(R.id.tellJoke);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
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

    public void launchDisplayJokeActivity(){
        //if (!testFlag) {
            Context context = getActivity();
            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra("joke", loadJoke);
            //Toast.makeText(context, loadedJoke, Toast.LENGTH_LONG).show();
            context.startActivity(intent);
            progressBar.setVisibility(View.GONE);
        //}
    }


    private void setUpInterstitialAd(){
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ad-interstitial");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(getContext(), JokeActivity.class);
                intent.putExtra("joke", loadJoke);
                startActivity(intent);
            }
        });
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);

    }

}
