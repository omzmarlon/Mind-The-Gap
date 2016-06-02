package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.Arrival;
import ca.ubc.cs.cpsc210.mindthegap.model.Line;
import ca.ubc.cs.cpsc210.mindthegap.model.Station;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * A parser for the data returned by the TfL station arrivals query
 */
public class TfLArrivalsParser extends TfLAbstractParser {

    /**
     * Parse arrivals from JSON response produced by TfL query.  All parsed arrivals are
     * added to given station assuming that corresponding JSON object as all of:
     * timeToStation, platformName, lineID and one of destinationName or towards.  If
     * any of the aforementioned elements is missing, the arrival is not added to the station.
     *
     * @param stn             station to which parsed arrivals are to be added
     * @param jsonResponse    the JSON response produced by TfL
     * @throws JSONException  when JSON response does not have expected format
     * @throws TfLArrivalsDataMissingException  when all arrivals are missing at least one of the following:
     * <ul>
     *     <li>timeToStation</li>
     *     <li>platformName</li>
     *     <li>lineId</li>
     *     <li>destinationName and towards</li>
     * </ul>
     */
    public static void parseArrivals(Station stn, String jsonResponse)
            throws JSONException, TfLArrivalsDataMissingException {
        // stub
        JSONArray jsonArray=new JSONArray(jsonResponse);
        int missingSize=0;
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            int timeToStation;
            String platformName;
            String lineId;
            String destName;



            if(jsonObject.has("timeToStation")
                    &&jsonObject.has("platformName")
                    &&jsonObject.has("lineId")) {

                timeToStation=jsonObject.getInt("timeToStation");
                platformName=jsonObject.getString("platformName");
                lineId=jsonObject.getString("lineId");

                if(!jsonObject.has("destinationName")){
                    if(!jsonObject.has("towards")){
                        missingSize++;
                    }
                    else{
                        destName=jsonObject.getString("towards");
                        for(Line line:stn.getLines()){
                            if(line.getId().equals(lineId))
                                stn.addArrival(
                                        line,new Arrival(timeToStation,destName,
                                                platformName));
                        }
                    }
                }else{
                    destName=TfLAbstractParser.parseName(
                            jsonObject.getString("destinationName"));
                    for(Line line:stn.getLines()){
                        if(line.getId().equals(lineId))
                            stn.addArrival(
                                    line,new Arrival(timeToStation,destName,
                                            platformName));
                }
            }

            }else{missingSize++;}

        }
        if(missingSize==jsonArray.length())
            throw new TfLArrivalsDataMissingException();
    }
}




            /*if(!jsonObject.has("timeToStation"))
                throw new TfLArrivalsDataMissingException();
            else
                timeToStation=jsonObject.getInt("timeToStation");

            if(!jsonObject.has("platformName"))
                throw new TfLArrivalsDataMissingException();
            else
                platformName=jsonObject.getString("platformName");

            if(!jsonObject.has("lineId"))
                throw new TfLArrivalsDataMissingException();
            else
                lineId=jsonObject.getString("lineId");

            if(!jsonObject.has("destinationName")){
                if(!jsonObject.has("towards"))
                    throw new TfLArrivalsDataMissingException();
                else
                    destName=jsonObject.getString("towards");
            }else
                destName=TfLAbstractParser.parseName(jsonObject.getString("destinationName"));

            for(Line line:stn.getLines()){
                if(line.getId().equals(lineId))
                    stn.addArrival(line,new Arrival(timeToStation,destName,platformName));*/
