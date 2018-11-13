package com.project.safegroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.louis.safegroup.MainActivity;
import com.louis.safegroup.R;

public class DangerQuad extends Fragment {
    private static final String TAG = "com.louis.safegroup.DangerQuad";
    private Button hurtButton;
    private Button aggressedButton;
    private Button freezeButton;
    private Button lostButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.danger_quad,container,false);
        hurtButton = (Button) view.findViewById(R.id.hurt_button);
        aggressedButton = (Button) view.findViewById(R.id.agressed_button);
        freezeButton = (Button) view.findViewById(R.id.freeze_button);
        lostButton = (Button) view.findViewById(R.id.lost_button);


        hurtButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(0);
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });

        aggressedButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(1);
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });

        freezeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(2);
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });
        lostButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(3);
                ((MainActivity)getActivity()).setViewPager(4);
            }
        });

        return view;
    }
}
