package com.app.trip;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class FirebaseInstance {

    public static FirebaseAuth auth;
    public static DatabaseReference databaseReference;

    public FirebaseInstance() {
        auth = FirebaseAuth.getInstance();
        databaseReference =  FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseAuth getFireBaseAuth() {
        return auth;
    }

    public  String getUserEmail() {
        return Objects.requireNonNull(auth.getCurrentUser()).getEmail();
    }

    public  String getUserId() {
        return auth.getCurrentUser().getUid();
    }

    public  DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
