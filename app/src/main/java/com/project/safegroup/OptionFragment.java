package com.project.safegroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.safegroup.R;


public class OptionFragment extends Fragment {

    private ListView listView ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option,container,false);

        // Get ListView object from xml
        listView = (ListView) view.findViewById(R.id.listOptions);

        // Defined Array values to show in ListView
        String[] values = new String[] {getString(R.string.deconnexion) ,
                "Futur Option",
                "Rejoindre un groupe",
        };


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        AuthUI.getInstance()
                            .signOut(getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    ((MainActivity)getActivity()).setFragment(0);//TO DO NE pas passer par la 1 ere page
                                    ((MainActivity)getActivity()).checkLogin();

                                }
                            });
                        break;
                    case 1:
                        AuthUI.getInstance()
                                .delete(getContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ((MainActivity)getActivity()).setFragment(0);//TO DO NE pas passer par la 1 ere page
                                        ((MainActivity)getActivity()).checkLogin();
                                    }
                                });
                        break;
                    case 2 :
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

                        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Rejoindre un groupe");

                        // Setting Dialog Message
                        alertDialog.setMessage("Entrer le lien du groupe");
                        final EditText input = new EditText(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        alertDialog.setView(input);

                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("Confirmer",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int which) {
                                        // Write your code here to execute after dialog
                                        Toast.makeText(getContext(),"Vous avez rejoint le groupe", Toast.LENGTH_SHORT).show();

                                        //Ici le code pour faire rejoindre un user à un groupe
                                        
                                    }
                                });
                        // Setting Negative "NO" Button
                        alertDialog.setNegativeButton("Annuler",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        // closed

                        // Showing Alert Message
                        alertDialog.show();

                        break;

                    default:
                        break;

                }


            }

        });
        return view;
    }

}