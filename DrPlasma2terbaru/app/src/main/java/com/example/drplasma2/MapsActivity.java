package com.example.drplasma2;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.drplasma2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private SearchView mapSearchView;
    private Marker currentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //mencari lokasi.
        mapSearchView = findViewById(R.id.mapSearch);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        //jika teks dikirim, aplikasi menggunakan Geocoder untuk mendapatkan koordinat lokasi dari teks dan menambahkan marker di peta.
        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location !=null){
                    //alamat jadi koordinat
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    //mengambil daftar alamat dari Geocoder. Jika sukses, daftar alamat disimpan dalam addressList
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        //Membuat objek LatLng berdasarkan koordinat
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        // Hapus marker sebelum menambahkan yang baru
                        if (currentMarker != null) {
                            currentMarker.remove();
                        }

                        // Tambahkan marker baru
                        currentMarker = myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 70));
                    }
                }
                //Ini menunjukkan bahwa kita telah menangani event onQueryTextSubmit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        //konfirm lokasi (balik ke form permintaan)
        Button konfirmasiButton = findViewById(R.id.konfirmap);
        konfirmasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menetapkan listener ke tombol konfirmasi untuk menanggapi event ketika tombol tersebut diklik.
                LatLng currentLocation = getCurrentLocation();
                if (currentLocation != null){
                    String location = getLocationName(currentLocation);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("lokasi", location);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(MapsActivity.this, "Lokasi belum dipilih", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Hapus marker sebelum menambahkan yang baru
                if (currentMarker != null) {
                    currentMarker.remove();
                }

                currentMarker = myMap.addMarker(new MarkerOptions().position(latLng).title("Marker Baru"));
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 70));
            }
        });
    }

    private LatLng getCurrentLocation(){
        if (myMap !=null){
            return myMap.getCameraPosition().target;
        }
        return null;
    }

    private String getLocationName(LatLng latLng){
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && !addressList.isEmpty()){
                return addressList.get(0).getAddressLine(0);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}