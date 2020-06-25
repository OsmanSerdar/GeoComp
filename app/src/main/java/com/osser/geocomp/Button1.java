package com.osser.geocomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Button1 extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinatetransformations);

        btn1= findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "Geographic to Rectengular",
                        Toast.LENGTH_SHORT
                ).show();
                open_ct1();

            }
        });

        btn2= findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "Rectengular to Geographic",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct2();
            }
        });

        btn3= findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "Geographic to UTM",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct3();

            }
        });

        btn4= findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "UTM to Geographic",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct4();

            }
        });

        btn5= findViewById(R.id.button);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "Geographic to Rectengular From Folder",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct5();
            }

        });

        btn6= findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "Rectengular to Geographic From Folder",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct6();
            }
        });

        btn7= findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(
                        Button1.this,
                        "Geographic to UTM From Folder",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct7();
            }
        });

        btn8= findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        Button1.this,
                        "UTM to Geographic From Folder",
                        Toast.LENGTH_SHORT
                ).show();

                open_ct8();
            }
        });
    }

    public void open_ct1()  {
        Intent intent =new Intent(this, ct1.class);
        startActivity(intent);
    }

    public void open_ct2()  {
        Intent intent =new Intent(this, ct2.class);
        startActivity(intent);
    }

    public void open_ct3()  {
        Intent intent =new Intent(this, ct3.class);
        startActivity(intent);
    }

    public void open_ct4()  {
        Intent intent =new Intent(this, ct4.class);
        startActivity(intent);
    }
    public void open_ct5()  {
        Intent intent =new Intent(this, ct5.class);
        startActivity(intent);
    }
    public void open_ct6()  {
        Intent intent =new Intent(this, ct6.class);
        startActivity(intent);
    }
    public void open_ct7()  {
        Intent intent =new Intent(this, ct7.class);
        startActivity(intent);
    }
    public void open_ct8()  {
        Intent intent =new Intent(this, ct8.class);
        startActivity(intent);
    }
}
