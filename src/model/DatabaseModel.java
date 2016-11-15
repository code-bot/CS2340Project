package model;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Matt Sternberg on 10/7/2016.
 */
@SuppressWarnings("ALL")
public class DatabaseModel {
    /** Set Model up as a singleton design pattern. */
    private static final DatabaseModel instance = new DatabaseModel();

    /**
     * Gets instance of database model we are currently working in
     * @return instance of DatabaseModel
     */
    public static DatabaseModel getInstance() { return instance; }

    private Firebase rootRef;
    private String uid;

    private Set<WaterSourceReport> waterSourceReports;
    private Set<WaterQualityReport> waterQualityReports;


    private DatabaseModel() {
        initFirebase();
    }

    /**
     * Initializes an instance of FirebaseDatabase to connect to the Firebase
     * database
     */
    private void initFirebase() {
        final String DATABASE_URL = "https://h2woah.firebaseio.com";
        rootRef = new Firebase(DATABASE_URL);
        initWaterReports();
        initQualityReports();
    }


    /** Currently logged in user */
    private static final ObjectProperty<User> currUser
            = new SimpleObjectProperty<>();


    /**
     * Getter for the current user
     * @return current user
     */
    public User getCurrentUser() { return currUser.get(); }

    /**
     * Sets the user id to the current user
     * @param uid id of current user
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Sets the current user to the person who just logged in. Null if logging out
     * @param user the User instance who just logged in
     * @return whether the user is set or not
     */
    public boolean setCurrentUser(User user) {
        //Can only set the current user if there is no current user
        // (Safety measure)
        if (currUser.get() == null) {
            currUser.set(user);
            return true;
        }
        return false;
    }
    /** Clears the current user for logging out */
    public void clearCurrentUser() {
        currUser.set(null);
    }

    /**
     * Create User
     * @param newUser the user information we want to store in database
     * @return whether the user was correctly added or not
     */
    public boolean createUser(User newUser) {
        rootRef.createUser(newUser.getEmail(), newUser.getPassword(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                uid = (String) stringObjectMap.get("uid");
                rootRef.child("users").child(uid).setValue(newUser);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });
        return true;
    }

    /**
     * Update a user that exists in the hashmap
     * @param previous  the user in the hashmap to update
     * @param user      the updated user
     * @param sameEmail whether the email changed or not
     * @return  successfully updated or not
     */
    public boolean updateUser(User previous, User user, boolean sameEmail) {
//        if (!userMap.containsKey(previous.getEmail())) {
//            System.out.println("Email does not exist");
//            return false;
//        } else if (!sameEmail && userMap.containsKey(user.getEmail())) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Registration Error");
//            alert.setHeaderText("Email already exists in system");
//            alert.setContentText("Please go to login page to login or use forgot password feature");
//            alert.showAndWait();
//            return false;
//        } else if (sameEmail) {
//            userMap.put(previous.getEmail(), user);
//            currUser.set(user);
//            return true;
//        } else {
//            userMap.remove(previous.getEmail(), previous);
//            userMap.put(user.getEmail(), user);
//            currUser.set(user);
//            return true;
//        }
        return false;
    }

    /**
     * Get the root reference to the firebase database
     * @return the Firebase reference
     */
    public Firebase getRootRef() {
        return rootRef;
    }

    /**
     * Logout of the server and unathenticate the current user
     */
    public void logout() {
        rootRef.unauth();
    }

    /**
     * Initialize and populate the list of water source reports with
     * the information in the firebase database
     */
    private void initWaterReports() {
        waterSourceReports = new HashSet<>();
        Firebase waterRepRef = rootRef.child("water_reports");
        waterRepRef.orderByKey().addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                waterSourceReports.add(makeSourceReport(dataSnapshot));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                WaterSourceReport waterSourceReport
                        = makeSourceReport(dataSnapshot);
                waterSourceReports.remove(waterSourceReport);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });
    }

    /**
     * Initalizes quality Reports to include everything the user entered
     */
    private void initQualityReports() {
        waterQualityReports = new HashSet<>();
        Firebase waterRepRef = rootRef.child("quality_reports");
        waterRepRef.orderByKey().addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                waterQualityReports.add(makeQualityReport(dataSnapshot));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                WaterQualityReport waterQualityReport = makeQualityReport(dataSnapshot);
                waterQualityReports.remove(waterQualityReport);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });

    }

