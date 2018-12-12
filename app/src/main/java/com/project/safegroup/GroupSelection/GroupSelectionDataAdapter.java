package com.project.safegroup.GroupSelection;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.safegroup.R;

import java.util.ArrayList;

public class GroupSelectionDataAdapter  extends ArrayAdapter<GroupSelectionData> {

    private ArrayList<GroupSelectionData> dataSet;
    Context mContext;


    public GroupSelectionDataAdapter(ArrayList<GroupSelectionData> data, Context context) {
        super(context, 0, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.group_selection_item,parent,false);

        GroupSelectionData currentData = dataSet.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.groupName);
        name.setText(currentData.getName());
        Button favoriteButton = (Button) listItem.findViewById(R.id.star);
        Button partyButton = (Button) listItem.findViewById(R.id.party);
        Resources res = mContext.getResources();
        if(currentData.isFavorite()) {
            favoriteButton.setBackground(res.getDrawable(R.drawable.favori_valide));
        }
        else {
            favoriteButton.setBackground(res.getDrawable(R.drawable.favori_off));
        }
        if(currentData.isParty()){
            partyButton.setBackground(res.getDrawable(R.drawable.fete_valide));
        }
        else{
            partyButton.setBackground(res.getDrawable(R.drawable.fete_off));;
        }
        if(currentData.isSelected()){
            listItem.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlueButton));
        }
        else{
            listItem.setBackgroundColor(mContext.getResources().getColor(R.color.light_grey_color));
        }
        return listItem;
    }
}
