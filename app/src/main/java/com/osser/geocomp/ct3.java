package com.osser.geocomp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class ct3 extends AppCompatActivity {

    private RadioButton aSwitch;
    private RadioButton bSwitch;
    private RadioButton cSwitch;
    private RadioButton dSwitch;

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button infodom;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ct3);

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


                double F=Double.parseDouble(editText1.getText().toString());
                double L=Double.parseDouble(editText2.getText().toString());
                double L0=Double.parseDouble(editText3.getText().toString());
                F = F * (Math.PI / 180);
                L = L * (Math.PI / 180);
                L0= L0*(Math.PI / 180);

                if (cSwitch.isChecked()) {
                    /*Calcuation for 3 degree*/
                    if (aSwitch.isChecked()) {
                        /*GRS80 parametres*/
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.0066943800229;
                        double e2u=(e2/(1-e2));

                        double A0=1-(e2*0.25)-((0.046875)*pow(e2,2))-((0.01953125)*pow(e2,3));
                        double A2=(((0.375)*(e2))+((0.09375)*(pow(e2,2)))+((0.0439453125)*(pow(e2,3))));
                        double A4=(0.05859375)*(pow(e2,2)+((0.75)*pow(e2,3)));
                        double A6=(0.011393229167)*pow(e2,3);
                        double m=a*(A0*F-A2*sin(2*F)+A4*sin(4*F)-A6*sin(6*F));
                        double T = tan(F);
                        double W =L-L0;
                        double p =a*((1-e2)/pow(1-e2*pow(sin(F),2),1.5));
                        double v = a/sqrt(1-e2*pow(sin(F),2));
                        double w =v/p;
                        double Term1E = (pow(W,2)*0.166666666667)*pow (cos(F),2)*(w-pow(T,2));
                        double Term2E =(pow(W,4)*0.008333333333)*pow (cos(F),4)*(4*pow(w,3)*(1-6*T*T)+pow(w,2)*(1+8*T*T)-w*2*T*T+pow(T,4));
                        double Term3E = (pow(W,6)*0.000198412698)*pow (cos(F),6)*(61-479*pow(T,2)+179*pow(T,4)-pow(T,6));
                        double Term1N = (pow(W,2)*0.5)*v*sin(F)*cos(F);
                        double Term2N =(pow(W,4)*0.041666666667)*v*sin(F)*pow(cos(F),3)*(4*w*w+w-T*T);
                        double Term3N = (pow(W,6)*0.001388888889)*v*sin(F)*pow(cos(F),5)*(8*pow(w,4)*(11-24*T*T)-28*pow(w,3)*(1-6*T*T)+w*w*(1-32*T*T)-w*2*T*T+pow(T,4));
                        double Term4N = (pow(W,8)*0.000024801587)*v*sin(F)*pow(cos(F),7)*(1385-3111*T*T+543*pow(T,4)-pow(T,6));
                        double yg=v*W*cos(F)*(1+Term1E+Term2E+Term3E);
                        double xg=m+Term1N+Term2N+Term3N+Term4N;
                        double E= yg+500000;
                        double N=xg;

                        String northString = String.format ("%.4f",N);
                        String eastString = String.format ("%.4f",E);

                        textView1.setText(eastString);
                        textView2.setText(northString);

                    }
                    if (bSwitch.isChecked()) {
                        /*WGS84 parametres*/
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        double A0=1-(e2*0.25)-((0.046875)*pow(e2,2))-((0.01953125)*pow(e2,3));
                        double A2=(((0.375)*(e2))+((0.09375)*(pow(e2,2)))+((0.0439453125)*(pow(e2,3))));
                        double A4=(0.05859375)*(pow(e2,2)+((0.75)*pow(e2,3)));
                        double A6=(0.011393229167)*pow(e2,3);
                        double m=a*(A0*F-A2*sin(2*F)+A4*sin(4*F)-A6*sin(6*F));
                        double T = tan(F);
                        double W =L-L0;
                        double p =a*((1-e2)/pow(1-e2*pow(sin(F),2),1.5));
                        double v = a/sqrt(1-e2*pow(sin(F),2));
                        double w =v/p;
                        double Term1E = (pow(W,2)*0.166666666667)*pow (cos(F),2)*(w-pow(T,2));
                        double Term2E =(pow(W,4)*0.008333333333)*pow (cos(F),4)*(4*pow(w,3)*(1-6*T*T)+pow(w,2)*(1+8*T*T)-w*2*T*T+pow(T,4));
                        double Term3E = (pow(W,6)*0.000198412698)*pow (cos(F),6)*(61-479*pow(T,2)+179*pow(T,4)-pow(T,6));
                        double Term1N = (pow(W,2)*0.5)*v*sin(F)*cos(F);
                        double Term2N =(pow(W,4)*0.041666666667)*v*sin(F)*pow(cos(F),3)*(4*w*w+w-T*T);
                        double Term3N = (pow(W,6)*0.001388888889)*v*sin(F)*pow(cos(F),5)*(8*pow(w,4)*(11-24*T*T)-28*pow(w,3)*(1-6*T*T)+w*w*(1-32*T*T)-w*2*T*T+pow(T,4));
                        double Term4N = (pow(W,8)*0.000024801587)*v*sin(F)*pow(cos(F),7)*(1385-3111*T*T+543*pow(T,4)-pow(T,6));
                        double yg=v*W*cos(F)*(1+Term1E+Term2E+Term3E);
                        double xg=m+Term1N+Term2N+Term3N+Term4N;
                        double E= yg+500000;
                        double N=xg;

                        String northString = String.format ("%.4f",N);
                        String eastString = String.format ("%.4f",E);

                        textView1.setText(eastString);
                        textView2.setText(northString);

                    }
                }

                if (dSwitch.isChecked()) {
                    /*Calcuation for 6 degree*/
                    if (aSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314140;
                        double e2 = 0.00669438002290;
                        double e2u=(e2/(1-e2));

                        double A0=1-(e2*0.25)-((0.046875)*pow(e2,2))-((0.01953125)*pow(e2,3));
                        double A2=(((0.375)*(e2))+((0.09375)*(pow(e2,2)))+((0.0439453125)*(pow(e2,3))));
                        double A4=(0.05859375)*(pow(e2,2)+((0.75)*pow(e2,3)));
                        double A6=(0.011393229167)*pow(e2,3);
                        double m=a*(A0*F-A2*sin(2*F)+A4*sin(4*F)-A6*sin(6*F));
                        double T = tan(F);
                        double W =L-L0;
                        double p =a*((1-e2)/pow(1-e2*pow(sin(F),2),1.5));
                        double v = a/sqrt(1-e2*pow(sin(F),2));
                        double w =v/p;
                        double Term1E = (pow(W,2)*0.166666666667)*pow (cos(F),2)*(w-pow(T,2));
                        double Term2E =(pow(W,4)*0.008333333333)*pow (cos(F),4)*(4*pow(w,3)*(1-6*T*T)+pow(w,2)*(1+8*T*T)-w*2*T*T+pow(T,4));
                        double Term3E = (pow(W,6)*0.000198412698)*pow (cos(F),6)*(61-479*pow(T,2)+179*pow(T,4)-pow(T,6));
                        double Term1N = (pow(W,2)*0.5)*v*sin(F)*cos(F);
                        double Term2N =(pow(W,4)*0.041666666667)*v*sin(F)*pow(cos(F),3)*(4*w*w+w-T*T);
                        double Term3N = (pow(W,6)*0.001388888889)*v*sin(F)*pow(cos(F),5)*(8*pow(w,4)*(11-24*T*T)-28*pow(w,3)*(1-6*T*T)+w*w*(1-32*T*T)-w*2*T*T+pow(T,4));
                        double Term4N = (pow(W,8)*0.000024801587)*v*sin(F)*pow(cos(F),7)*(1385-3111*T*T+543*pow(T,4)-pow(T,6));
                        double yg=0.9996*v*W*cos(F)*(1+Term1E+Term2E+Term3E);
                        double xg=0.9996*(m+Term1N+Term2N+Term3N+Term4N);
                        double E= yg+500000;
                        double N=xg;

                        String northString = String.format ("%.4f",N);
                        String eastString = String.format ("%.4f",E);

                        textView1.setText(eastString);
                        textView2.setText(northString);


                    }
                    if (bSwitch.isChecked()) {
                        int a = 6378137;
                        double b = 6356752.314245;
                        double e2 = 0.006694379990197;
                        double e2u=(e2/(1-e2));

                        double A0=1-(e2*0.25)-((0.046875)*pow(e2,2))-((0.01953125)*pow(e2,3));
                        double A2=(((0.375)*(e2))+((0.09375)*(pow(e2,2)))+((0.0439453125)*(pow(e2,3))));
                        double A4=(0.05859375)*(pow(e2,2)+((0.75)*pow(e2,3)));
                        double A6=(0.011393229167)*pow(e2,3);
                        double m=a*(A0*F-A2*sin(2*F)+A4*sin(4*F)-A6*sin(6*F));
                        double T = tan(F);
                        double W =L-L0;
                        double p =a*((1-e2)/pow(1-e2*pow(sin(F),2),1.5));
                        double v = a/sqrt(1-e2*pow(sin(F),2));
                        double w =v/p;
                        double Term1E = (pow(W,2)*0.166666666667)*pow (cos(F),2)*(w-pow(T,2));
                        double Term2E =(pow(W,4)*0.008333333333)*pow (cos(F),4)*(4*pow(w,3)*(1-6*T*T)+pow(w,2)*(1+8*T*T)-w*2*T*T+pow(T,4));
                        double Term3E = (pow(W,6)*0.000198412698)*pow (cos(F),6)*(61-479*pow(T,2)+179*pow(T,4)-pow(T,6));
                        double Term1N = (pow(W,2)*0.5)*v*sin(F)*cos(F);
                        double Term2N =(pow(W,4)*0.041666666667)*v*sin(F)*pow(cos(F),3)*(4*w*w+w-T*T);
                        double Term3N = (pow(W,6)*0.001388888889)*v*sin(F)*pow(cos(F),5)*(8*pow(w,4)*(11-24*T*T)-28*pow(w,3)*(1-6*T*T)+w*w*(1-32*T*T)-w*2*T*T+pow(T,4));
                        double Term4N = (pow(W,8)*0.000024801587)*v*sin(F)*pow(cos(F),7)*(1385-3111*T*T+543*pow(T,4)-pow(T,6));
                        double yg=0.9996*v*W*cos(F)*(1+Term1E+Term2E+Term3E);
                        double xg=0.9996*(m+Term1N+Term2N+Term3N+Term4N);
                        double E= yg+500000;
                        double N=xg;

                        String northString = String.format ("%.4f",N);
                        String eastString = String.format ("%.4f",E);

                        textView1.setText(eastString);
                        textView2.setText(northString);
                    }
                }
            }
        });

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = ct3.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rlct3);
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
