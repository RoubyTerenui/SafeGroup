package dataBase.collections;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dataBase.model.User;

public class UserHelper {

    private static final String COLLECTION_NAME = "users";


    // --- COLLECTION REFERENCE ---


    public static CollectionReference getUsersCollection(){

        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);

    }


    // --- CREATE ---

    public static Task<Void> createUser(String firstname, String lastname, String nickname, String uid) {
        User userToCreate = new User(firstname,lastname,nickname,uid);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---


    public static Task<DocumentSnapshot> getUser(String uid){

        return UserHelper.getUsersCollection().document(uid).get();

    }


    // --- UPDATE ---


    public static Task<Void> update_Firstname(String firstname, String uid) {

        return UserHelper.getUsersCollection().document(uid).update("firstname", firstname);

    }

    public static Task<Void> update_Lastname(String lastname, String uid) {

        return UserHelper.getUsersCollection().document(uid).update("lastname", lastname);

    }
    public static Task<Void> update_Nickname(String nickname, String uid) {

        return UserHelper.getUsersCollection().document(uid).update("nickname", nickname);

    }



    // --- DELETE ---


    public static Task<Void> deleteUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).delete();

    }
}
