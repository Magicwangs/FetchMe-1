package com.cgf.fetchme.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cgf.fetchme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenguanfu on 3/30/2016.
 */
public class TwoFragment extends Fragment {

    private List<Person> persons;
    private RecyclerView rv;


    public TwoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_activity, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

        return rootView;
    }

    private void initializeData() {
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.emma));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.lavery));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.lillie));
    }

    private void initializeAdapter() {
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
