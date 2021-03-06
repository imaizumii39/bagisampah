package com.example.bagisampah.Adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bagisampah.Model.DataSampah;
import com.example.bagisampah.R;
import com.example.bagisampah.View.DetailSampah.DetailSampah;
import com.example.bagisampah.View.DetailSampah.DetailSampahSaya;
import com.example.bagisampah.View.DetailSampah.DetailSampahTerbooking;
import com.example.bagisampah.View.DetailSampah.DetailSampahTerbookingSaya;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class SampahAdapter extends RecyclerView.Adapter<SampahAdapter.ViewHolder>{
    private Context context;
    private List<DataSampah> listSampahs;
    private FirebaseAuth auth;
    private FirebaseDatabase db;


    public SampahAdapter(Context context, List<DataSampah> my_data) {
        this.context = context;
        this.listSampahs = my_data;
    }

    @NonNull
    @Override
    public SampahAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sampah,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SampahAdapter.ViewHolder holder, final int position) {
        DataSampah listSampah = listSampahs.get(position);
        holder.nama.setText(listSampah.getNama());
        holder.deskripsi.setText(listSampah.getDeskripsi());
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
//        if (listSampah.getUser().equalsIgnoreCase(auth.getCurrentUser().getUid())&&listSampah.getStatus().equalsIgnoreCase("Available")){
//            holder.harga.setBackgroundResource(R.drawable.bg_jarak_round);
//            holder.harga.setText("Edit");
//            holder.jarak.setBackgroundResource(R.drawable.bg_hapus_round);
//            holder.jarak.setText("Hapus");
//            holder.jarak.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.harga.setOnClickListener(view ->{
//                Intent intent = new Intent(view.getContext(), EditSampah.class);
//                intent.putExtra("imgSampah",listSampah.getImg());
//                intent.putExtra("namaSampah",listSampah.getNama());
//                intent.putExtra("deskripsiSampah",listSampah.getDeskripsi());
//                intent.putExtra("hargaSampah",listSampah.getHarga());
//                intent.putExtra("alamatUser",listSampah.getAlamat());
//                intent.putExtra("kategoriSampah",listSampah.getKategori());
//                intent.putExtra("key", listSampah.getKey());
//                intent.putExtra("uid", listSampah.getUser());
//                intent.putExtra("latLoc", listSampah.getLatloc());
//                intent.putExtra("longLoc", listSampah.getLongloc());
//                Log.d(TAG, "uidgetuser: "+listSampah.getUser());
//                Log.d(TAG, "key1: "+listSampah.getKey());
//                view.getContext().startActivity(intent);
//            } );
//            holder.jarak.setOnClickListener(view -> {
//                db.getReference("DBSampah").child(listSampah.getKey()).child("statusSampah").setValue("Dihapus");
//            });
//        } else if(listSampah.getUser().equalsIgnoreCase(auth.getCurrentUser().getUid()) && listSampah.getStatus().equalsIgnoreCase("Terbooking")){
//            holder.harga.setVisibility(View.INVISIBLE);
//            holder.jarak.setBackgroundResource(R.drawable.bg_gratis_round);
//            holder.jarak.setTextColor(Color.parseColor("#FFFFFF"));
//            holder.jarak.setText("Terbooking");
//            holder.jarak.setWidth(180);
        if(listSampah.getHarga().equalsIgnoreCase("0")){
            holder.harga.setBackgroundResource(R.drawable.bg_gratis_round);
            holder.harga.setText("Gratis");
            holder.harga.setTextColor(Color.parseColor("#FFFFFF"));
            holder.jarak.setBackgroundResource(R.drawable.bg_jarak_round);
            holder.jarak.setText(listSampah.getJarak()+" KM");
        }
        else {
            holder.harga.setBackgroundResource(R.drawable.bg_jarak_round);
            holder.harga.setText("Rp "+listSampah.getHarga());
            holder.jarak.setBackgroundResource(R.drawable.bg_jarak_round);
            Log.d(TAG, "GETJARAK: "+listSampah.getJarak());
            holder.jarak.setText(listSampah.getJarak()+" KM");
        }
        Picasso.get().load(listSampah.getImg()).into(holder.imgSampah);

        holder.card_sampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                auth = FirebaseAuth.getInstance();
                if(listSampah.getUser().equalsIgnoreCase(auth.getCurrentUser().getUid()) && listSampah.getStatus().equalsIgnoreCase("Available")){
                    /* sampah saya */
                    Log.d(TAG, "onClick: terklik");
                    Log.d(TAG, "onClick: "+ listSampah.getNama());
                    Intent intent = new Intent(v.getContext(), DetailSampahSaya.class);
                    intent.putExtra("imgSampah",listSampah.getImg());
                    intent.putExtra("namaSampah",listSampah.getNama());
                    intent.putExtra("deskripsiSampah",listSampah.getDeskripsi());
                    intent.putExtra("hargaSampah",listSampah.getHarga());
                    intent.putExtra("alamatUser",listSampah.getAlamat());
                    intent.putExtra("kategoriSampah",listSampah.getKategori());
                    intent.putExtra("key", listSampah.getKey());
                    intent.putExtra("uid", listSampah.getUser());
                    intent.putExtra("latLoc", listSampah.getLatloc());
                    intent.putExtra("longLoc", listSampah.getLongloc());
                    Log.d(TAG, "uidgetuser: "+listSampah.getUser());
                    Log.d(TAG, "key1: "+listSampah.getKey());
                    v.getContext().startActivity(intent);
                }else if(listSampah.getUser().equalsIgnoreCase(auth.getCurrentUser().getUid()) && listSampah.getStatus().equalsIgnoreCase("Terbooking")){
                    Intent intent = new Intent(v.getContext(), DetailSampahTerbookingSaya.class);
                    intent.putExtra("imgSampah",listSampah.getImg());
                    intent.putExtra("namaSampah",listSampah.getNama());
                    intent.putExtra("deskripsiSampah",listSampah.getDeskripsi());
                    intent.putExtra("hargaSampah",listSampah.getHarga());
                    intent.putExtra("alamatUser",listSampah.getAlamat());
                    intent.putExtra("kategoriSampah",listSampah.getKategori());
                    intent.putExtra("key", listSampah.getKey());
                    intent.putExtra("uid", listSampah.getUser());
                    intent.putExtra("idPengambil", listSampah.getIdPengambil());
                    intent.putExtra("latLoc", listSampah.getLatloc());
                    intent.putExtra("longLoc", listSampah.getLongloc());
                    v.getContext().startActivity(intent);
                }else if(listSampah.getIdPengambil().equalsIgnoreCase(auth.getCurrentUser().getUid()) && listSampah.getStatus().equalsIgnoreCase("Terbooking")){
                    Intent intent = new Intent(v.getContext(), DetailSampahTerbooking.class);
                    intent.putExtra("imgSampah",listSampah.getImg());
                    intent.putExtra("namaSampah",listSampah.getNama());
                    intent.putExtra("deskripsiSampah",listSampah.getDeskripsi());
                    intent.putExtra("hargaSampah",listSampah.getHarga());
                    intent.putExtra("alamatUser",listSampah.getAlamat());
                    intent.putExtra("kategoriSampah",listSampah.getKategori());
                    intent.putExtra("key", listSampah.getKey());
                    intent.putExtra("uid", listSampah.getUser());
                    intent.putExtra("idPengambil", listSampah.getIdPengambil());
                    intent.putExtra("latLoc", listSampah.getLatloc());
                    intent.putExtra("longLoc", listSampah.getLongloc());
                    v.getContext().startActivity(intent);
                }
                else{
                    Log.d(TAG, "onClick: terklik");
                    Log.d(TAG, "onClick: "+ listSampah.getNama());
                    Intent intent = new Intent(v.getContext(), DetailSampah.class);
                    intent.putExtra("imgSampah",listSampah.getImg());
                    intent.putExtra("namaSampah",listSampah.getNama());
                    intent.putExtra("deskripsiSampah",listSampah.getDeskripsi());
                    intent.putExtra("kategoriSampah",listSampah.getKategori());
                    intent.putExtra("hargaSampah",listSampah.getHarga());
                    intent.putExtra("alamatUser",listSampah.getAlamat());
                    intent.putExtra("key", listSampah.getKey());
                    intent.putExtra("latLoc", listSampah.getLatloc());
                    intent.putExtra("longLoc", listSampah.getLongloc());
                    Log.d(TAG, "key2: "+listSampah.getKey());
                    intent.putExtra("uid", listSampah.getUser());
                    v.getContext().startActivity(intent);
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return listSampahs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama, deskripsi, harga, jarak;
        private ImageView imgSampah;
        public CardView card_sampah;
        public ViewHolder(View itemView){
            super(itemView);
            card_sampah= (CardView) itemView.findViewById(R.id.card_sampah);
            nama = itemView.findViewById(R.id.nama);
            deskripsi = itemView.findViewById(R.id.deskripsi);
            harga = itemView.findViewById(R.id.harga);
            jarak = itemView.findViewById(R.id.jarak);
            imgSampah = itemView.findViewById(R.id.imgSampah);
        }
    }
}
