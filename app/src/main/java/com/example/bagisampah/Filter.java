package com.example.bagisampah;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Filter extends AppCompatActivity {

    private SeekBar seekBarDistance, seekBarPrice;
    private TextView maxDistance, maxPrice, textMinPrice;
    private int hargaMax;
    private String hargaMaxString, hargaMaxStringFinal;
    private String jarakMaxString;
    private Button btnTerapkan;
    private CheckBox checkBoxGratis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        seekBarDistance = findViewById(R.id.seek_bar_distance);
        maxDistance = findViewById(R.id.max_distance);
        seekBarPrice = findViewById(R.id.seek_bar_price);
        maxPrice =findViewById(R.id.max_price);
        textMinPrice = findViewById(R.id.text_minprice);
        checkBoxGratis = findViewById(R.id.checkbox_gratis);
        btnTerapkan = findViewById(R.id.btn_terapkan);
        Bundle extras = getIntent().getExtras();


        if (extras != null){
            hargaMax = extras.getInt("hargaMax");
            hargaMaxString = String.valueOf(hargaMax);
            hargaMaxStringFinal = hargaMaxString;
            jarakMaxString = "10";

        }
        if (DataFilter.getFiltered()){
            seekBarDistance.setProgress(Integer.parseInt(DataFilter.getMaxJarak())-1);
            maxDistance.setText(DataFilter.getMaxJarak()+" KM");
            seekBarPrice.setMax(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1);
            jarakMaxString = DataFilter.getMaxJarak();
            hargaMaxStringFinal=DataFilter.getMaxHarga();

            if (DataFilter.getMaxHarga().equals("0")){
                checkBoxGratis.setChecked(true);
                seekBarPrice.setMax(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1);
                seekBarPrice.setProgress(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1);
                maxPrice.setText("Rp "+(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1)+"000");
                seekBarPrice.setEnabled(false);
                textMinPrice.setTextColor(Color.parseColor("#9e9e9e"));
                maxPrice.setTextColor(Color.parseColor("#9e9e9e"));
                hargaMaxStringFinal = "0";
            }else {
                seekBarPrice.setProgress(Integer.parseInt(DataFilter.getMaxHarga().substring(0, DataFilter.getMaxHarga().length()-3)));
                maxPrice.setText("Rp "+(Integer.parseInt(DataFilter.getMaxHarga().substring(0, DataFilter.getMaxHarga().length()-3)))+"000");
            }

        } else {
            seekBarPrice.setMax(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1);
            seekBarPrice.setProgress(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1);
            maxPrice.setText("Rp "+(Integer.parseInt(hargaMaxString.substring(0, hargaMaxString.length()-3))+1)+"000");
        }



        seekBarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                maxDistance.setText((i+1)+" KM");
                jarakMaxString = String.valueOf((i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(Filter.this, jarakMaxString, Toast.LENGTH_SHORT).show();
            }
        });
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                maxPrice.setText("Rp "+(i+1)+"000");
                hargaMaxString = i+1+"000";
                hargaMaxStringFinal = hargaMaxString;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Filter.this, hargaMaxString, Toast.LENGTH_SHORT).show();
            }
        });

        checkBoxGratis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxGratis.isChecked()){
                    seekBarPrice.setEnabled(false);
                    textMinPrice.setTextColor(Color.parseColor("#9e9e9e"));
                    maxPrice.setTextColor(Color.parseColor("#9e9e9e"));
                    hargaMaxStringFinal = "0";
                }
                else {
                    seekBarPrice.setEnabled(true);
                    textMinPrice.setTextColor(Color.parseColor("#212121"));
                    maxPrice.setTextColor(Color.parseColor("#212121"));
                    hargaMaxStringFinal = hargaMaxString;
                }
            }
        });

        btnTerapkan.setOnClickListener(view -> {
            DataFilter.setMaxJarak(jarakMaxString);
            DataFilter.setMaxHarga(hargaMaxStringFinal);
            DataFilter.setFiltered(true);
            Log.d("maxHarga", "onCreate: "+DataFilter.getMaxHarga());
            Intent intent = new Intent(Filter.this, MainActivity.class);
//            intent.putExtra("hargaMax", hargaMaxString);
//            intent.putExtra("distanceMax", jarakMaxString);
           //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Filter.this,MainActivity.class);
        intent.putExtra("fragmentToLoad",R.id.nav_cari_sampah);
        startActivity(intent);
        finish();
    }
}
