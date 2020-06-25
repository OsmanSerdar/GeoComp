package com.osser.geocomp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ct2 extends AppCompatActivity {
    RadioButton aSwitch;
    RadioButton bSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct2);
        aSwitch= (RadioButton) findViewById(R.id.switch1);
        bSwitch=(RadioButton) findViewById(R.id.switch2);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1=findViewById(R.id.text1);
                EditText editText2=findViewById(R.id.text2);
                EditText editText3=findViewById(R.id.text3);
                TextView textView1=findViewById(R.id.text4);
                TextView textView2=findViewById(R.id.text5);
                TextView textView3=findViewById(R.id.text6);

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

                if(aSwitch.isChecked()) { /*grs80 */
                    int a =6378137;
                    double b=6356752.314140;
                    double e2=0.00669438002290;
                    double Lamda=(Math.atan2(Y,X))*180/Math.PI;
                    double h=0;
                    double D=Math.sqrt(X*X+Y*Y);
                    double fi0=Math.atan2(Z,(D*(1-e2)));
                    double N0=0;
                    for (int i=1 ; i<10 ; i++){
                        N0= a/Math.sqrt(1-e2*(Math.sin(fi0)*(Math.sin(fi0))));
                        h=(D/Math.cos(fi0))-N0;
                        fi0=Math.atan2(Z,(D*(1-e2*(N0/(N0+h)))));
                    }
                    fi0=fi0*180/Math.PI;

                    String fi1String = String.format ("%.10f", fi0);
                    String LamdaString = String.format ("%.10f", Lamda );
                    String hString = String.format ("%.4f", h );

                    textView1.setText(fi1String);
                    textView2.setText(LamdaString);
                    textView3.setText(hString);

                }
                if(bSwitch.isChecked()) { /*wgs84 */
                    int a =6378137;
                    double b= 6356752.314245;
                    double e2=0.006694379990197;
                    double Lamda=(Math.atan2(Y,X))*180/Math.PI;
                    double h=0;
                    double D=Math.sqrt(X*X+Y*Y);
                    double fi0=Math.atan2(Z,(D*(1-e2)));
                    double N0=0;
                    for (int i=1 ; i<10 ; i++){
                        N0= a/Math.sqrt(1-e2*(Math.sin(fi0)*(Math.sin(fi0))));
                        h=(D/Math.cos(fi0))-N0;
                        fi0=Math.atan2(Z,(D*(1-e2*(N0/(N0+h)))));
                    }

                    fi0=fi0*180/Math.PI;
                    String fi1String = String.format ("%.10f", fi0);
                    String LamdaString = String.format ("%.10f", Lamda );
                    String hString = String.format ("%.4f", h );

                    textView1.setText(fi1String);
                    textView2.setText(LamdaString);
                    textView3.setText(hString);

                }

            }
        });
        Button button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1=findViewById(R.id.text1);
                EditText editText2=findViewById(R.id.text2);
                EditText editText3=findViewById(R.id.text3);
                TextView textView1=findViewById(R.id.text4);
                TextView textView2=findViewById(R.id.text5);
                TextView textView3=findViewById(R.id.text6);

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
