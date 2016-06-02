package ca.ubc.cs.cpsc210.mindthegap.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.ubc.cs.cpsc210.mindthegap.R;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

import java.util.Set;

/**
 * StationInfoWindow displayed when station is tapped
 */
class StationInfoWindow extends MarkerInfoWindow {
    private StationSelectionListener stnSelectionListener;

    private Station lastStn=null;

    /**
     * Constructor
     *
     * @param listener   listener to handle user selection of station
     * @param mapView    the map view on which this info window will be displayed
     */
    public StationInfoWindow(StationSelectionListener listener, MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
        stnSelectionListener = listener;

        Button btn = (Button) (mView.findViewById(R.id.bubble_moreinfo));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Station selected = (Station) mMarkerRef.getRelatedObject();
                stnSelectionListener.onStationSelected(selected);
                StationInfoWindow.this.close();
            }
        });
    }

    @Override public void onOpen(Object item){
        super.onOpen(item);


        Station station=(Station)mMarkerRef.getRelatedObject();
        String stnName=station.getName();

        if(lastStn!=null) {
            if (shareLine(lastStn.getLines(), station.getLines())
                    && !lastStn.getName().equals(stnName)) {
                TextView textView =
                        ((TextView) this.mView.findViewById(R.id.bubble_title));
                textView.setText(textView.getText() + "\nIN LINE WITH LAST STATION: " + lastStn.getName());
            }
            else if (lastStn.getName().equals(stnName)){
                //Do nothing
            }else {
                TextView textView =
                        ((TextView) this.mView.findViewById(R.id.bubble_title));
                textView.setText(textView.getText() + "\nNOT IN LINE WITH LAST STATION: " + lastStn.getName()
                        + "\nTRANSFER REQUIRED");
            }
        }





        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);

        lastStn=station;
    }

    private static boolean shareLine(Set<Line> s1,Set<Line> s2){
        for(Line line1:s1){
            if(s2.contains(line1)) return true;
        }
        return false;
    }


}
