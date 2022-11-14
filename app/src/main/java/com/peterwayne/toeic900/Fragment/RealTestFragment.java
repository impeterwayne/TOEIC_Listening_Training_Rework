package com.peterwayne.toeic900.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.peterwayne.toeic900.Activity.MainActivity;
import com.peterwayne.toeic900.Adapter.TestListAdapter;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.Model.RealTest;
import com.peterwayne.toeic900.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class RealTestFragment extends Fragment {
    private Toolbar toolbar;
    private RecyclerView rcv_tests;
    private TestListAdapter testListAdapter;
    private List<RealTest> realTestList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_real_test, container, false);
        addControls(view);
        loadData();
        return view;
    }

    private void loadData() {
        realTestList = new ArrayList<>();
        DBQuery.db.collection("Tests").orderBy("number").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot doc : queryDocumentSnapshots)
                {
                    RealTest test = doc.toObject(RealTest.class);
                    realTestList.add(test);
                }
                testListAdapter = new TestListAdapter(getContext(), realTestList);
                rcv_tests.setLayoutManager(new LinearLayoutManager(getContext()));
                rcv_tests.setAdapter(testListAdapter);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
    }

    private void addControls(View view) {
        rcv_tests = view.findViewById(R.id.rcv_tests);
        toolbar = view.findViewById(R.id.toolbar);
    }

}