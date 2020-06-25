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

import static android.widget.Toast.*;
import static java.lang.Math.cos;

public class ct1 extends AppCompatActivity {

    private RadioButton aSwitch;
    private RadioButton bSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct1);

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
                double F=Double.parseDouble(editText1.getText().toString());
                double L=Double.parseDouble(editText2.getText().toString());
                double h=Double.parseDouble(editText3.getText().toString());

                if(aSwitch.isChecked()) {
                    int a =6378137;
                    double b=6356752.314140;
                    double e2=0.00669438002290;
                    F= F*(Math.PI/180);
                    L= L*(Math.PI/180);
                    double N= a/Math.sqrt(1-(e2)*(Math.sin(F)*Math.sin(F)));

                    double X =(N+h)*(cos(F))*(Math.cos(L));
                    double Y=(N+h)*(cos(F))*(Math.sin(L));
                    double Z=(N*(1-e2)+h)*Math.sin(F);

                    String fi1String = String.format ("%.4f",X);
                    String LamdaString = String.format ("%.4f",Y );
                    String hString = String.format ("%.4f",Z);

                    textView1.setText(fi1String);
                    textView2.setText(LamdaString);
                    textView3.setText(hString);

                }
                if(bSwitch.isChecked()) {
                    int a =6378137;
                    double b= 6356752.314245;
                    double e2=0.006694379990197;
                    F= F*(Math.PI/180);
                    L= L*(Math.PI/180);
                    double N= a/Math.sqrt(1-(e2)*(Math.sin(F)*Math.sin(F)));

                    double X =(N+h)*(cos(F))*(Math.cos(L));
                    double Y=(N+h)*(cos(F))*(Math.sin(L));
                    double Z=(N*(1-e2)+h)*Math.sin(F);

                    String fi1String = String.format ("%.4f",X);
                    String LamdaString = String.format ("%.4f",Y );
                    String hString = String.format ("%.4f",Z);

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
