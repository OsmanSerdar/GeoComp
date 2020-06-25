package com.osser.geocomp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class dt4 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ed50_wgs84_3d);
        Button calculate =findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                EditText editText1=findViewById(R.id.editText1);
                EditText editText2=findViewById(R.id.editText2);
                EditText editText3=findViewById(R.id.editText3);
                TextView textView1=findViewById(R.id.textView4);
                TextView textView2=findViewById(R.id.textView5);
                TextView textView3=findViewById(R.id.textView6);

                String edit1= editText1.getText().toString();
                String edit2= editText1.getText().toString();
                String edit3= editText1.getText().toString();

                if(TextUtils.isEmpty(edit1)) {
                    editText1.setError("Please enter a value");
                    return;
                }
                if(TextUtils.isEmpty(edit2)){
                    editText2.setError("Please enter a value");
                    return;
                }
                if(TextUtils.isEmpty(edit3)) {
                    editText3.setError("Please enter a value");
                    return;
                }

                double X=Double.parseDouble(editText1.getText().toString());
                double Y=Double.parseDouble(editText2.getText().toString());
                double Z=Double.parseDouble(editText3.getText().toString());

                double k= 0.0000010347;

                double [][] matrixtx = new double[3][1];
                matrixtx[0][0]=-84.003;
                matrixtx[1][0]=-102.315;
                matrixtx[2][0]=-129.879;

                double [][] matrixE = new double [3][3];
                matrixE[0][0]=1;
                matrixE[1][0]=0.0001316111111*(Math.PI/180);
                matrixE[2][0]=0.00000008333333333*(Math.PI/180);
                matrixE[0][1]=-0.0001316111111*(Math.PI/180);
                matrixE[1][1]=1;
                matrixE[2][1]=0.00000508333333*(Math.PI/180);
                matrixE[0][2]=-0.00000008333333333*(Math.PI/180);
                matrixE[1][2]=-0.00000508333333*(Math.PI/180);
                matrixE[2][2]=1;

                double [][] matrixWGS84X= new double[3][1];
                matrixWGS84X[0][0]=X;
                matrixWGS84X[1][0]=Y;
                matrixWGS84X[2][0]=Z;

                double [][] matrixED50X= new double[3][1];


                matrixED50X[0][0]=matrixtx[0][0]+k*X+matrixE[0][0]*X+matrixE[0][1]*Y+matrixE[0][2]*Z;
                matrixED50X[1][0]=matrixtx[1][0]+k*Y+matrixE[1][0]*X+matrixE[1][1]*Y+matrixE[1][2]*Z;
                matrixED50X[2][0]=matrixtx[2][0]+k*Z+matrixE[2][0]*X+matrixE[2][1]*Y+matrixE[2][2]*Z;

                String ed50X = String.format ("%.4f",matrixED50X[0][0]);
                String ed50Y = String.format ("%.4f",matrixED50X[1][0] );
                String ed50Z = String.format ("%.4f",matrixED50X[2][0]);

                textView1.setText(ed50X);
                textView2.setText(ed50Y);
                textView3.setText(ed50Z);



            }
        });

        Button button1=findViewById(R.id.clear);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText1=findViewById(R.id.editText1);
                EditText editText2=findViewById(R.id.editText2);
                EditText editText3=findViewById(R.id.editText3);
                TextView textView1=findViewById(R.id.textView4);
                TextView textView2=findViewById(R.id.textView5);
                TextView textView3=findViewById(R.id.textView6);


                editText1.setText(" ");
                editText2.setText(" ");
                editText3.setText(" ");
                textView1.setText(" ");
                textView2.setText(" ");
                textView3.setText(" ");
            }
        });

    }
}
