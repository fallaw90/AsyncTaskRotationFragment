package com.fallntic.asynctaskrotationfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NonUITaskFragment extends Fragment {

    MyTask mMyTask;
    private Activity mActivity;

    public NonUITaskFragment(){

    }

    public void beginTask(String... arguments){

        mMyTask = new MyTask(mActivity);
        mMyTask.execute(arguments);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //Save a reference to the newly acquired activity
        this.mActivity = (Activity) context;
        if (mMyTask != null){
            mMyTask.onAttach(mActivity);
        }

        L.m("Fragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        L.m("Fragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        L.m("Fragment onCreateView");

        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        L.m("Fragment onActivityCreated");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        L.m("Fragment onSaveInstanceState");
    }

    @Override
    public void onStart() {
        super.onStart();

        L.m("Fragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        L.m("Fragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        L.m("Fragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        L.m("Fragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        L.m("Fragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        L.m("Fragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (mMyTask != null){
            mMyTask.onDetach();
        }
        L.m("Fragment onDetach");
    }
}
