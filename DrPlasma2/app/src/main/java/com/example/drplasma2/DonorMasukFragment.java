package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorMasukFragment extends Fragment {

    private List<DonorMasuk> donors = new ArrayList<>();
    private DonorMasukAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_masuk, container, false);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");

        RecyclerView rvEvent = view.findViewById(R.id.rv_donor_masuk);
        rvEvent.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewAdapter = new DonorMasukAdapter(getContext(), donors);

        rvEvent.setAdapter(recyclerViewAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donors.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonorMasuk donor = snapshot.getValue(DonorMasuk.class);
                    donors.add(donor);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button buttonCoba = view.findViewById(R.id.buttoncoba);

        buttonCoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!donors.isEmpty()) {
                    DonorMasuk latestDonor = donors.get(donors.size() - 1);

                    Intent intent = new Intent(getContext(), DetailDonorMasukActivity.class)
                            ;

                    intent.putExtra("namaPencari", latestDonor.getNamaPencari());
                    intent.putExtra("golonganDarah", latestDonor.getGolonganDarah());
                    intent.putExtra("jumlahKebutuhanDarah", latestDonor.getJumlahKebutuhanDarah());
                    intent.putExtra("nomorTelepon", latestDonor.getNomorTelepon());
                    intent.putExtra("rumahSakit", latestDonor.getRumahSakit());
                    intent.putExtra("lokasiRumahSakit", latestDonor.getLokasiRumahSakit());

                    startActivity(intent);
                }
            }
        });

        return view;
    }
}
