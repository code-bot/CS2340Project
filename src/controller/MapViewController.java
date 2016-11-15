package controller;



import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.DatabaseModel;
import model.Report;
import model.WaterSourceReport;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by Rahul on 10/23/16.
 */
public class MapViewController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void mapInitialized() {
        final double ATLLAT = 33.7490;
        final double ATLLONG = -84.3880;
        final int DEFAULT_ZOOM = 12;

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(ATLLAT, ATLLONG))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(DEFAULT_ZOOM);

        map = mapView.createMap(mapOptions);

        DatabaseModel databaseModel = DatabaseModel.getInstance();
        Set<WaterSourceReport> reports = databaseModel.getSourceReports();
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
