package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drplasma2.R;

public class RiwayatUtamaFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public RiwayatUtamaFragment() {

    }

    public static RiwayatUtamaFragment newInstance(String param1, String param2) {
        RiwayatUtamaFragment fragment = new RiwayatUtamaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_riwayat_utama, container, false);
        Button btnRU1 = view.findViewById(R.id.button_utama1);
        btnRU1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCari1 = new Intent(getActivity(), RiwayatDonorActivity.class);
                startActivity(toCari1);
            }
        });

        Button btnRU2 = view.findViewById(R.id.button_utama2);
        btnRU2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCari2 = new Intent(getActivity(), RiwayatPermintaanActivity.class);
                startActivity(toCari2);
            }
        });
        return view;
    }
}