package controller;

/**
 * Created by Rahul on 10/23/16.
 */
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import java.util.Set;

import model.DatabaseModel;
import model.WaterSourceReport;
import netscape.javascript.JSObject;
import fxapp.MainFXApplication;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.Model;
import model.Report;


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

        Set<WaterSourceReport> reports = new HashSet<>();
        reports = DatabaseModel.getInstance().getSourceReports();
        for (Report report : reports) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(report.getLat(), report.getLong());
            markerOptions.position(loc);
            Marker marker = new Marker( markerOptions );
            map.addMarker(marker);
            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(report.toString());

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);});
        }
    }
}
