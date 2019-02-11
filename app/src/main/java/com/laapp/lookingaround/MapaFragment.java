package com.laapp.lookingaround;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laapp.lookingaround.dao.BancoDAO;
import com.laapp.lookingaround.model.Banco;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        LatLng uni = retornaCoordenadas("Rua Amador Bueno 389, Santo Amaro, Sao Paulo");
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(uni, 14);
        googleMap.moveCamera(update);

        BancoDAO bancoDAO = new BancoDAO(getContext());
        for (Banco banco : bancoDAO.buscaBancos()){
            LatLng coord = retornaCoordenadas(banco.getEndereco());
            if (coord != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coord);
                marcador.title(banco.getNome());
                marcador.snippet(String.valueOf(banco.getNota()));
                googleMap.addMarker(marcador);

                bancoDAO.close();
            }
        }

    }

    private LatLng retornaCoordenadas(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
