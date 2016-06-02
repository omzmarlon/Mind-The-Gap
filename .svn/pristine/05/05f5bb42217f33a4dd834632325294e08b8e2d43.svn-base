package ca.ubc.cs.cpsc210.mindthegap.TfL;

import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Wrapper for TfL Arrival Data Provider
 */
public class TfLHttpArrivalDataProvider extends AbstractHttpDataProvider {
    private Station stn;

    public TfLHttpArrivalDataProvider(Station stn) {
        super();
        this.stn = stn;
    }

    @Override
    /**
     * Produces URL used to query TfL web service for expected arrivals at
     * station specified in call to constructor.
     *
     * @returns URL to query TfL web service for arrival data
     */
    protected URL getURL() throws MalformedURLException {
        String request = "";
        request+="https://api.tfl.gov.uk/Line/";

        String lineids="";
        Set<Line> lines=stn.getLines();
        for(Line line : lines){
            lineids+=line.getId()+",";
        }
        lineids=lineids.substring(0,lineids.length()-1);
        request+=lineids;
        request+="/Arrivals?stopPointId=";

        String stationid=stn.getID();
        request+=stationid;
        request+="&app_id=&app_key=";

        // TODO Phase 2 Task 7

        return new URL(request);
    }
}
