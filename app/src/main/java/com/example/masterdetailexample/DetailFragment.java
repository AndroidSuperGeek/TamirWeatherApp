package com.example.masterdetailexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.masterdetailexample.view.adapter.CitiesAdapter;
import com.example.masterdetailexample.view.adapter.City;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subscribers.DisposableSubscriber;

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @BindView(R.id.cities_view)
    RecyclerView citiesView;

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind (this,view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (this.getContext ());
        citiesView.setLayoutManager(layoutManager);

        CitiesAdapter mAdapter = new CitiesAdapter (DataManager.getCities());
        citiesView.setAdapter(mAdapter);

        fragmentManager =getActivity ().getSupportFragmentManager ();

        return view;
    }

}
