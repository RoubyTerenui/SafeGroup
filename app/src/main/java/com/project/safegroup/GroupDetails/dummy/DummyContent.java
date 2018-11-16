package com.project.safegroup.GroupDetails.dummy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataBase.model.Group;
import dataBase.model.User;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Group> ITEMS = new ArrayList<Group>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Group> ITEM_MAP = new HashMap<String, Group>();

    private static final int COUNT = 25;

    static {
        DatabaseReference mDatabaseReference= FirebaseDatabase.getInstance().getReference();
        User user1 = new User( "louisbla", "1", "louisbla@gmail.com",null,mDatabaseReference);
        User user2 = new User( "tere", "2", "terenuirouby@gmail.com",null,mDatabaseReference);
        User user3 = new User( "Cply", "3", "jacqueCply@gmail.com",null,mDatabaseReference);
        List<Integer> list1= new ArrayList<Integer>();
        list1.add(3);
        List<Integer> list2= new ArrayList<Integer>();
        list2.add(1);
        List<Integer> list3=new ArrayList<Integer>();
        list3.add(8);
        addItem(new Group("groupe de merde", "g1", user1.getNickname(),list1,mDatabaseReference));
        addItem(new Group("groupe pas mal", "g2", user2.getNickname(), list2,mDatabaseReference));
        addItem(new Group("groupe cool", "g3", user3.getNickname(), list3,mDatabaseReference));
    }

    private static void addItem(Group item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGr_id(), item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

}
