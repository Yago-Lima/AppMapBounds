package com.example.appmapbounds;

import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {
    private MapView map;
    private final GeoPoint fixedCenter = new GeoPoint(-10.3282, -48.2994);
    private final BoundingBox boundingBox = new BoundingBox(-12.0,-48.0,-12.0,-50.0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        addMarcador(fixedCenter);

        map.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                GeoPoint center = (GeoPoint) map.getMapCenter();
                if (!boundingBox.contains(center)) {
                    map.getController().setCenter(fixedCenter);
                }
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                return false;
            }
        });
    }

    private void addMarcador(GeoPoint fixedCenter) {
        Marker marker = new Marker(map);
        marker.setPosition(fixedCenter);
        marker.setTitle("Oi, estou aqui");
        map.getOverlays().add(marker);
        map.getController().setZoom(15.0);
        map.getController().setCenter(fixedCenter);
//        map.setScrollableAreaLimitDouble(boundingBox);
    }//

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onPause();
    }
}//class