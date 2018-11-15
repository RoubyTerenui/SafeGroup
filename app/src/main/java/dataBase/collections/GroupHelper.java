package dataBase.collections;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dataBase.model.Group;
import dataBase.model.User;

public class GroupHelper {

    private static final String COLLECTION_NAME = "groups";


    // --- COLLECTION REFERENCE ---


    public static CollectionReference getGroupsCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);

    }


    // --- CREATE ---

    public static Task<Void> createGroup(String name, String gid, User administrator, int typeOfGroup) {
        Group groupToCreate = new Group(name,gid,administrator,typeOfGroup);
        return UserHelper.getUsersCollection().document(gid).set(groupToCreate);
    }

    // --- GET ---


    public static Task<DocumentSnapshot> getUser(String gid){

        return UserHelper.getUsersCollection().document(gid).get();

    }


    // --- UPDATE ---


    public static Task<Void> update_Administrator(User administrator, String gid) {

        return UserHelper.getUsersCollection().document(gid).update("Admin", administrator);

    }

    public static Task<Void> update_GroupName(String name, String gid) {

        return UserHelper.getUsersCollection().document(gid).update("name", name);

    }
    public static Task<Void> update_Nickname(int typeOfGroup, String gid) {

        return UserHelper.getUsersCollection().document(gid).update("typeOfGroup", typeOfGroup);

    }

    // --- DELETE ---


    public static Task<Void> deleteUser(String gid) {

        return UserHelper.getUsersCollection().document(gid).delete();
    }

}
