package com.example.bagisampah.View.MainMenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bagisampah.Model.DataSampah;
import com.example.bagisampah.R;
import com.example.bagisampah.Adapter.SampahAdapterSaya;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SampahSayaFragment extends Fragment {
    private List<DataSampah> data_sampahs = new ArrayList<DataSampah>();
    private RecyclerView recycler_sampah;
    private RecyclerView.Adapter adapter_sampah;
    private FirebaseDatabase db0, db;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth auth;
    HashMap<String,String> sampah = new HashMap<>();
    private SampahAdapterSaya adapter;
    private DatabaseReference mDatabase;
    private TextView txtKosong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate =inflater.inflate(R.layout.fragment_sampah_saya,null);
        recycler_sampah = inflate.findViewById(R.id.recycler_sampah);
        recycler_sampah.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycler_sampah.setLayoutManager(linearLayoutManager);
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        txtKosong = inflate.findViewById(R.id.txt_kosong);
        txtKosong.setVisibility(View.INVISIBLE);





        db.getReference("DBSampah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data_sampahs.clear();
                for (DataSnapshot sn : dataSnapshot.getChildren()){
                    if (sn.child("user").getValue(String.class).equalsIgnoreCase(auth.getCurrentUser().getUid()) && !sn.child("statusSampah").getValue(String.class).equalsIgnoreCase("Selesai") && !sn.child("statusSampah").getValue(String.class).equalsIgnoreCase("Dihapus")){
                        String img = sn.child("img").getValue(String.class);
                        String nama = sn.child("namaSampah").getValue(String.class);
                        String deskripsi = sn.child("deskripsiSampah").getValue(String.class);
                        String kategori = sn.child("kategoriSampah").getValue(String.class);
                        String latloc = sn.child("latlocSampah").getValue(String.class);
                        String longloc = sn.child("longlocSampah").getValue(String.class);
                        String harga = sn.child("hargaSampah").getValue(String.class);
                        String status = sn.child("statusSampah").getValue(String.class);
                        String jarak = sn.child("jarakSampah").getValue(String.class);
                        String alamat = sn.child("alamatSampah").getValue(String.class);
                        String uid = sn.child("user").getValue(String.class);
                        String key = sn.getKey();
                        String idPengambil = sn.child("idPengambil").getValue(String.class);
                        data_sampahs.add(new DataSampah(img, nama, deskripsi, kategori, latloc, longloc, harga, status, jarak, alamat, uid, key, idPengambil));
                        Collections.reverse(data_sampahs);
                    }
                }
                if (data_sampahs.size()==0){
                    txtKosong.setVisibility(View.VISIBLE);
                }
                adapter = new SampahAdapterSaya(getContext(), data_sampahs);
                recycler_sampah.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return inflate;
    }
}
