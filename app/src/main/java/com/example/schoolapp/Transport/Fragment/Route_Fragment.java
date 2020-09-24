package com.example.schoolapp.Transport.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.Adopter.routeAdoptr;
import com.example.schoolapp.Transport.modelclass.RoutModerlclass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Route_Fragment extends Fragment {
RecyclerView recyclerview;
List<RoutModerlclass> list;
FloatingActionButton floatingActionButton;
Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_route, container, false);
        recyclerview=view.findViewById(R.id.recyclerview);
        floatingActionButton=view.findViewById(R.id.fab);
        list = new ArrayList<>();
        list.add(new RoutModerlclass(1,"UP 43 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(2,"UP 44 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(3,"UP 46 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(4,"UP 47 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(5,"UP 48 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(6,"UP 49 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(7,"UP 50 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(8,"UP 51 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(9,"UP 35 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(10,"UP 55 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(11,"UP 65 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(12,"UP 75 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(13,"UP 85 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(14,"UP 95 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        list.add(new RoutModerlclass(15,"UP 25 EZ05262","ALAM","8933894533","CP","AZADPUR"));
        routeAdoptr customAdapter = new routeAdoptr(getActivity(), list);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(customAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new BusRouteFragment();
                FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.add(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
    }

