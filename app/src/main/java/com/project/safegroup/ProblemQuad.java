package com.project.safegroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.safegroup.R;

public class ProblemQuad extends Fragment {
    private static final String TAG = "com.louis.safegroup.ProblemQuad";
    private Button sadButton;
    private Button NeedLoveButton;
    private Button angryButton;
    private Button NeedOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.problem_quad,container,false);
        sadButton = (Button) view.findViewById(R.id.sad_button);
        NeedLoveButton = (Button) view.findViewById(R.id.needLove_button);
        angryButton = (Button) view.findViewById(R.id.angry_button);
        NeedOutButton = (Button) view.findViewById(R.id.needOut_button);


        sadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(4);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        NeedLoveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(5);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        angryButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(6);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });
        NeedOutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setStatePrecision(7);
                ((MainActivity)getActivity()).setFragment(4);
            }
        });

        return view;
    }
}
