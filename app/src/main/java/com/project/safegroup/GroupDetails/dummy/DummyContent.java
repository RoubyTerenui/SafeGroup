package com.project.safegroup.GroupDetails.dummy;

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
        User user1 = new User("Louis", "Blasselle", "louisbla", "1");
        User user2 = new User("Terenui", "Rouby", "tere", "2");
        User user3 = new User("Jacques", "Cply", "jacouille", "3");

        addItem(new Group("groupe de merde", "1", user1, 3));
        addItem(new Group("groupe pas mal", "2", user2, 1));
        addItem(new Group("groupe cool", "3", user3, 8));
    }

    private static void addItem(Group item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getGid(), item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

}
