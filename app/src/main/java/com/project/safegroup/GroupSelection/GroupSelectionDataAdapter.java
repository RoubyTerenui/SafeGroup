package com.project.safegroup.GroupSelection;

import android.content.Context;
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

    /*@Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.group_selection_item,parent,false);

        GroupSelectionData currentData = dataSet.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.groupName);
        name.setText(currentData.getName());

        if(currentData.isFavorite()) {
            ImageView star = (ImageView) listItem.findViewById(R.id.star);
          //  star.setImageResource( parent.getResources().getDrawable(parent.getResources().getIdentifier("favorite_icon", "drawable", getContext().getPackageName())));
        }
        ConstraintLayout layout = (ConstraintLayout) listItem.findViewById(R.id.layout);
        if(currentData.isSelected()){
            layout.setBackgroundColor(Color.BLUE);
        }
        else{
            layout.setBackgroundColor(Color.WHITE);
        }
        return listItem;
    }
}
