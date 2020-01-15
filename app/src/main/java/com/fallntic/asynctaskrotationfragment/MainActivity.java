package com.fallntic.asynctaskrotationfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private EditText selectionText;
    private ListView chooseImageList;
    public static String[] listOfImages;
    private ProgressBar downloadImmagesProgress;
    private NonUITaskFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectionText = (EditText) findViewById(R.id.urlSelectionText);
        chooseImageList = (ListView) findViewById(R.id.chooseImageList);
        listOfImages = getResources().getStringArray(R.array.imageUrls);
        downloadImmagesProgress = (ProgressBar) findViewById(R.id.downloadProgress);

        chooseImageList.setOnItemClickListener(this);

        if (savedInstanceState == null){
            fragment = new NonUITaskFragment();
            getSupportFragmentManager().beginTransaction().add(fragment, "TaskFragment").commit();
        }
        else {
            fragment = (NonUITaskFragment) getSupportFragmentManager().findFragmentByTag("TaskFragment");
        }
        if (fragment != null){
            if (fragment.mMyTask != null && fragment.mMyTask.getStatus() == AsyncTask.Status.RUNNING){
                downloadImmagesProgress.setVisibility(View.VISIBLE);
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }*/

    public void downloadImage(View view) {

        String url = selectionText.getText().toString();
        if (url != null && url.length() > 0) {

            fragment.beginTask(url);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        selectionText.setText(listOfImages[i]);
    }

    public void showProgressBarBeforeDownloading(){
        if (fragment.mMyTask != null){
            if (downloadImmagesProgress.getVisibility() != View.VISIBLE
                && downloadImmagesProgress.getProgress() != downloadImmagesProgress.getMax()){

                downloadImmagesProgress.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideProgressBarAfterDownloading(){
        if (fragment.mMyTask != null){
            if (downloadImmagesProgress.getVisibility() == View.VISIBLE){

                downloadImmagesProgress.setVisibility(View.GONE);
            }
        }
    }

    public void updateProgress(int progressValue){
        downloadImmagesProgress.setProgress(progressValue);
    }

}