//    /**
//     * Add the water source report to the database
//     * @param report the water source report to add to the database
//     */
//    public void submitWaterSourceReport(WaterSourceReport report) {
//        waterSourceReports.add(report);
//    }

    private WaterQualityReport makeQualityReport(DataSnapshot dataSnapshot) {
        int num = (int) (long) dataSnapshot.child("num").getValue();
        String date = (String) dataSnapshot.child("date").getValue();
        double lat = (double) dataSnapshot.child("lat").getValue();
        double lon = (double) dataSnapshot.child("long").getValue();
        String time = (String) dataSnapshot.child("time").getValue();
        String name = (String) dataSnapshot.child("name").getValue();
        WaterQualityReport.WaterSafety safety =
                WaterQualityReport.stringToSafety((String)
                        dataSnapshot.child("safety").getValue());
        double vppm = (double) dataSnapshot.child("vppm").getValue();
        double cppm = (double) dataSnapshot.child("cppm").getValue();
        return new WaterQualityReport(num, date, time, name, lat, lon, safety, vppm, cppm);
    }

    /**
     * Create a water source report from the information stored in database
     * @param dataSnapshot a DataSnapshot holding information from the Firebase database
     * @return a WaterSourceReport created from the information attributes in the database
     */
    private WaterSourceReport makeSourceReport(DataSnapshot dataSnapshot) {
        int num = (int) (long) dataSnapshot.child("num").getValue();
        String date = (String) dataSnapshot.child("date").getValue();
        double lat = (double) dataSnapshot.child("lat").getValue();
        double lon = (double) dataSnapshot.child("long").getValue();
        String time = (String) dataSnapshot.child("time").getValue();
        WaterSourceReport.WaterCondition waterCondition
                = WaterSourceReport.stringToCondition(
                (String) dataSnapshot.child("condition").getValue()
        );
        WaterSourceReport.WaterType waterType
                = WaterSourceReport.stringToWaterType(
                (String) dataSnapshot.child("type").getValue()
        );
        String name = (String) dataSnapshot.child("name").getValue();
        return new WaterSourceReport(num, date, time, name, lat, lon, waterType, waterCondition);
    }

    /**
     * Add a WaterSourceReport to the waterSourceReports json tree in Firebase
     * database.
     * @param waterSourceReport the source report to add to the database
     * @return a boolean indicating whether the creation operation in the
     * database has succeeded
     */
    public boolean addReport(WaterSourceReport waterSourceReport) {
        Firebase reportsRef = rootRef.child("water_reports");
        try {
            reportsRef.child(String.valueOf(waterSourceReport.getNum())).setValue(waterSourceReport);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Add a WaterSourceReport to the waterQualityReports json tree in Firebase
     * database
     * @param waterQualityReport the quality report to add to the database
     * @return a boolean indicating whether the creation operation in the
     * database has succeeded
     */
    public boolean addReport(WaterQualityReport waterQualityReport) {
        Firebase qualityRef = rootRef.child("quality_reports");
        try {
            qualityRef.child(String.valueOf(waterQualityReport.getNum())).setValue(waterQualityReport);
            //waterQualityReports.add(waterQualityReport);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Get source Reports in the form of a set of reports
     * @return the set of source reports
     */
    public Set<WaterSourceReport> getSourceReports() {
        return waterSourceReports;
    }

    /**
     * Getter for Quality Reports
     * @return the set of water quality reports
     */
    public Iterable<WaterQualityReport> getWaterQualityReports() { return waterQualityReports; }

    /**
     * Method to reset the password for someone who has forgotten theirs
     * @param email the email of the person who forgot their password
     */
    public void forgotPassword(String email) {
        rootRef.resetPassword(email, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                System.out.println("Successful password reset!");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });
    }

    /**
     * Method to change password for someone
     * @param email the email of the user who forgot password
     * @param oldPass old password of the user
     * @param newPass the new password they want it to be set to
     */
    public void changePassword(String email, String oldPass, String newPass) {
        rootRef.changePassword(email, oldPass, newPass,
                new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                rootRef.child("users").child(uid).child("password")
                        .setValue(newPass);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });

    }
}
