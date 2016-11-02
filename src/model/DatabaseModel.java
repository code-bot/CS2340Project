package model;

import com.firebase.client.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Matt Sternberg on 10/7/2016.
 */
public class DatabaseModel {
    /** Set Model up as a singleton design pattern */
    private static final DatabaseModel instance = new DatabaseModel();
    public static DatabaseModel getInstance() { return instance; }

    private final String DATABASE_URL = "https://h2woah.firebaseio.com";
    private Firebase rootRef;
    private String uid;

    private Set<WaterSourceReport> waterSourceReports;
    private Set<WaterQualityReport> waterQualityReports;


    DatabaseModel() {
        initFirebase();
    }

    /**
     * Initializes an instance of FirebaseDatabase to connect to the Firebase database
     */
    public void initFirebase() {
        rootRef = new Firebase(DATABASE_URL);
        initWaterReports();
        initQualityReports();
    }


    /** Currently logged in user */
    private static final ObjectProperty<User> currUser = new SimpleObjectProperty<>();


    /** Getter and setter for the currUser */
    public User getCurrentUser() { return currUser.get(); }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public boolean setCurrentUser(User user) {
        //Can only set the current user if there is no current user (Safety measure)
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
     * Create user
     */
    public boolean createUser(User newUser) {
        rootRef.createUser(newUser.getEmail(), newUser.getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
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
     * Get the root reference to the firebase database
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
    public void initWaterReports() {
        waterSourceReports = new HashSet<>();
        Firebase waterRepRef = rootRef.child("water_reports");
        waterRepRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                waterSourceReports.add(makeSourceReport(dataSnapshot));
                System.out.println("Added source report");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                WaterSourceReport waterSourceReport = makeSourceReport(dataSnapshot);
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

    public void initQualityReports() {
        waterQualityReports = new HashSet<>();
        Firebase waterRepRef = rootRef.child("quality_reports");
        waterRepRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                waterQualityReports.add(makeQualityReport(dataSnapshot));
                System.out.println("Added quality report");
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
                WaterQualityReport.stringToSafety((String) dataSnapshot.child("safety").getValue());
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
        WaterSourceReport.WaterCondition waterCondition = WaterSourceReport.stringToCondition(
                (String) dataSnapshot.child("condition").getValue()
        );
        WaterSourceReport.WaterType waterType = WaterSourceReport.stringToWaterType(
                (String) dataSnapshot.child("type").getValue()
        );
        String name = (String) dataSnapshot.child("name").getValue();
        return new WaterSourceReport(num, date, time, name, lat, lon, waterType, waterCondition);
    }

    /**
     * Add a WaterSourceReport to the waterSourceReports json tree in Firebase database
     * @param waterSourceReport the source report to add to the database
     * @return a boolean indicating whether the creation operation in the database has succeeded
     */
    public boolean addReport(WaterSourceReport waterSourceReport) {
        Firebase reportsRef = rootRef.child("water_reports");
        try {
            System.out.println("trying source report");
            reportsRef.child(String.valueOf(waterSourceReport.getNum())).setValue(waterSourceReport);
            //waterSourceReports.add(waterSourceReport);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Add a WaterSourceReport to the waterQualityReports json tree in Firebase database
     * @param waterQualityReport the quality report to add to the database
     * @return a boolean indicating whether the creation operation in the database has succeeded
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

    public Set<WaterSourceReport> getSourceReports() {
        return waterSourceReports;
    }

    public Set<WaterQualityReport> getWaterQualityReports() { return waterQualityReports; }

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

    public void changePassword(String email, String oldPass, String newPass) {
        rootRef.changePassword(email, oldPass, newPass, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                System.out.println("Password successfully changed to " + newPass);
                rootRef.child("users").child(uid).child("password").setValue(newPass);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println(firebaseError.getMessage());
            }
        });

    }
}
