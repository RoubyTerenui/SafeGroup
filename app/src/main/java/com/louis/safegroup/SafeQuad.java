package com.louis.safegroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SafeQuad extends Fragment {
    private static final String TAG = "com.louis.safegroup.SafeQuad";
    private Button myHomeButton;
    private Button friendHomeButton;
    private Button underWatchButton;
    private Button readyButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.safe_quad,container,false);
        myHomeButton = (Button) view.findViewById(R.id.myHome_button);
        friendHomeButton = (Button) view.findViewById(R.id.friendHome_button);
        underWatchButton = (Button) view.findViewById(R.id.underWatch_button);
        readyButton = (Button) view.findViewById(R.id.ready_button);


        myHomeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(8);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        friendHomeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(9);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        underWatchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(10);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });
        readyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(11);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        return view;
    }
}
