package com.osser.geocomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Button2 extends AppCompatActivity {
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datumtransformations);

        btn3= findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button2.this,
                        "WG84 to ED50 3D",
                        Toast.LENGTH_SHORT
                ).show();

                open_dt3();



            }
        });

        btn4= findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button2.this,
                        "ED50 to WGS84 3D",
                        Toast.LENGTH_SHORT
                ).show();

                open_dt4();

            }
        });

        btn5= findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button2.this,
                        "Affine Transformation",
                        Toast.LENGTH_SHORT
                ).show();

                open_dt5();

            }
        });

        btn6= findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button2.this,
                        "Helmert Transformation",
                        Toast.LENGTH_SHORT
                ).show();

                open_dt6();



            }
        });

        btn7= findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button2.this,
                        "Bursa-Wolf Transformation",
                        Toast.LENGTH_SHORT
                ).show();

                open_dt7();


            }
        });

    }

    public void open_dt3()  {
        Intent intent =new Intent(this, dt3.class);
        startActivity(intent);
    }

    public void open_dt4()  {
        Intent intent =new Intent(this, dt4.class);
        startActivity(intent);
    }

    public void open_dt5()  {
        Intent intent =new Intent(this, dt5.class);
        startActivity(intent);
    }

    public void open_dt6()  {
        Intent intent =new Intent(this, dt6.class);
        startActivity(intent);
    }

    public void open_dt7()  {
        Intent intent =new Intent(this, dt7.class);
        startActivity(intent);
    }
}
