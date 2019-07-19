package com.example.proyecto2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto2.Adapter.PlaceAdapter;
import com.example.proyecto2.MainActivity;
import com.example.proyecto2.Model.PlaceModel;
import com.example.proyecto2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PlaceModel> myModelArrayList = new ArrayList<>();
    PlaceAdapter mAdapter = new PlaceAdapter(myModelArrayList);

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = v.findViewById(R.id.myRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        ((MainActivity) getActivity()).myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals("message")) {
                        myModelArrayList = new ArrayList<>();
                        for (DataSnapshot newSnapshot : postSnapshot.getChildren()) {
                            PlaceModel recoveredData = newSnapshot.getValue(PlaceModel.class);
                            myModelArrayList.add(recoveredData);

                        }
                        mAdapter.loadData(myModelArrayList);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.e("onCancelled", "Failed to read value.");
            }
        });

        return v;
    }

}
