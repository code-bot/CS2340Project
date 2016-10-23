package controller;

/**
 * Created by Rahul on 10/23/16.
 */
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;

import fxapp.MainFXApplication;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class MapViewController implements Initializable, MapComponentInitializedListener {

    private MainFXApplication mainApplication;

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    @FXML
    private Button button;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        LatLong loc1 = new LatLong(33.77, -84.38);
        LatLong loc2 = new LatLong(34.50, -84.60);
        LatLong loc3 = new LatLong(35.29, -85.10);
        LatLong loc4 = new LatLong(35.91, -85.92);
        LatLong loc5 = new LatLong(32.95, -83.85);


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(33.6097, -84.3331))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(loc1);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(loc2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(loc3);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(loc4);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(loc5);

        Marker loc1Marker = new Marker(markerOptions1);
        Marker loc2Marker = new Marker(markerOptions2);
        Marker loc3Marker = new Marker(markerOptions3);
        Marker loc4Marker= new Marker(markerOptions4);
        Marker loc5Marker = new Marker(markerOptions5);

        map.addMarker(loc1Marker);
        map.addMarker(loc2Marker);
        map.addMarker(loc3Marker);
        map.addMarker(loc4Marker);
        map.addMarker(loc5Marker);

    }
}
