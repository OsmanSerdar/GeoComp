package com.osser.geocomp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.codekidlabs.storagechooser.StorageChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class ct7 extends AppCompatActivity {

    public static final int FILEPICKER_PERMISSIONS = 1;

    int pressed;
    Button folder1;
    TextView edit1;
    Button folder2;
    TextView edit2;
    Button info1;
    Button info2;
    String conjugatepath;
    String calculatedpath;

    private RadioButton aSwitch;
    private RadioButton bSwitch;
    private RadioButton cSwitch;
    private RadioButton dSwitch;


    String elipsoide;

    ArrayList<String> NN = new ArrayList<String>();
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lng = new ArrayList<Double>();
    ArrayList<Double> L0 = new ArrayList<Double>();

    Button calculate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct7);

        folder1=findViewById(R.id.folder1);
        edit1=findViewById(R.id.editText1);

        aSwitch= (RadioButton) findViewById(R.id.switch1);
        bSwitch=(RadioButton) findViewById(R.id.switch2);
        cSwitch= (RadioButton) findViewById(R.id.switch3);
        dSwitch=(RadioButton) findViewById(R.id.switch4);

        folder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if(hasPermissions(ct7.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(ct7.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
                }
                pressed=1;
            }
        });

        calculate =findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String edit= edit1.getText().toString();

                if(TextUtils.isEmpty(edit)) {
                    edit1.setError("Please choose a file");
                    return;
                }

                try {
                    Scanner conjugate = new Scanner(new File(conjugatepath));



                    while (conjugate.hasNext()) {

                        NN.add(conjugate.next());
                        lat.add((conjugate.nextDouble()));
                        lng.add((conjugate.nextDouble()));
                        L0.add(( conjugate.nextDouble()));
                    }



                } catch (FileNotFoundException e) {
                    Toast.makeText(
                            ct7.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

                int n = NN.size();

                for (int i=0;i<n;i++){
                    L0.set(i,L0.get(i)*(PI/180));
                    lat.set(i,lat.get(i)*(PI/180));
                    lng.set(i,lng.get(i)*(PI/180));

                }

                ArrayList<Double> E = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));
                ArrayList<Double> N = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));

                if(aSwitch.isChecked()) {
                    elipsoide ="GRS80";

                }
                else{
                    elipsoide ="WGS84";
                }

                if (cSwitch.isChecked()) {
                    /*Calcuation for 3 degree*/
                    if (aSwitch.isChecked()) {
                        /*GRS80 parametres*/
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.0066943800229;
                        double e2u=(e2/(1-e2));

                        for (int i=0;i<n;i++) {
                            double A0 = 1 - (e2 * 0.25) - ((0.046875) * pow(e2, 2)) - ((0.01953125) * pow(e2, 3));
                            double A2 = (((0.375) * (e2)) + ((0.09375) * (pow(e2, 2))) + ((0.0439453125) * (pow(e2, 3))));
                            double A4 = (0.05859375) * (pow(e2, 2) + ((0.75) * pow(e2, 3)));
                            double A6 = (0.011393229167) * pow(e2, 3);
                            double m = a * (A0 *lat.get(i) - A2 * sin(2 *lat.get(i)) + A4 * sin(4 *lat.get(i)) - A6 * sin(6 *lat.get(i)));
                            double T = tan(lat.get(i));
                            double W = lng.get(i)-L0.get(i);
                            double p = a * ((1 - e2) / pow(1 - e2 * pow(sin(lat.get(i)), 2), 1.5));
                            double v = a / sqrt(1 - e2 * pow(sin(lat.get(i)), 2));
                            double w = v / p;
                            double Term1E = (pow(W, 2) * 0.166666666667) * pow(cos(lat.get(i)), 2) * (w - pow(T, 2));
                            double Term2E = (pow(W, 4) * 0.008333333333) * pow(cos(lat.get(i)), 4) * (4 * pow(w, 3) * (1 - 6 * T * T) + pow(w, 2) * (1 + 8 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term3E = (pow(W, 6) * 0.000198412698) * pow(cos(lat.get(i)), 6) * (61 - 479 * pow(T, 2) + 179 * pow(T, 4) - pow(T, 6));
                            double Term1N = (pow(W, 2) * 0.5) * v * sin(lat.get(i)) * cos(lat.get(i));
                            double Term2N = (pow(W, 4) * 0.041666666667) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 3) * (4 * w * w + w - T * T);
                            double Term3N = (pow(W, 6) * 0.001388888889) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 5) * (8 * pow(w, 4) * (11 - 24 * T * T) - 28 * pow(w, 3) * (1 - 6 * T * T) + w * w * (1 - 32 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term4N = (pow(W, 8) * 0.000024801587) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 7) * (1385 - 3111 * T * T + 543 * pow(T, 4) - pow(T, 6));
                            double yg = v * W * cos(lat.get(i)) * (1 + Term1E + Term2E + Term3E);
                            double xg = m + Term1N + Term2N + Term3N + Term4N;

                            E.set(i,yg+500000);
                            N.set(i,xg);
                        }


                    }
                    if (bSwitch.isChecked()) {
                        /*WGS84 parametres*/
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        for (int i=0;i<n;i++) {
                            double A0 = 1 - (e2 * 0.25) - ((0.046875) * pow(e2, 2)) - ((0.01953125) * pow(e2, 3));
                            double A2 = (((0.375) * (e2)) + ((0.09375) * (pow(e2, 2))) + ((0.0439453125) * (pow(e2, 3))));
                            double A4 = (0.05859375) * (pow(e2, 2) + ((0.75) * pow(e2, 3)));
                            double A6 = (0.011393229167) * pow(e2, 3);
                            double m = a * (A0 *lat.get(i) - A2 * sin(2 *lat.get(i) + A4 * sin(4 *lat.get(i))) - A6 * sin(6 *lat.get(i)));
                            double T = tan(lat.get(i));
                            double W = lng.get(i)-L0.get(i);
                            double p = a * ((1 - e2) / pow(1 - e2 * pow(sin(lat.get(i)), 2), 1.5));
                            double v = a / sqrt(1 - e2 * pow(sin(lat.get(i)), 2));
                            double w = v / p;
                            double Term1E = (pow(W, 2) * 0.166666666667) * pow(cos(lat.get(i)), 2) * (w - pow(T, 2));
                            double Term2E = (pow(W, 4) * 0.008333333333) * pow(cos(lat.get(i)), 4) * (4 * pow(w, 3) * (1 - 6 * T * T) + pow(w, 2) * (1 + 8 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term3E = (pow(W, 6) * 0.000198412698) * pow(cos(lat.get(i)), 6) * (61 - 479 * pow(T, 2) + 179 * pow(T, 4) - pow(T, 6));
                            double Term1N = (pow(W, 2) * 0.5) * v * sin(lat.get(i)) * cos(lat.get(i));
                            double Term2N = (pow(W, 4) * 0.041666666667) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 3) * (4 * w * w + w - T * T);
                            double Term3N = (pow(W, 6) * 0.001388888889) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 5) * (8 * pow(w, 4) * (11 - 24 * T * T) - 28 * pow(w, 3) * (1 - 6 * T * T) + w * w * (1 - 32 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term4N = (pow(W, 8) * 0.000024801587) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 7) * (1385 - 3111 * T * T + 543 * pow(T, 4) - pow(T, 6));
                            double yg = v * W * cos(lat.get(i)) * (1 + Term1E + Term2E + Term3E);
                            double xg = m + Term1N + Term2N + Term3N + Term4N;

                            E.set(i,yg + 500000);
                            N.set(i,xg);

                        }
                    }
                }

                if (dSwitch.isChecked()) {
                    /*Calcuation for 6 degree*/
                    if (aSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.00669438002290;
                        double e2u=(e2/(1-e2));


                        for (int i=0;i<n;i++) {
                            double A0 = 1 - (e2 * 0.25) - ((0.046875) * pow(e2, 2)) - ((0.01953125) * pow(e2, 3));
                            double A2 = (((0.375) * (e2)) + ((0.09375) * (pow(e2, 2))) + ((0.0439453125) * (pow(e2, 3))));
                            double A4 = (0.05859375) * (pow(e2, 2) + ((0.75) * pow(e2, 3)));
                            double A6 = (0.011393229167) * pow(e2, 3);
                            double m = a * (A0 *lat.get(i) - A2 * sin(2 *lat.get(i)) + A4 * sin(4 *lat.get(i)) - A6 * sin(6 *lat.get(i)));
                            double T = tan(lat.get(i));
                            double W = lng.get(i) - L0.get(i);
                            double p = a * ((1 - e2) / pow(1 - e2 * pow(sin(lat.get(i)), 2), 1.5));
                            double v = a / sqrt(1 - e2 * pow(sin(lat.get(i)), 2));
                            double w = v / p;
                            double Term1E = (pow(W, 2) * 0.166666666667) * pow(cos(lat.get(i)), 2) * (w - pow(T, 2));
                            double Term2E = (pow(W, 4) * 0.008333333333) * pow(cos(lat.get(i)), 4) * (4 * pow(w, 3) * (1 - 6 * T * T) + pow(w, 2) * (1 + 8 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term3E = (pow(W, 6) * 0.000198412698) * pow(cos(lat.get(i)), 6) * (61 - 479 * pow(T, 2) + 179 * pow(T, 4) - pow(T, 6));
                            double Term1N = (pow(W, 2) * 0.5) * v * sin(lat.get(i)) * cos(lat.get(i));
                            double Term2N = (pow(W, 4) * 0.041666666667) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 3) * (4 * w * w + w - T * T);
                            double Term3N = (pow(W, 6) * 0.001388888889) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 5) * (8 * pow(w, 4) * (11 - 24 * T * T) - 28 * pow(w, 3) * (1 - 6 * T * T) + w * w * (1 - 32 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term4N = (pow(W, 8) * 0.000024801587) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 7) * (1385 - 3111 * T * T + 543 * pow(T, 4) - pow(T, 6));
                            double yg = 0.9996 * v * W * cos(lat.get(i)) * (1 + Term1E + Term2E + Term3E);
                            double xg = 0.9996 * (m + Term1N + Term2N + Term3N + Term4N);
                            E.set(i,yg + 500000);
                            N.set(i,xg);
                        }
                    }
                    if (bSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        for (int i=0;i<n;i++) {
                            double A0 = 1 - (e2 * 0.25) - ((0.046875) * pow(e2, 2)) - ((0.01953125) * pow(e2, 3));
                            double A2 = (((0.375) * (e2)) + ((0.09375) * (pow(e2, 2))) + ((0.0439453125) * (pow(e2, 3))));
                            double A4 = (0.05859375) * (pow(e2, 2) + ((0.75) * pow(e2, 3)));
                            double A6 = (0.011393229167) * pow(e2, 3);
                            double m = a * (A0 * lat.get(i) - A2 * sin(2 * lat.get(i)) + A4 * sin(4 *lat.get(i)) - A6 * sin(6 * lat.get(i)));
                            double T = tan(lat.get(i));
                            double W = lng.get(i) - L0.get(i);
                            double p = a * ((1 - e2) / pow(1 - e2 * pow(sin(lat.get(i)), 2), 1.5));
                            double v = a / sqrt(1 - e2 * pow(sin(lat.get(i)), 2));
                            double w = v / p;
                            double Term1E = (pow(W, 2) * 0.166666666667) * pow(cos(lat.get(i)), 2) * (w - pow(T, 2));
                            double Term2E = (pow(W, 4) * 0.008333333333) * pow(cos(lat.get(i)), 4) * (4 * pow(w, 3) * (1 - 6 * T * T) + pow(w, 2) * (1 + 8 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term3E = (pow(W, 6) * 0.000198412698) * pow(cos(lat.get(i)), 6) * (61 - 479 * pow(T, 2) + 179 * pow(T, 4) - pow(T, 6));
                            double Term1N = (pow(W, 2) * 0.5) * v * sin(lat.get(i) * cos(lat.get(i)));
                            double Term2N = (pow(W, 4) * 0.041666666667) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 3) * (4 * w * w + w - T * T);
                            double Term3N = (pow(W, 6) * 0.001388888889) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 5) * (8 * pow(w, 4) * (11 - 24 * T * T) - 28 * pow(w, 3) * (1 - 6 * T * T) + w * w * (1 - 32 * T * T) - w * 2 * T * T + pow(T, 4));
                            double Term4N = (pow(W, 8) * 0.000024801587) * v * sin(lat.get(i)) * pow(cos(lat.get(i)), 7) * (1385 - 3111 * T * T + 543 * pow(T, 4) - pow(T, 6));
                            double yg = 0.9996 * v * W * cos(lat.get(i)) * (1 + Term1E + Term2E + Term3E);
                            double xg = 0.9996 * (m + Term1N + Term2N + Term3N + Term4N);
                            E.set(i,yg + 500000);
                            N.set(i,xg);
                        }
                    }
                }


                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar now = Calendar.getInstance();
                String UTC= formatter.format(now.getTime());
                String Createdby =String.format("Created By             :%s","GeoComp");
                String Date =String.format("Calculation Time       :%s",UTC);
                String nparemater=String.format("Number of Observations :%d",n);
                String Sytem=String.format("Reference Elipsoide    :%s",elipsoide);
                String brackets ="..................................................................................";
                String h1=String.format("%6s%14s%14s","PN","Easting(m)","Northing(m)");
                String h2=String.format("%6s%14s%14s%10s","PN","lat(degree)","long(degree)","DOM(deg)");

                StringBuffer header = new StringBuffer();
                header.append(Createdby+"\n"+Date+"\n"+Sytem+"\n"+nparemater+"\n"+"\n"+"\n"+"\n"+"TRANSFORMED TARGET POINTS"+"\n"+brackets+"\n"+h1+"\n"+brackets+"\n");

                StringBuffer bufferh2 = new StringBuffer();
                bufferh2.append("\n"+"TARGET POINTS"+"\n"+brackets+"\n"+h2+"\n"+brackets+"\n");


                ArrayList<String> StringPN = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%6s"," ")));
                ArrayList<String> StringX = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> StringY = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> StringZ = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%9s"," ")));
                ArrayList<String> Stringlat = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> Stringlong = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> Stringh = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%9s"," ")));

                for(int i=0;i<n;i++) {
                    StringPN.set(i,String.format("%6s",NN.get(i)));
                    StringX.set(i,String.format("%14.4f",E.get(i)));
                    StringY.set(i,String.format("%14.4f", N.get(i)));
                }

                for(int i=0;i<n;i++) {
                    StringPN.set(i,String.format("%6s",NN.get(i)));
                    Stringlat.set(i,String.format("%14.10f",lat.get(i)*(180/PI)));
                    Stringlong.set(i,String.format("%14.10f", lng.get(i)*(180/PI)));
                    Stringh.set(i,String.format("%10.0f",L0.get(i)*(180/PI)));
                }

                StringBuffer datah1 = new StringBuffer();
                StringBuffer datah2 = new StringBuffer();

                for(int i=0;i<n;i++){
                    datah1.append(StringPN.get(i)+StringX.get(i)+StringY.get(i)+"\n");
                }
                for(int i=0;i<n;i++){
                    datah2.append(StringPN.get(i)+Stringlat.get(i)+Stringlong.get(i)+Stringh.get(i)+"\n");
                }

                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "GeoComp");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "geographic_to_utm.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    String strh1 = datah1.toString();
                    String strh2 = datah2.toString();
                    writer.write(header+strh1+bufferh2+strh2);
                    writer.flush();
                    writer.close();
                    String absolute = gpxfile.getAbsolutePath();
                    Toast.makeText(
                            ct7.this,
                            "Saved :"+absolute,
                            Toast.LENGTH_SHORT
                    ).show();
                } catch (IOException e) {
                    Toast.makeText(
                            ct7.this,
                            "An error occurred.",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

            }
        });

    }

    public void ShowFilepicker(){
        // 1. Initialize dialog
        final StorageChooser chooser = new StorageChooser.Builder()
                .withActivity(ct7.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                Toast.makeText(ct7.this, "The file is : " + path, Toast.LENGTH_SHORT).show();
                String filename=path.substring(path.lastIndexOf("/")+1);
                if(pressed==1){
                    edit1.setText(filename);
                    conjugatepath=path;
                }
                if(pressed==2){
                    edit2.setText(filename);
                    calculatedpath=path;
                }
            }
        });
        // 3. Display File Picker !
        chooser.show();
    }
    /**
     * Helper method that verifies whether the permissions of a given array are granted or not.
     *
     * @param context
     * @param permissions
     * @return {Boolean}
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Callback that handles the status of the permissions request.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FILEPICKER_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                            ct7.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            ct7.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }
}
