package com.osser.geocomp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class ct4 extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button infodom;
    private PopupWindow mPopupWindow;

    private RadioButton aSwitch;
    private RadioButton bSwitch;
    private RadioButton cSwitch;
    private RadioButton dSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct4);

        aSwitch= (RadioButton) findViewById(R.id.switch1);
        bSwitch=(RadioButton) findViewById(R.id.switch2);
        cSwitch= (RadioButton) findViewById(R.id.switch3);
        dSwitch=(RadioButton) findViewById(R.id.switch4);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1=findViewById(R.id.text1);
                EditText editText2=findViewById(R.id.text2);
                EditText editText3=findViewById(R.id.text3);
                TextView textView1=findViewById(R.id.text4);
                TextView textView2=findViewById(R.id.text5);

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


                double E=Double.parseDouble(editText1.getText().toString());
                double N=Double.parseDouble(editText2.getText().toString());
                double L0=Double.parseDouble(editText3.getText().toString());
                L0 = (L0*PI)/180;

                if (cSwitch.isChecked()) {
                    /*Calcuation for 3 degree*/
                    if (aSwitch.isChecked()) {
                        /*GRS80 parametres*/
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.00669438002290;
                        double e2u=(e2/(1-e2));

                        double nu=N/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                        double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                        double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                        double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                        double T=pow(Math.tan(F1),2);
                        double D=(E-500000)/(RN);
                        double C=e2u*pow(Math.cos(F1),2);
                        double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                        double F=F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889));
                        double L=L0+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1);


                        String northString = String.format ("%.10f",(F*180)/PI);
                        String eastString = String.format ("%.10f",(L*180)/PI);

                        textView1.setText(northString);
                        textView2.setText(eastString);

                    }
                    if (bSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));
                        double nu=N/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                        double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                        double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                        double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                        double T=pow(Math.tan(F1),2);
                        double D=(E-500000)/(RN);
                        double C=e2u*pow(Math.cos(F1),2);
                        double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                        double F=F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889));
                        double L=L0+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1);


                        String northString = String.format ("%.10f",(F*180)/PI);
                        String eastString = String.format ("%.10f",(L*180)/PI);

                        textView1.setText(northString);
                        textView2.setText(eastString);

                    }
                }

                if (dSwitch.isChecked()) {
                    if (aSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.00669438002290;
                        double e2u=(e2/(1-e2));
                        double nu=(N/0.9996)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                        double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                        double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                        double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                        double T=pow(Math.tan(F1),2);
                        double D=(E-500000)/(RN*0.9996);
                        double C=e2u*pow(Math.cos(F1),2);
                        double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                        double F=F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889));
                        double L=L0+(D-(1+2*T+C)*(pow(D,3)*0.01666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1);


                        String northString = String.format ("%.10f",(F*180)/PI);
                        String eastString = String.format ("%.10f",(L*180)/PI);

                        textView1.setText(northString);
                        textView2.setText(eastString);


                    }
                    if (bSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));
                        double nu=(N/0.9996)/(a*(1-e2*0.25-(0.04687)*e2*e2-(0.01953125)*e2*e2*e2));
                        double e1= (1-sqrt(1-e2))/(1+sqrt(1-e2));
                        double F1=nu+((1.5)*e1-(0.84375)*pow(e1,3))*sin(2*nu)+((1.3125)*e1*e1-(1.71875)*pow(e1,4))*sin(4*nu)+((1.572916666667)*pow(e1,3))*sin(6*nu)+((1097/512)*pow(e1,4))*sin(8*nu);
                        double RN=a/Math.sqrt((1-e2*Math.sin(F1)*Math.sin(F1)));
                        double T=pow(Math.tan(F1),2);
                        double D=(E-500000)/(RN*0.9996);
                        double C=e2u*pow(Math.cos(F1),2);
                        double R1=a*((1-e2)/pow(1-e2*pow(sin(F1),2),1.5));

                        double F=F1-(RN*(tan(F1)/R1))*(D*D*0.5-(5+3*T+10*C-4*C*C-9*e2u)*(Math.pow(D,4)*0.041666666667)+(61+90*T+298*C+45*T*T-252*e2u-3*C*C)*(Math.pow(D,6)*0.001388888889));
                        double L=L0+(D-(1+2*T+C)*(pow(D,3)*0.1666666667)+(5-2*C+28*T-3*C*C+8*e2u+24*T*T)*(pow(D,5)*0.008333333333))/cos(F1);


                        String northString = String.format ("%.10f",(F*180)/PI);
                        String eastString = String.format ("%.10f",(L*180)/PI);

                        textView1.setText(northString);
                        textView2.setText(eastString);
                    }
                }

            }
        });

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = ct4.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rlct4);
        infodom = (Button) findViewById(R.id.infodom);

        // Set a click listener for the text view
        infodom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.dom,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
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


                editText1.setText(" ");
                editText2.setText(" ");
                editText3.setText(" ");
                textView1.setText(" ");
                textView2.setText(" ");

            }
        });
    }
}
