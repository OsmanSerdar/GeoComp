package com.osser.geocomp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FragmentHome extends Fragment {

    Button btn1;
    Button btn2;
    Button btn3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);

        btn1= view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Coordinate Transformations",Toast.LENGTH_SHORT).show();
                open_Button1();

            }
        });

        btn2= view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Datum Transformations",Toast.LENGTH_SHORT).show();
                open_Button2();

            }
        });

        btn3= view.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"TR Map Sheet Finder",Toast.LENGTH_SHORT).show();
                open_Button3();
            }
        });

        return view;
    }

    public void open_Button1()  {

        Intent intent = new Intent(getActivity(), Button1.class);
        startActivity(intent);
    }

    public void open_Button2()  {

        Intent intent = new Intent(getActivity(), Button2.class);
        startActivity(intent);

    }
    public void open_Button3()  {

        Intent intent = new Intent(getActivity(), TR_map_sheet_finder.class);
        startActivity(intent);
    }
}