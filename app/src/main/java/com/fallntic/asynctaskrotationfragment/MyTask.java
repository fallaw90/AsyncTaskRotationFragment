package com.fallntic.asynctaskrotationfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * AsyncTask<Params, Progress, Result>
 * Params: doInBackground()
 * Progress: Value of the update
 * Result: Return value of doInBackground()
 */
class MyTask extends AsyncTask<String, Integer, Boolean> {

    private int contentLength, counter = 0, calulatedProgress = 0;
    private Activity mActivity;

    public MyTask(Activity activity){
        onAttach(activity);
    }

    public void onAttach(Activity activity){
        this.mActivity = activity;
    }

    public void onDetach(){
        this.mActivity = null;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onPreExecute() {
        if (mActivity == null){
            L.m("Skipping Progress Update Since Activity is null");
        }
        else {
            ((MainActivity) mActivity).showProgressBarBeforeDownloading();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {

        boolean successful = false;
        URL downloadURL = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            downloadURL = new URL(params[0]);
            //Recommend it by goole for all purposes while establishing connection
            connection = (HttpURLConnection) downloadURL.openConnection();
            contentLength = connection.getContentLength();
            inputStream = connection.getInputStream();

            file = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .getAbsoluteFile() + "/" + Uri.parse(params[0]).getLastPathSegment());
            fileOutputStream = new FileOutputStream(file);
            L.m("" + file.getAbsolutePath());
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {

                fileOutputStream.write(buffer, 0, read);
                counter += read;
                L.m("Counter: " + counter + " Content length: " + contentLength);
                publishProgress(counter);

            }
            successful = true;
        } catch (MalformedURLException e) {
            L.m(e + "");
            e.printStackTrace();
        } catch (IOException e) {
            L.m(e + "");
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    L.m(e + "");
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    L.m(e + "");
                    e.printStackTrace();
                }
            }
        }

        return successful;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mActivity == null){
            L.m("Skipping Progress Update Since Activity is null");
        }
        else {
            //values[0] is the counter
            //contentLength is the total length of the file
            calulatedProgress = (int) (((double) values[0] / contentLength) * 100);
            ((MainActivity) mActivity).updateProgress(calulatedProgress);
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (mActivity == null){
            L.m("Skipping Progress Update Since Activity is null");
        }
        else {
            ((MainActivity) mActivity).hideProgressBarAfterDownloading();
        }
    }
}