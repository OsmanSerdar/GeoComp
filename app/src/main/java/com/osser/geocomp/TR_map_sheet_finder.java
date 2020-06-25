package com.osser.geocomp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import javax.xml.transform.SourceLocator;


public class TR_map_sheet_finder extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String scale = "";

    EditText editText;
    EditText editText1;

    private Button goLocation;

    double latitude, longitude;

    private static final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tr_map_sheet_finder);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(TR_map_sheet_finder.this, button1);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popupmenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        scale = (String) item.getTitle();

                        Toast.makeText(TR_map_sheet_finder.this, "You have selected  : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        Button button = findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = findViewById(R.id.editText);
                editText1 = findViewById(R.id.editText1);

                String edit1= editText1.getText().toString();
                String edit2= editText1.getText().toString();

                if(TextUtils.isEmpty(edit1)) {
                    editText.setError("Please enter a value");
                    return;
                }
                if(TextUtils.isEmpty(edit2)){
                    editText1.setError("Please enter a value");
                    return;
                }

                latitude = Float.parseFloat(editText.getText().toString());
                longitude = Float.parseFloat(editText1.getText().toString());


                if (42.5 > latitude && latitude > 35.5 && 25.5 < longitude && longitude < 45) {
                    double x = 43;
                    double y = 25;
                    int i1 = 0;
                    double x1 = 0;
                    for (int i = 68; i < 83; i++) {
                        x = x - 0.5;
                        if (x - 0.5 < latitude && latitude < x) {
                            i1 = i;
                            x1 = x - 0.5;
                        }
                    }
                    int i2 = 0;
                    double y1 = 0;
                    for (int i = 15; i < 54; i++) {
                        y = y + 0.5;
                        if (y < longitude && longitude < y + 0.5) {
                            i2 = i;
                            y1 = y;
                        }
                    }
                    int a = 0;
                    int b = 0;
                    double x2 = 0;
                    double y2 = 0;
                    String i3 = " ";
                    if (x1 < latitude && latitude < x1 + 0.25) {
                        a = 2;
                        x2 = x1;
                    }
                    if (x1 + 0.25 < latitude && latitude < x1 + 0.50) {
                        a = 1;
                        x2 = x1 + 0.25;
                    }
                    if (y1 < longitude && longitude < y1 + 0.25) {
                        b = 1;
                        y2 = y1;
                    }
                    if (y1 + 0.25 < longitude && longitude < y1 + 0.50) {
                        b = 2;
                        y2 = y1 + 0.25;
                    }
                    double y2p = y2;
                    double x2p = x2;
                    if (a == 1 && b == 1) {
                        i3 = "-a-";
                    }
                    if (a == 1 && b == 2) {
                        i3 = "-b-";
                    }
                    if (a == 2 && b == 1) {
                        i3 = "-d-";
                    }
                    if (a == 2 && b == 2) {
                        i3 = "-c-";
                    }
                    double y3 = 0;
                    int a1 = 0;
                    x2 = x2 - 0.05;
                    y2 = y2 - 0.05;
                    double x3 = 0;
                    for (int i = 1; i < 6; i++) {
                        x2 = x2 + 0.05;
                        if (x2 < latitude && latitude < x2 + 0.05) {
                            a1 = i;
                            x3 = x2;
                        }
                    }
                    int b1 = 0;
                    for (int i = 1; i < 6; i++) {
                        y2 = y2 + 0.05;
                        if (y2 < longitude && longitude < y2 + 0.05) {
                            b1 = i;
                            y3 = y2;
                        }
                    }
                    int c = ((6 - a1) - 1) * 5 + b1;
                    int i4 = c;
                    int a2 = 0;
                    double x4 = 0;
                    int b2 = 0;
                    double y4 = 0;
                    String i5 = "";
                    if (x3 < latitude && latitude < x3 + 0.025) {
                        a2 = 2;
                        x4 = x3;
                    }
                    if (x3 + 0.025 < latitude && latitude < x3 + 0.05) {
                        a2 = 1;
                        x4 = x3 + 0.025;
                    }
                    if (y3 < longitude && longitude < y3 + 0.025) {
                        b2 = 1;
                        y4 = y3;
                    }
                    if (y3 + 0.025 < longitude && longitude < y3 + 0.05) {
                        b2 = 2;
                        y4 = y3 + 0.025;
                    }
                    if (a2 == 1 && b2 == 1) {
                        i5 = "-a-";
                    }
                    if (a2 == 1 && b2 == 2) {
                        i5 = "-b-";
                    }
                    if (a2 == 2 && b2 == 1) {
                        i5 = "-d-";
                    }
                    if (a2 == 2 && b2 == 2) {
                        i5 = "-c-";
                    }
                    int a3 = 0;
                    double x5 = 0;
                    int b3 = 0;
                    double y5 = 0;
                    if (x4 < latitude && latitude < x4 + 0.0125) {
                        a3 = 2;
                        x5 = x4;
                    }
                    if (x4 + 0.0125 < latitude && latitude < x4 + 0.025) {
                        a3 = 1;
                        x5 = x4 + 0.0125;
                    }
                    if (y4 < longitude && longitude < y4 + 0.0125) {
                        b3 = 1;
                        y5 = y4;
                    }
                    if (y4 + 0.0125 < longitude && longitude < y4 + 0.025) {
                        b3 = 2;
                        y5 = y4 + 0.0125;
                    }
                    int i6 = 0;
                    int a4 = 0;
                    int b4 = 0;
                    double x6 = 0;
                    double y6 = 0;
                    String i7 = "";
                    if (a3 == 1 && b3 == 1) {
                        i6 = 1;
                    }
                    if (a3 == 1 && b3 == 2) {
                        i6 = 2;
                    }
                    if (a3 == 2 && b3 == 1) {
                        i6 = 4;
                    }
                    if (a3 == 2 && b3 == 2) {
                        i6 = 3;
                    }
                    if (x5 < latitude && latitude < x5 + 0.00625) {
                        a4 = 2;
                        x6 = x5;
                    }
                    if (x5 + 0.00625 < latitude && latitude < x5 + 0.0125) {
                        a4 = 1;
                        x6 = x5 + 0.00625;
                    }
                    if (y5 < longitude && longitude < y5 + 0.00625) {
                        b4 = 1;
                        y6 = y5;
                    }
                    if (y5 + 0.00625 < longitude && longitude < y5 + 0.0125) {
                        b4 = 2;
                        y6 = y5 + 0.00625;
                    }
                    if (a4 == 1 && b4 == 1) {
                        i7 = "-a";
                    }
                    if (a4 == 1 && b4 == 2) {
                        i7 = "-b";
                    }
                    if (a4 == 2 && b4 == 1) {
                        i7 = "-d";
                    }
                    if (a4 == 2 && b4 == 2) {
                        i7 = "-c";
                    }
                    String paftano1 = "";
                    switch (scale) {

                        case "1/100000":
                            paftano1 = String.format("%c%d", (char) i1, i2);
                            // Instantiates a new Polygon object and adds points to define a rectangle
                            Polygon polygon100000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x1, y1),
                                            new LatLng(x1 + 0.5, y1),
                                            new LatLng(x1 + 0.5, y1 + 0.5),
                                            new LatLng(x1, y1 + 0.5),
                                            new LatLng(x1, y1))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            LatLng paftano100000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano100000).title("Map Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano100000, 7.5F));
                            break;

                        case "1/50000":
                            paftano1 = String.format("%c%d%s", (char) i1, i2, i3);
                            // Instantiates a new Polygon object and adds points to define a rectangle
                            Polygon polygon50000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x2p, y2p),
                                            new LatLng(x2p + 0.25, y2p),
                                            new LatLng(x2p + 0.25, y2p + 0.25),
                                            new LatLng(x2p, y2p + 0.25),
                                            new LatLng(x2p, y2p))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            LatLng paftano50000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano50000).title("Map Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano50000, 9F));
                            break;

                        case "1/10000":
                            paftano1 = String.format("%c%d%s%d", (char) i1, i2, i3, i4);
                            // Instantiates a new Polygon object and adds points to define a rectangle
                            Polygon polygon10000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x3, y3),
                                            new LatLng(x3 + 0.05, y3),
                                            new LatLng(x3 + 0.05, y3 + 0.05),
                                            new LatLng(x3, y3 + 0.05),
                                            new LatLng(x3, y3))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            LatLng paftano10000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano10000).title("Map Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano10000, 10.5F));
                            break;

                        case "1/5000":
                            paftano1 = String.format("%c%d%s%d%s", (char) i1, i2, i3, i4, i5);
                            // Instantiates a new Polygon object and adds points to define a rectangle
                            Polygon polygon5000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x4, y4),
                                            new LatLng(x4 + 0.025, y4),
                                            new LatLng(x4 + 0.025, y4 + 0.025),
                                            new LatLng(x4, y4 + 0.025),
                                            new LatLng(x4, y4))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            LatLng paftano5000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano5000).title("Paper Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano5000, 12F));
                            break;

                        case "1/2000":
                            paftano1 = String.format("%c%d%s%d%s%d", (char) i1, i2, i3, i4, i5, i6);
                            // Instantiates a new Polygon object and adds points to define a rectangle
                            Polygon polygon2000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x5, y5),
                                            new LatLng(x5 + 0.0125, y5),
                                            new LatLng(x5 + 0.0125, y5 + 0.0125),
                                            new LatLng(x5, y5 + 0.0125),
                                            new LatLng(x5, y5))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            // Get back the mutable Polygon
                            LatLng paftano2000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano2000).title("Map Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano2000, 13.5F));
                            break;

                        default:
                            paftano1 = String.format("%c%d%s%d%s%d%s", (char) i1, i2, i3, i4, i5, i6, i7);
                            Polygon polygon1000 = mMap.addPolygon(new PolygonOptions()
                                    .add(new LatLng(x6, y6),
                                            new LatLng(x6 + 0.00625, y6),
                                            new LatLng(x6 + 0.00625, y6 + 0.00625),
                                            new LatLng(x6, y6 + 0.00625),
                                            new LatLng(x6, y6))
                                    .strokeColor(Color.BLACK)
                                    .fillColor(0x90020000));
                            LatLng paftano1000 = new LatLng(latitude, longitude);
                            mMap.addMarker(new MarkerOptions().position(paftano1000).title("Map Sheet Number: " + paftano1));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano1000, 15F));
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This point is not in TURKEY", Toast.LENGTH_LONG).show();
                }

            }
        });
        goLocation = findViewById(R.id.golocation);
        goLocation.setOnClickListener(new View.OnClickListener() {


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();

                } else {

                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                TR_map_sheet_finder.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_LOCATION
                        );
                        return;

                    } else {
                        final LocationRequest locationRequest = new LocationRequest();
                        locationRequest.setInterval(5000);
                        locationRequest.setFastestInterval(0);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            Toast.makeText(getApplicationContext(), "Permission denied!" , Toast.LENGTH_LONG).show();
                            return;
                        }
                        LocationServices.getFusedLocationProviderClient(TR_map_sheet_finder.this)
                                .requestLocationUpdates(locationRequest, new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        LocationServices.getFusedLocationProviderClient(TR_map_sheet_finder.this)
                                                .removeLocationUpdates(this);
                                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                                            int latestlocationindex = locationResult.getLocations().size() - 1;
                                            latitude = locationResult.getLocations().get(latestlocationindex).getLatitude();
                                            longitude = locationResult.getLocations().get(latestlocationindex).getLongitude();
                                        }
                                    }
                                }, Looper.getMainLooper());
                    }

                    if (latitude == 0 && longitude == 0) {
                        Toast.makeText(getApplicationContext(), "Please press one more time to get the current location", Toast.LENGTH_LONG).show();
                    } else if (42.5 > latitude && latitude > 35.5 && 25.5 < longitude && longitude < 45) {

                        Toast.makeText(getApplicationContext(), "latitude:" + latitude + "\n" + "longitude:" + longitude, Toast.LENGTH_LONG).show();

                        double x = 43;
                        double y = 25;
                        int i1 = 0;
                        double x1 = 0;
                        for (int i = 68; i < 83; i++) {
                            x = x - 0.5;
                            if (x - 0.5 < latitude && latitude < x) {
                                i1 = i;
                                x1 = x - 0.5;
                            }
                        }
                        int i2 = 0;
                        double y1 = 0;
                        for (int i = 15; i < 54; i++) {
                            y = y + 0.5;
                            if (y < longitude && longitude < y + 0.5) {
                                i2 = i;
                                y1 = y;
                            }
                        }
                        int a = 0;
                        int b = 0;
                        double x2 = 0;
                        double y2 = 0;
                        String i3 = " ";
                        if (x1 < latitude && latitude < x1 + 0.25) {
                            a = 2;
                            x2 = x1;
                        }
                        if (x1 + 0.25 < latitude && latitude < x1 + 0.50) {
                            a = 1;
                            x2 = x1 + 0.25;
                        }
                        if (y1 < longitude && longitude < y1 + 0.25) {
                            b = 1;
                            y2 = y1;
                        }
                        if (y1 + 0.25 < longitude && longitude < y1 + 0.50) {
                            b = 2;
                            y2 = y1 + 0.25;
                        }
                        double y2p = y2;
                        double x2p = x2;
                        if (a == 1 && b == 1) {
                            i3 = "-a-";
                        }
                        if (a == 1 && b == 2) {
                            i3 = "-b-";
                        }
                        if (a == 2 && b == 1) {
                            i3 = "-d-";
                        }
                        if (a == 2 && b == 2) {
                            i3 = "-c-";
                        }
                        double y3 = 0;
                        int a1 = 0;
                        x2 = x2 - 0.05;
                        y2 = y2 - 0.05;
                        double x3 = 0;
                        for (int i = 1; i < 6; i++) {
                            x2 = x2 + 0.05;
                            if (x2 < latitude && latitude < x2 + 0.05) {
                                a1 = i;
                                x3 = x2;
                            }
                        }
                        int b1 = 0;
                        for (int i = 1; i < 6; i++) {
                            y2 = y2 + 0.05;
                            if (y2 < longitude && longitude < y2 + 0.05) {
                                b1 = i;
                                y3 = y2;
                            }
                        }
                        int c = ((6 - a1) - 1) * 5 + b1;
                        int i4 = c;
                        int a2 = 0;
                        double x4 = 0;
                        int b2 = 0;
                        double y4 = 0;
                        String i5 = "";
                        if (x3 < latitude && latitude < x3 + 0.025) {
                            a2 = 2;
                            x4 = x3;
                        }
                        if (x3 + 0.025 < latitude && latitude < x3 + 0.05) {
                            a2 = 1;
                            x4 = x3 + 0.025;
                        }
                        if (y3 < longitude && longitude < y3 + 0.025) {
                            b2 = 1;
                            y4 = y3;
                        }
                        if (y3 + 0.025 < longitude && longitude < y3 + 0.05) {
                            b2 = 2;
                            y4 = y3 + 0.025;
                        }
                        if (a2 == 1 && b2 == 1) {
                            i5 = "-a-";
                        }
                        if (a2 == 1 && b2 == 2) {
                            i5 = "-b-";
                        }
                        if (a2 == 2 && b2 == 1) {
                            i5 = "-d-";
                        }
                        if (a2 == 2 && b2 == 2) {
                            i5 = "-c-";
                        }
                        int a3 = 0;
                        double x5 = 0;
                        int b3 = 0;
                        double y5 = 0;
                        if (x4 < latitude && latitude < x4 + 0.0125) {
                            a3 = 2;
                            x5 = x4;
                        }
                        if (x4 + 0.0125 < latitude && latitude < x4 + 0.025) {
                            a3 = 1;
                            x5 = x4 + 0.0125;
                        }
                        if (y4 < longitude && longitude < y4 + 0.0125) {
                            b3 = 1;
                            y5 = y4;
                        }
                        if (y4 + 0.0125 < longitude && longitude < y4 + 0.025) {
                            b3 = 2;
                            y5 = y4 + 0.0125;
                        }
                        int i6 = 0;
                        int a4 = 0;
                        int b4 = 0;
                        double x6 = 0;
                        double y6 = 0;
                        String i7 = "";
                        if (a3 == 1 && b3 == 1) {
                            i6 = 1;
                        }
                        if (a3 == 1 && b3 == 2) {
                            i6 = 2;
                        }
                        if (a3 == 2 && b3 == 1) {
                            i6 = 4;
                        }
                        if (a3 == 2 && b3 == 2) {
                            i6 = 3;
                        }
                        if (x5 < latitude && latitude < x5 + 0.00625) {
                            a4 = 2;
                            x6 = x5;
                        }
                        if (x5 + 0.00625 < latitude && latitude < x5 + 0.0125) {
                            a4 = 1;
                            x6 = x5 + 0.00625;
                        }
                        if (y5 < longitude && longitude < y5 + 0.00625) {
                            b4 = 1;
                            y6 = y5;
                        }
                        if (y5 + 0.00625 < longitude && longitude < y5 + 0.0125) {
                            b4 = 2;
                            y6 = y5 + 0.00625;
                        }
                        if (a4 == 1 && b4 == 1) {
                            i7 = "-a";
                        }
                        if (a4 == 1 && b4 == 2) {
                            i7 = "-b";
                        }
                        if (a4 == 2 && b4 == 1) {
                            i7 = "-d";
                        }
                        if (a4 == 2 && b4 == 2) {
                            i7 = "-c";
                        }
                        String paftano1 = "";
                        switch (scale) {

                            case "1/100000":
                                paftano1 = String.format("%c%d", (char) i1, i2);
                                // Instantiates a new Polygon object and adds points to define a rectangle
                                Polygon polygon100000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x1, y1),
                                                new LatLng(x1 + 0.5, y1),
                                                new LatLng(x1 + 0.5, y1 + 0.5),
                                                new LatLng(x1, y1 + 0.5),
                                                new LatLng(x1, y1))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                LatLng paftano100000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano100000).title("Map Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano100000, 7.5F));
                                break;

                            case "1/50000":
                                paftano1 = String.format("%c%d%s", (char) i1, i2, i3);
                                // Instantiates a new Polygon object and adds points to define a rectangle
                                Polygon polygon50000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x2p, y2p),
                                                new LatLng(x2p + 0.25, y2p),
                                                new LatLng(x2p + 0.25, y2p + 0.25),
                                                new LatLng(x2p, y2p + 0.25),
                                                new LatLng(x2p, y2p))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                LatLng paftano50000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano50000).title("Map Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano50000, 9F));
                                break;

                            case "1/10000":
                                paftano1 = String.format("%c%d%s%d", (char) i1, i2, i3, i4);
                                // Instantiates a new Polygon object and adds points to define a rectangle
                                Polygon polygon10000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x3, y3),
                                                new LatLng(x3 + 0.05, y3),
                                                new LatLng(x3 + 0.05, y3 + 0.05),
                                                new LatLng(x3, y3 + 0.05),
                                                new LatLng(x3, y3))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                LatLng paftano10000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano10000).title("Map Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano10000, 10.5F));
                                break;

                            case "1/5000":
                                paftano1 = String.format("%c%d%s%d%s", (char) i1, i2, i3, i4, i5);
                                // Instantiates a new Polygon object and adds points to define a rectangle
                                Polygon polygon5000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x4, y4),
                                                new LatLng(x4 + 0.025, y4),
                                                new LatLng(x4 + 0.025, y4 + 0.025),
                                                new LatLng(x4, y4 + 0.025),
                                                new LatLng(x4, y4))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                LatLng paftano5000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano5000).title("Paper Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano5000, 12F));
                                break;

                            case "1/2000":
                                paftano1 = String.format("%c%d%s%d%s%d", (char) i1, i2, i3, i4, i5, i6);
                                // Instantiates a new Polygon object and adds points to define a rectangle
                                Polygon polygon2000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x5, y5),
                                                new LatLng(x5 + 0.0125, y5),
                                                new LatLng(x5 + 0.0125, y5 + 0.0125),
                                                new LatLng(x5, y5 + 0.0125),
                                                new LatLng(x5, y5))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                // Get back the mutable Polygon
                                LatLng paftano2000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano2000).title("Map Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano2000, 13.5F));
                                break;

                            default:
                                paftano1 = String.format("%c%d%s%d%s%d%s", (char) i1, i2, i3, i4, i5, i6, i7);
                                Polygon polygon1000 = mMap.addPolygon(new PolygonOptions()
                                        .add(new LatLng(x6, y6),
                                                new LatLng(x6 + 0.00625, y6),
                                                new LatLng(x6 + 0.00625, y6 + 0.00625),
                                                new LatLng(x6, y6 + 0.00625),
                                                new LatLng(x6, y6))
                                        .strokeColor(Color.BLACK)
                                        .fillColor(0x90020000));
                                LatLng paftano1000 = new LatLng(latitude, longitude);
                                mMap.addMarker(new MarkerOptions().position(paftano1000).title("Map Sheet Number: " + paftano1));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paftano1000, 15F));
                                break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "This point is not in TURKEY", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng Turkey = new LatLng(39.925533, 32.866287);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Turkey, 4.5F));

        mMap = googleMap;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getCurentlocation();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Permission denied :(", Toast.LENGTH_LONG).show();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getCurentlocation() {

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
