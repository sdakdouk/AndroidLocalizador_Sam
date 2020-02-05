package com.example.dis_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;


    private EditText ad2;
    private Button b1;
    private Button b2;
    private TextView res;
    private TextView dir1;
    private ArrayList<Address> addresses1;
    private ArrayList<Address> addresses2;
     double longitudeGPS ;
     double latitudeGPS ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        b2 = (Button) findViewById(R.id.b3);
        dir1 = (TextView) findViewById(R.id.dir1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    ArrayList<Address> addresses = new ArrayList<Address>();
                                    Geocoder geocoder1 = new Geocoder(MainActivity.this);
                                    try {
                                        addresses = (ArrayList<Address>) geocoder1.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                                        if (addresses != null && addresses.size() > 0) {
                                            Address address = addresses.get(0);
                                            // sending back first address line and locality
                                            dir1.setText(address.getAddressLine(0) + ", " + address.getLocality());
                                        }} catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    }
                            }
                        });
            }
        });



        ad2 = (EditText) findViewById(R.id.ad2);
        res = (TextView) findViewById(R.id.res);
        b1 = (Button) findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder1 = new Geocoder(MainActivity.this);
                Geocoder geocoder2 = new Geocoder(MainActivity.this);
                try {
                    addresses1 = (ArrayList<Address>) geocoder1.getFromLocationName(dir1.getText().toString(), 1);
                    Address address1 = addresses1.get(0);
                    Location location1 = new Location("punto A");
                    location1.setLatitude(address1.getLatitude());
                    location1.setLongitude(address1.getLongitude());
                    addresses2 = (ArrayList<Address>) geocoder2.getFromLocationName(ad2.getText().toString(), 1);
                    Address address2 = addresses2.get(0);
                    Location location2 = new Location("punto B");
                    location2.setLatitude(address2.getLatitude());
                    location2.setLongitude(address2.getLongitude());
                    double disKm = location1.distanceTo(location2) / 1000;

                    res.setText("La distancia es "+disKm+" la latitud y longitud de la direcciones son" +
                            " dir1Lat= "+address1.getLatitude()+" dir1Lon= "+address1.getLongitude()+
                            " y dir2Lat= "+ address2.getLatitude()+" dir2Lon= "+address2.getLongitude());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });




    }

}
