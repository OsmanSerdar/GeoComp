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
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class ct5 extends AppCompatActivity {

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

    String elipsoide;

    ArrayList<String> NN = new ArrayList<String>();
    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lng = new ArrayList<Double>();
    ArrayList<Double> h = new ArrayList<Double>();

    Button calculate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct5);

        folder1=findViewById(R.id.folder1);
        edit1=findViewById(R.id.editText1);

        aSwitch= (RadioButton) findViewById(R.id.switch1);
        bSwitch=(RadioButton) findViewById(R.id.switch2);

        folder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if(hasPermissions(ct5.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(ct5.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
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
                        h.add((conjugate.nextDouble()));
                    }



                } catch (FileNotFoundException e) {
                    Toast.makeText(
                            ct5.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

                int n = NN.size();

                ArrayList<Double> X = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));
                ArrayList<Double> Y = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));
                ArrayList<Double> Z = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));

                if(aSwitch.isChecked()) {
                    elipsoide ="GRS80";

                }
                else{
                    elipsoide ="WGS84";
                }

                if(aSwitch.isChecked()) {

                    int a =6378137;
                    double b=6356752.314140;
                    double e2=0.00669438002290;



                    for(int i=0;i<n;i++) {
                        lat.set(i,lat.get(i)* (Double)(Math.PI / 180));
                        lng.set(i,lng.get(i)* (Double)(Math.PI / 180));
                        double N = a / Math.sqrt(1 - (e2) * (Math.sin(lat.get(i)) * Math.sin(lat.get(i))));

                        X.set(i,(N + h.get(i)) * (cos(lat.get(i))) * (Math.cos(lng.get(i))));
                        Y.set(i,(N + h.get(i)) * (cos(lat.get(i))) * (Math.sin(lng.get(i))));
                        Z.set(i,(N * (1 - e2) + h.get(i))* Math.sin(lat.get(i)));
                    }


                }

                if(bSwitch.isChecked()) {

                    int a =6378137;
                    double b= 6356752.314245;
                    double e2=0.006694379990197;



                    for(int i=0;i<n;i++) {
                        lat.set(i,lat.get(i)* (Double)(Math.PI / 180));
                        lng.set(i,lng.get(i)* (Double)(Math.PI / 180));
                        double N = a / Math.sqrt(1 - (e2) * (Math.sin(lat.get(i)) * Math.sin(lat.get(i))));

                        X.set(i,(N + h.get(i)) * (cos(lat.get(i))) * (Math.cos(lng.get(i))));
                        Y.set(i,(N + h.get(i)) * (cos(lat.get(i))) * (Math.sin(lng.get(i))));
                        Z.set(i,(N * (1 - e2) + h.get(i))* Math.sin(lat.get(i)));
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
                String h1=String.format("%6s%14s%14s%14s","PN","X(m)","Y(m)","Z(m)");
                String h2=String.format("%6s%14s%14s%14s","PN","lat(deg)","long(degree)","h(m)");

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
                    StringX.set(i,String.format("%14.4f",X.get(i)));
                    StringY.set(i,String.format("%14.4f", Y.get(i)));
                    StringZ.set(i,String.format("%14.4f",Z.get(i)));
                }

                for(int i=0;i<n;i++) {
                    StringPN.set(i,String.format("%6s",NN.get(i)));
                    Stringlat.set(i,String.format("%14.6f",lat.get(i)*(180/PI)));
                    Stringlong.set(i,String.format("%14.6f", lng.get(i)*(180/PI)));
                    Stringh.set(i,String.format("%14.4f",h.get(i)));
                }

                StringBuffer datah1 = new StringBuffer();
                StringBuffer datah2 = new StringBuffer();

                for(int i=0;i<n;i++){
                    datah1.append(StringPN.get(i)+StringX.get(i)+StringY.get(i)+StringZ.get(i)+"\n");
                }
                for(int i=0;i<n;i++){
                    datah2.append(StringPN.get(i)+Stringlat.get(i)+Stringlong.get(i)+Stringh.get(i)+"\n");
                }

                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "GeoComp");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "geographic_to_rectengular.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    String strh1 = datah1.toString();
                    String strh2 = datah2.toString();
                    writer.write(header+strh1+bufferh2+strh2);
                    writer.flush();
                    writer.close();
                    String absolute = gpxfile.getAbsolutePath();
                    Toast.makeText(
                            ct5.this,
                            "Saved :"+absolute,
                            Toast.LENGTH_SHORT
                    ).show();
                } catch (IOException e) {
                    Toast.makeText(
                            ct5.this,
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
                .withActivity(ct5.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                Toast.makeText(ct5.this, "The file is : " + path, Toast.LENGTH_SHORT).show();
                String filename=path.substring(path.lastIndexOf("/")+1);
                if(pressed==1){
                    edit1.setText(filename);
                    conjugatepath=path;
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
                            ct5.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            ct5.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }
}
