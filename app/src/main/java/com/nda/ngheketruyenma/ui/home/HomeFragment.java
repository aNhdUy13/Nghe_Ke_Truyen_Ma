package com.nda.ngheketruyenma.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nda.ngheketruyenma.MainActivity;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef  = database.getReference("Truyen Ma");

    List<Homes> homesList;
    HomesAdapter homesAdapter;

    List<HomesHorizontal> homesHorizontalList;
    AdapterHomesHorizontal adapterHomesHorizontal;

    RecyclerView rv_home, rcv_homeHorizontal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myRef.keepSynced(true);

        mapting(root);
        setupRecycleView();
        initiate();

        return root;
    }
    private void initiate() {
        getAllData();

        getHorizontalData();
    }

    private void getAllData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Homes homes =dataSnapshot.getValue(Homes.class);

                    homesList.add(homes);
                }

                sortData();
                homesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getHorizontalData()
    {
        ValueEventListener  valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    HomesHorizontal homesHorizontal =dataSnapshot.getValue(HomesHorizontal.class);

                    homesHorizontalList.add(homesHorizontal);
                }


                sortData();
                adapterHomesHorizontal.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        };

        Query query = myRef.orderByChild("author")
                .equalTo("Nguyễn Ngọc Ngạn");

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void sortData() {
        Collections.sort(homesList, new Comparator<Homes>() {
            @Override
            public int compare(Homes o1, Homes o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }
    public void gotoDetail(String author, String name, String content, String source, String img)
    {
        Intent intent = new Intent(getContext(), HomesDetail.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("storyDetail",true);
        bundle.putString("authorDetail",author);
        bundle.putString("storyNameDetail",name);
        bundle.putString("storyContentDetail",content);
        bundle.putString("storyMp3Detail",source);
        bundle.putString("storyImgDetail",img);
        intent.putExtras(bundle);
        startActivity(intent);


    }


    private void setupRecycleView() {
        homesList = new ArrayList<>();
        homesAdapter = new HomesAdapter(this,homesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rv_home.setLayoutManager(linearLayoutManager);
        rv_home.setAdapter(homesAdapter);

        homesHorizontalList = new ArrayList<>();
        adapterHomesHorizontal = new AdapterHomesHorizontal(this,homesHorizontalList);
        LinearLayoutManager linearLayoutManager_horizontal = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_homeHorizontal.setLayoutManager(linearLayoutManager_horizontal);
        rcv_homeHorizontal.setAdapter(adapterHomesHorizontal);
    }

    private void mapting(View root) {
        rv_home = (RecyclerView) root.findViewById(R.id.rv_homeVertical);
        rcv_homeHorizontal  = (RecyclerView) root.findViewById(R.id.rcv_homeHorizontal);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}