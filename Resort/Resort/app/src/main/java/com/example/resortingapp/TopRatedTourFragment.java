package com.example.resortingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import resortingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TopRatedTourFragment extends Fragment {

    List<Tour> mTours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;
    FloatingActionButton addTourBtn,addPlaceBtn;
    View mView;
    SwipeRefreshLayout swipeRefreshLayout;
    TourAdapter mAdapter;
    private Filter filter = new Filter();
    private Button btn_filters;
    private Button btn_category;
    private Button btn_regions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_top_rated_tour, container, false);

        firebase_connection();

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refresh_top_rated);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                firebase_connection();

            }
        });

        addTourBtn = mView.findViewById(R.id.add_tour_btn);
        addPlaceBtn=mView.findViewById(R.id.add_place_btn);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onCallback(User user) {
                if (user.isAdmin) {
                    addTourBtn.show();
                    addPlaceBtn.show();
                }else
                {addTourBtn.setVisibility(View.INVISIBLE);
                    addPlaceBtn.setVisibility(View.INVISIBLE);
            }}
        });

        addTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTourActivity.class);
                startActivity(intent);
            }
        });


//        final EditText addPlaceEt = mView.findViewById(R.id.place_name_et);
   //   addPlaceBtn = mView.findViewById(R.id.add_place_btn);
        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),PlaceDeatil.class);
                startActivity(intent);
            }
        });

        btn_filters = mView.findViewById(R.id.btn_filtres);
        btn_regions = mView.findViewById(R.id.btn_regions);
        btn_filters.setOnClickListener(v -> startActivity(new Intent(getContext(), FiltresActivity.class)));
        btn_regions.setOnClickListener(v -> startActivity(new Intent(getContext(), ProvinceActivity.class)));
        return mView;
    }
    private void configFilters() {
        filter = SPFiltre.getFiltre(requireActivity());

        if (!filter.getProvince().getRegion().isEmpty()) {
            btn_regions.setText(filter.getProvince().getRegion());
        } else {
            btn_regions.setText("Cities");
        }
//        if (!filter.getInterest().isEmpty()) {
//            btn_filters.setText(filter.getInterest());
//        } else {
//            btn_filters.setText("Interest");
//        }
      //  recoverads();
    }
    @Override
    public void onStart() {
        super.onStart();
        configFilters();
        firebase_connection();
    }

    private void firebase_connection() {
        FirestoreQueries.getTours(new FirestoreQueries.FirestoreTourCallback() {
            @Override
            public void onCallback(List<Tour> tours) {
                mAdapter = null;

                    if (!filter.getInterest().isEmpty()) {
                        if (!filter.getInterest().equals("All Interest")) {

                            for (Tour ads : new ArrayList<>(tours)) {

                                if (!ads.tourKeywords.contains(filter.getInterest())) {
                                    tours.remove(ads);
                                }
                            }
                        }
                    }


                    if (!filter.getProvince().getRegion().isEmpty()) {

                        for (Tour ads : new ArrayList<>(tours)) {
                            int i=0;
                           // Toast.makeText(getContext(), filter.getProvince().getRegion(), Toast.LENGTH_SHORT).show();
                            for(Place place:new ArrayList<>(ads.places)) {
                           //     Toast.makeText(getContext(), place.nameEN, Toast.LENGTH_SHORT).show();
                                if (place.nameEN.contains(filter.getProvince().getRegion()))
                                {
                                    i++;
                            }
                            }
                            if(i==0)
                            {
                                tours.remove(ads);
                            }

                        }
                    }


                    if (!filter.getSeason().isEmpty()) {
                        for (Tour ads : new ArrayList<>(tours)) {
                            int i=0;
                            for(Place place:new ArrayList<>(ads.places)) {
                            String s="";
                                switch(place.recommendedSeason){
                                    case 0:
                                        s="Summer";
                                        break;
                                    case 1:
                                        s="Winter";
                                        break;
                                    case 2:
                                        s="Spring";
                                        break;
                                    case 3:
                                        s="Autumn";
                                        break;
                                }
                                if (s.contains(filter.getSeason()))
                                {
                                    i++;
                                }

                            }
                            if(i==0)
                            {
                                tours.remove(ads);
                            }
                        }
                    }


//                    if (!filter.getSearch().isEmpty()) {
//                        for (Ads ads : new ArrayList<>(adsList)) {
//                            if (!ads.getTitle().toLowerCase().contains(filter.getSearch().toLowerCase())) {
//                                adsList.remove(ads);
//                            }
//                        }
//                    }


//                    if (filter.getValueMin() > 0) {
//                        for (Tour ads : new ArrayList<>(tours)) {
//                            if (ads< filter.getValueMin()) {
//                                adsList.remove(ads);
//                            }
//                        }
//                    }


//                    if (filter.getValueMax() > 0) {
//                        for (Ads ads : new ArrayList<>(adsList)) {
//                            if (ads.getValue() > filter.getValueMax()) {
//                                adsList.remove(ads);
//                            }
//                        }
//                    }

                mTours = tours;
                mAdapter = new TourAdapter(mTours);
                mRecyclerView = mView.findViewById(R.id.rv_top_rated);
                mManger = new LinearLayoutManager(mView.getContext());
                mRecyclerView.setLayoutManager(mManger);
                mRecyclerView.setAdapter(mAdapter);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

}
