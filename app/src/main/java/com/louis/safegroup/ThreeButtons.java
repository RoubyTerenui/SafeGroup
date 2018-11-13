package com.louis.safegroup;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.louis.safegroup.R;

public class ThreeButtons extends Fragment {
    private static final String TAG = "com.louis.safegroup.ThreeButtons";
    private Button redButton;
    private Button orangeButton;
    private Button greenButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.three_buttons_layout,container,false);
        redButton = (Button) view.findViewById(R.id.danger_button);
        orangeButton = (Button) view.findViewById(R.id.problem_button);
        greenButton = (Button) view.findViewById(R.id.safe_button);


        redButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setState(0);
                ((MainActivity)getActivity()).setFragment(1);
            }
        });

        orangeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setState(1);
                ((MainActivity)getActivity()).setFragment(2);
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setState(2);
                ((MainActivity)getActivity()).setFragment(3);
            }
        });

        return view;
    }
}
