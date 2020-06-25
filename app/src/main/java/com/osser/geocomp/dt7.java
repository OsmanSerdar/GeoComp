package com.osser.geocomp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

public class dt7 extends AppCompatActivity {

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

    ArrayList<String> NN = new ArrayList<String>();
    ArrayList<Double> XR = new ArrayList<Double>();
    ArrayList<Double> YR = new ArrayList<Double>();
    ArrayList<Double> ZR = new ArrayList<Double>();
    ArrayList<Double> X = new ArrayList<Double>();
    ArrayList<Double> Y = new ArrayList<Double>();
    ArrayList<Double> Z = new ArrayList<Double>();
    ArrayList<String> TNN = new ArrayList<String>();
    ArrayList<Double> XT = new ArrayList<Double>();
    ArrayList<Double> YT = new ArrayList<Double>();
    ArrayList<Double> ZT = new ArrayList<Double>();

    Button calculate;

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button infocj;
    private PopupWindow mPopupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bursa_wolf);

        folder1=findViewById(R.id.folder1);
        edit1=findViewById(R.id.editText1);


        folder1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if(hasPermissions(dt7.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(dt7.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
                }
                pressed=1;
            }
        });

        folder2=findViewById(R.id.folder2);
        edit2=findViewById(R.id.editText2);

        folder2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                if(hasPermissions(dt7.this, PERMISSIONS)){
                    ShowFilepicker();
                }else{
                    ActivityCompat.requestPermissions(dt7.this, PERMISSIONS, FILEPICKER_PERMISSIONS);
                }
                pressed=2;
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

                String editt= edit2.getText().toString();

                if(TextUtils.isEmpty(editt)) {
                    edit2.setError("Please choose a file");
                    return;
                }

                try {
                    Scanner conjugate = new Scanner(new File(conjugatepath));

                    while (conjugate.hasNext()) {

                        NN.add(conjugate.next());
                        XR.add((conjugate.nextDouble()));
                        YR.add((conjugate.nextDouble()));
                        ZR.add((conjugate.nextDouble()));
                        X.add((conjugate.nextDouble()));
                        Y.add((conjugate.nextDouble()));
                        Z.add((conjugate.nextDouble()));

                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(
                            dt7.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

                try {
                    Scanner targetpoints = new Scanner(new File(calculatedpath));

                    while (targetpoints.hasNext()) {
                        TNN.add(targetpoints.next());
                        XT.add(targetpoints.nextDouble());
                        YT.add((targetpoints.nextDouble()));
                        ZT.add((targetpoints.nextDouble()));
                    }
                } catch (FileNotFoundException e) {
                    Toast.makeText(
                            dt7.this,
                            "An error occurred",
                            Toast.LENGTH_SHORT
                    ).show();
                    e.printStackTrace();
                }

                int n =(NN.size()*3);
                int u=7;
                int f=n-u;

                if(f>=1){
                    Toast.makeText(
                            dt7.this,
                            "f="+f+">1 Can be Adjusted :)",
                            Toast.LENGTH_SHORT
                    ).show();

                }

                if(f<1){
                    Toast.makeText(
                            dt7.this,
                            "f="+f+"<1 Can not be Adjusted :(",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                ArrayList<Double> L = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A1 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A2 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A3 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A4 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A5 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A6 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));
                ArrayList<Double> A7 = new ArrayList<Double>(Collections.<Double>nCopies((n*3), (double) 0));

                double number=0;
                int decimal=0;
                double frac = 0;

                for(int i=0;i<=n-1;i++) {
                    number = (double) i/3 ;
                    decimal = (int) number;
                    frac = (double) (number - decimal);

                    if (frac == 0) {
                        L.set(i, XR.get((int) ((i) / 3)) - X.get((int) ((i) / 3)));
                        A1.set(i, (double) 1);
                        A5.set(i, -Z.get((int) ((i) / 3)));
                        A6.set(i, Y.get((int) ((i) / 3)));
                        A7.set(i, X.get((int) ((i) / 3)));
                    }
                    else if (frac > 0.3 && frac < 0.6) {

                        L.set(i, YR.get((int) ((i) / 3)) - Y.get((int) ((i) / 3)));
                        A2.set(i, (double) 1);
                        A4.set(i, Z.get((int) ((i) / 3)));
                        A6.set(i, -X.get((int) ((i) / 3)));
                        A7.set(i, Y.get((int) ((i) / 3)));
                    }
                    else if (frac>0.6){
                        L.set(i,ZR.get((int)((i)/3))-Z.get((int)((i)/3)));
                        A3.set(i, (double) 1);
                        A4.set(i,-Y.get((int)((i)/3)));
                        A5.set(i,X.get((int)((i)/3)));
                        A7.set(i,Z.get((int)((i)/3)));
                    }
                }

                int rowL = n;
                int colL = 1;

                double [][] matrixL = new double[rowL][colL];

                for (int i = 0; i <= (n-1); i++) {
                    int j = 0;
                    matrixL[i][j] = L.get(i);
                }

                int rowA = n;
                int colA = 7;

                double [][] matrixA = new double[rowA][colA];

                for (int i = 0; i <= (n-1); i++) {
                    int j = 0;
                    matrixA[i][j] = A1.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 1;
                    matrixA[i][j] = A2.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 2;
                    matrixA[i][j] = A3.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 3;
                    matrixA[i][j] = A4.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 4;
                    matrixA[i][j] = A5.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 5;
                    matrixA[i][j] = A6.get(i);
                }
                for (int i = 0; i <= (n-1); i++) {
                    int j = 6;
                    matrixA[i][j] = A7.get(i);
                }
                double [][] matrixAT = new double[colA][rowA];
                for (int i = 0; i < rowA; i++)
                {
                    for (int j = 0; j < colA; j++)
                    {
                        matrixAT[j][i] = matrixA[i][j];
                    }
                }
                double [][] a = new double[7][7];
                for (int i = 0; i <7; i++)
                {
                    for (int j = 0; j <7; j++)
                    {
                        for( int k=0; k<=n-1;k++){

                            a[i][j] +=  matrixAT[i][k] * matrixA[k][j];
                        }
                    }
                }
                double [][] matrixn = new double[7][1];
                for (int i = 0; i <7; i++)
                {
                    for (int j = 0; j <1; j++)
                    {
                        for( int k=0; k<=n-1;k++){

                            matrixn[i][j] +=  matrixAT[i][k] * matrixL[k][j];
                        }
                    }
                }
                double [][] invN = invert(a);
                /*matrixinvN = adj(matrixN)/det(matrixN);*/

                double [][] matrixx = new double[7][1];

                for (int i = 0; i < 7; i++)
                {
                    for (int j = 0; j < 1; j++)
                    {
                        for (int k = 0; k < 7; k++)
                        {
                            matrixx[i][j] +=  invN[i][k] * matrixn[k][j];
                        }
                    }
                }

                double [][] matrixE = new double[3][3];
                matrixE[0][0]=1*(1+matrixx[6][0]);
                matrixE[1][1]=1*(1+matrixx[6][0]);
                matrixE[2][2]=1*(1+matrixx[6][0]);
                matrixE[0][1]=matrixx[5][0]*(1+matrixx[6][0]);
                matrixE[0][2]=-matrixx[4][0]*(1+matrixx[6][0]);
                matrixE[1][0]=-matrixx[5][0]*(1+matrixx[6][0]);
                matrixE[1][2]=matrixx[3][0]*(1+matrixx[6][0]);
                matrixE[2][0]=matrixx[4][0]*(1+matrixx[6][0]);
                matrixE[2][1]=-matrixx[3][0]*(1+matrixx[6][0]);

                double [][] matrixt = new double[3][1];
                matrixt[0][0]=matrixx[0][0];
                matrixt[1][0]=matrixx[1][0];
                matrixt[2][0]=matrixx[2][0];

                double [][] matrixTP = new double [3][1];
                ArrayList<Double> TPX = new ArrayList<Double>(Collections.<Double>nCopies((XT.size()), (double) 0));
                ArrayList<Double> TPY = new ArrayList<Double>(Collections.<Double>nCopies((XT.size()), (double) 0));
                ArrayList<Double> TPZ = new ArrayList<Double>(Collections.<Double>nCopies((XT.size()), (double) 0));

                for(int i=0;i<XT.size();i++){
                    matrixTP[0][0]=matrixx[0][0]+matrixE[0][0]*XT.get(i)+matrixE[0][1]*YT.get(i)+matrixE[0][2]*ZT.get(i);
                    matrixTP[1][0]=matrixx[1][0]+matrixE[1][0]*XT.get(i)+matrixE[1][1]*YT.get(i)+matrixE[1][2]*ZT.get(i);
                    matrixTP[2][0]=matrixx[2][0]+matrixE[2][0]*XT.get(i)+matrixE[2][1]*YT.get(i)+matrixE[2][2]*ZT.get(i);
                    TPX.set(i,matrixTP[0][0]);
                    TPY.set(i,matrixTP[1][0]);
                    TPZ.set(i,matrixTP[2][0]);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar now = Calendar.getInstance();
                String UTC= formatter.format(now.getTime());
                String Createdby =String.format("Created By             :%s","GeoComp");
                String Date =String.format("Calculation Time       :%s",UTC);
                String nparemater=String.format("Number of Observations :%d",n);
                String nunknown=String.format("Number of Unknown      :%d",7);
                String nfreedom=String.format("Degrees of Freedom     :%d",f);
                String tx =String.format  ("tx                     :%8.4f",matrixx[0][0]);
                String ty =String.format  ("ty                     :%8.4f",matrixx[1][0]);
                String tz =String.format  ("tz                     :%8.4f",matrixx[2][0]);
                String Ex =String.format  ("Ex                     :%13.10f",matrixx[3][0]);
                String Ey =String.format  ("Ey                     :%13.10f",matrixx[4][0]);
                String Ez =String.format  ("Ez                     :%13.10f",matrixx[5][0]);
                String k =String.format   ("k                      :%13.10f",matrixx[6][0]);
                String brackets ="..................................................................................";
                String h1=String.format("%6s%14s%14s%14s","PN","X(m)","Y(m)","Z(m)");
                String h2=String.format("%6s%14s%14s%14s","PN","X(m)","Y(m)","Z(m)");
                String h3=String.format("%6s%14s%14s%14s%14s%14s%14s","PN","XR(m)","YR(m)","ZR(m)","X(m)","Y(m)","Z(m)");

                StringBuffer header = new StringBuffer();
                header.append(Createdby+"\n"+Date+"\n"+nparemater+"\n"+nunknown+"\n"+nfreedom+"\n"+tx+"\n"+ty+"\n"+tz+"\n"+Ex+"\n"+Ey+"\n"+Ez+"\n"+k+"\n"+"\n"+"ADJUSTED TARGET POINTS"+"\n"+brackets+"\n"+h1+"\n"+brackets+"\n");
                StringBuffer bufferh2 = new StringBuffer();
                bufferh2.append("\n"+"TARGET POINTS"+"\n"+brackets+"\n"+h2+"\n"+brackets+"\n");
                StringBuffer bufferh3 = new StringBuffer();
                bufferh3.append("\n"+"CONJUGATED POINTS"+"\n"+brackets+"\n"+h3+"\n"+brackets+"\n");
                StringBuffer bufferh4 = new StringBuffer();
                bufferh4.append("\n"+"VECTOR L                            MATRIX A"+"\n"+brackets+"\n");

                ArrayList<String> StringL = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%6s"," ")));
                ArrayList<String> StringA1 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> StringA2 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%10s"," ")));
                ArrayList<String> StringA3 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%9s"," ")));
                ArrayList<String> StringA4 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%9s"," ")));
                ArrayList<String> StringA5 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%9s"," ")));
                ArrayList<String> StringA6 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%14s"," ")));
                ArrayList<String> StringA7 = new ArrayList<String>(Collections.<String>nCopies((n),String.format("%15s"," ")));
                ArrayList<String> StringTNN = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringTPX = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringTPY = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringTPZ = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringFTNN = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringFTPX = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringFTPY = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringFTPZ = new ArrayList<String>(Collections.<String>nCopies((XT.size()),String.format("%15s"," ")));
                ArrayList<String> StringCJPN = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringXR = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringYR = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringZR = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringX = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringY = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));
                ArrayList<String> StringZ = new ArrayList<String>(Collections.<String>nCopies((XR.size()),String.format("%15s"," ")));

                for(int i=0;i<=n-1;i++) {
                    StringL.set(i,String.format("%8.4f",L.get(i)));
                    StringA1.set(i,String.format("%3.0f",A1.get(i)));
                    StringA2.set(i,String.format("%3.0f",A2.get(i)));
                    StringA3.set(i,String.format("%3.0f",A3.get(i)));
                    StringA4.set(i,String.format("%14.4f",A4.get(i)));
                    StringA5.set(i,String.format("%14.4f",A5.get(i)));
                    StringA6.set(i,String.format("%14.4f",A6.get(i)));
                    StringA7.set(i,String.format("%14.4f",A7.get(i)));
                }

                for(int i=0;i<XT.size();i++){
                    StringTNN.set(i,String.format("%6s",TNN.get(i)));
                    StringTPX.set(i,String.format("%14.5f",TPX.get(i)));
                    StringTPY.set(i,String.format("%14.5f",TPY.get(i)));
                    StringTPZ.set(i,String.format("%14.5f",TPZ.get(i)));
                }

                for(int i=0;i<XT.size();i++){
                    StringFTNN.set(i,String.format("%6s",TNN.get(i)));
                    StringFTPX.set(i,String.format("%14.5f",XT.get(i)));
                    StringFTPY.set(i,String.format("%14.5f",YT.get(i)));
                    StringFTPZ.set(i,String.format("%14.5f",ZT.get(i)));
                }

                for(int i=0;i<XR.size();i++){
                    StringCJPN.set(i,String.format("%6s",NN.get(i)));
                    StringXR.set(i,String.format("%14.3f",XR.get(i)));
                    StringYR.set(i,String.format("%14.3f",YR.get(i)));
                    StringZR.set(i,String.format("%14.3f",ZR.get(i)));
                    StringX.set(i,String.format("%14.3f",X.get(i)));
                    StringY.set(i,String.format("%14.3f",Y.get(i)));
                    StringZ.set(i,String.format("%14.3f",Z.get(i)));
                }

                StringBuffer datah1 = new StringBuffer();
                StringBuffer datah2 = new StringBuffer();
                StringBuffer datah3 = new StringBuffer();

                for(int i=0;i<XT.size();i++){
                    datah1.append(StringTNN.get(i)+StringTPX.get(i)+StringTPY.get(i)+StringTPZ.get(i)+"\n");
                }
                for(int i=0;i<XT.size();i++){
                    datah2.append(StringFTNN.get(i)+StringFTPX.get(i)+StringFTPY.get(i)+StringFTPZ.get(i)+"\n");
                }
                for(int i=0;i<XR.size();i++){
                    datah3.append(StringCJPN.get(i)+StringXR.get(i)+StringYR.get(i)+StringZR.get(i)+StringX.get(i)+StringY.get(i)+StringZ.get(i)+"\n");
                }
                StringBuffer data = new StringBuffer();
                for (int i=0;i<=n-1;i++) {
                    data.append(StringL.get(i)+StringA1.get(i)+StringA2.get(i)+StringA3.get(i)+StringA4.get(i)+StringA5.get(i)+StringA6.get(i)+StringA7.get(i)+"\n");
                }

                try {
                    File root = new File(Environment.getExternalStorageDirectory(), "GeoComp");
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File gpxfile = new File(root, "busa-wolf_result.txt");
                    FileWriter writer = new FileWriter(gpxfile);
                    String str = data.toString();
                    String strh1 = datah1.toString();
                    String strh2 = datah2.toString();
                    String strh3 = datah3.toString();
                    writer.write(header+strh1+bufferh2+strh2+bufferh3+strh3+bufferh4+str);
                    writer.flush();
                    writer.close();
                    String absolute = gpxfile.getAbsolutePath();
                    Toast.makeText(
                            dt7.this,
                            "Saved :"+absolute,
                            Toast.LENGTH_SHORT
                    ).show();
                } catch (IOException e) {
                    Toast.makeText(
                            dt7.this,
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
                .withActivity(dt7.this)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .allowCustomPath(true)
                .setType(StorageChooser.FILE_PICKER)
                .build();

        // 2. Retrieve the selected path by the user and show in a toast !
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                Toast.makeText(dt7.this, "The file is : " + path, Toast.LENGTH_SHORT).show();
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
                            dt7.this,
                            "Permission granted! Please click on pick a file once again.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            dt7.this,
                            "Permission denied to read your External storage :(",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                return;
            }
        }
    }


    public static double[][] invert(double a[][])
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.

    public static void gaussian(double a[][], int index[])
    {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i=0; i<n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i)
        {
            double c1 = 0;
            for (int j=0; j<n; ++j)
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i)
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1)
                {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {
                double pj = a[index[i]][j]/a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

}
