package com.project.safegroup;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.safegroup.R;

import org.w3c.dom.Text;

public class NotificationRecap extends Fragment {
    private static final String TAG = "com.louis.safegroup.NotificationRecap";
    private Button backButton;
    private TextView detailText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notification_recap,container,false);
        backButton = (Button) view.findViewById(R.id.back_button);
        detailText = (TextView) view.findViewById(R.id.detail_notification);
        backButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });
        return view;
    }
}
