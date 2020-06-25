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

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class ct8 extends AppCompatActivity {

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
    ArrayList<Double> E = new ArrayList<Double>();
    ArrayList<Double> N  = new ArrayList<Double>();
    ArrayList<Double> DOM = new ArrayList<Double>();

    Button calculate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct8);

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
                if(hasPermissions(ct8.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(ct8.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
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
                        E.add((conjugate.nextDouble()));
                        N.add((conjugate.nextDouble()));
                        DOM.add((conjugate.nextDouble()));
                    }



                } catch (FileNotFoundException e) {
                    Toast.makeText(
                            ct8.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

                int n = NN.size();

                for (int i=0;i<n;i++){
                    DOM.set(i,DOM.get(i)*(PI/180));
                }

                ArrayList<Double> F = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));
                ArrayList<Double> L = new ArrayList<Double>(Collections.<Double>nCopies((n), (double) 0));

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

                            double nu=N.get(i)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                            double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                            double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                            double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                            double T=pow(Math.tan(F1),2);
                            double D=(E.get(i)-500000)/(RN);
                            double C=e2u*pow(Math.cos(F1),2);
                            double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                            F.set(i,F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889)));
                            L.set(i,DOM.get(i)+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1));

                        }
                    }
                    if (bSwitch.isChecked()) {
                        /*WGS84 parametres*/
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        for (int i=0;i<n;i++) {
                            double nu=N.get(i)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                            double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                            double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                            double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                            double T=pow(Math.tan(F1),2);
                            double D=(E.get(i)-500000)/(RN);
                            double C=e2u*pow(Math.cos(F1),2);
                            double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                            F.set(i,F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889)));
                            L.set(i,DOM.get(i)+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1));
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

                            double nu=(N.get(i)/0.9996)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                            double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                            double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                            double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                            double T=pow(Math.tan(F1),2);
                            double D=(E.get(i)-500000)/(RN*0.9996);
                            double C=e2u*pow(Math.cos(F1),2);
                            double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                            F.set(i,F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889)));
                            L.set(i,DOM.get(i)+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1));
                        }
                    }
                    if (bSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        for (int i=0;i<n;i++) {

                            double nu=(N.get(i)/0.9996)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                            double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                            double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                            double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                            double T=pow(Math.tan(F1),2);
                            double D=(E.get(i)-500000)/(RN*0.9996);
                            double C=e2u*pow(Math.cos(F1),2);
                            double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                            F.set(i,F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889)));
                            L.set(i,DOM.get(i)+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1));
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
                String h1=String.format("%6s%14s%14s","PN","lat(degree)","long(degree)");
                String h2=String.format("%6s%14s%14s%14s","PN","Easting(m)","Norting(m)","DOM(degree)");

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
                    StringZ.set(i,String.format("%10.0f", DOM.get(i)*(180/PI)));
                }

                for(int i=0;i<n;i++) {
                    StringPN.set(i,String.format("%6s",NN.get(i)));
                    Stringlat.set(i,String.format("%14.10f",F.get(i)*(Double)(180/PI)));
                    Stringlong.set(i,String.format("%14.10f", L.get(i)*(Double)(180/PI)));
                }

                StringBuffer datah1 = new StringBuffer();
                StringBuffer datah2 = new StringBuffer();

                for(int i=0;i<n;i++){
                    datah1.append(StringPN.get(i)+StringX.get(i)+StringY.get(i)+StringZ.get(i)+"\n");
                }
                for(int i=0;i<n;i++){
                    datah2.append(StringPN.get(i)+Stringlat.get(i)+Stringlong.get(i)+"\n");
                }

                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "GeoComp");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "utm_to_geographic.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    String strh1 = datah1.toString();
                    String strh2 = datah2.toString();
                    writer.write(header+strh2+bufferh2+strh1);
                    writer.flush();
                    writer.close();
                    String absolute = gpxfile.getAbsolutePath();
                    Toast.makeText(
                            ct8.this,
                            "Saved :"+absolute,
                            Toast.LENGTH_SHORT
                    ).show();
                } catch (IOException e) {
                    Toast.makeText(
                            ct8.this,
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
                .withActivity(ct8.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                Toast.makeText(ct8.this, "The file is : " + path, Toast.LENGTH_SHORT).show();
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
                            ct8.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            ct8.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }
}
