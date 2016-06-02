package ca.ubc.cs.cpsc210.mindthegap.parsers;

import ca.ubc.cs.cpsc210.mindthegap.model.*;
import ca.ubc.cs.cpsc210.mindthegap.parsers.exception.TfLLineDataMissingException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * A parser for the data returned by TFL line route query
 */
public class TfLLineParser extends TfLAbstractParser {

    /**
     * Parse line from JSON response produced by TfL.  No stations added to line if TfLLineDataMissingException
     * is thrown.
     *
     * @param lmd              line meta-data
     * @return                 line parsed from TfL data
     * @throws JSONException   when JSON data does not have expected format
     * @throws TfLLineDataMissingException when
     * <ul>
     *  <li> JSON data is missing lineName, lineId or stopPointSequences elements </li>
     *  <li> for a given sequence: </li>
     *    <ul>
     *      <li> the stopPoint array is missing </li>
     *      <li> all station elements are missing one of name, lat, lon or stationId elements </li>
     *    </ul>
     * </ul>
     */

    private static Set<Branch> branchesForLine=new HashSet<Branch>();
    private static List<Station> stns=new LinkedList<Station>();

    public static Line parseLine(LineResourceData lmd, String jsonResponse)
            throws JSONException, TfLLineDataMissingException {

        Line line;
        JSONObject jsonObjectLine=new JSONObject(jsonResponse);

        if(!jsonObjectLine.has("stopPointSequences"))
            throw new TfLLineDataMissingException();

        JSONArray stopPointSequences=jsonObjectLine.getJSONArray("stopPointSequences");
        JSONArray lineStrings=jsonObjectLine.getJSONArray("lineStrings");


        /*int missingNum=0;
        int totalNum=0;
        for(int a=0;a<stopPointSequences.length();a++){
            if(!stopPointSequences.getJSONObject(a).has("stopPoint"))
                throw new TfLLineDataMissingException();
            JSONArray stopPoints=
                    stopPointSequences.getJSONObject(a).getJSONArray("stopPoint");
            for(int k=0;k<stopPoints.length();k++){
                totalNum++;
                if(!stopPoints.getJSONObject(k).has("stationId")
                        ||!stopPoints.getJSONObject(k).has("name")
                        ||!stopPoints.getJSONObject(k).has("lat")
                        ||!stopPoints.getJSONObject(k).has("lon"))
                    missingNum++;
            }
        }
        if(missingNum==totalNum) throw new TfLLineDataMissingException();*/



        parseBranch(lineStrings);
        parseStation(stopPointSequences);

        if(!jsonObjectLine.has("lineId"))
            throw new TfLLineDataMissingException();
        if(!jsonObjectLine.has("lineName"))
            throw new TfLLineDataMissingException();


        line =new Line(lmd,
                jsonObjectLine.getString("lineId"),
                jsonObjectLine.getString("lineName"));

        for(Branch b:branchesForLine) line.addBranch(b);
        for(Station s:stns) line.addStation(s);

        return line;
    }

    private static void parseStation(JSONArray stopPointSequences)
            throws JSONException, TfLLineDataMissingException {

        /*Todo assuming it hasn't already been constructed when a different line was parsed.
        If the station has already been constructed, add the existing station to
        the line rather than creating a duplicate.*/


        for(int i=0;i<stopPointSequences.length();i++){
            if(!stopPointSequences.getJSONObject(i).has("stopPoint"))
                throw new TfLLineDataMissingException();

            JSONArray stopPoints=
                    stopPointSequences.getJSONObject(i).getJSONArray("stopPoint");

            int missingNum=0;
            for(int k=0;k<stopPoints.length();k++){

                if(!stopPoints.getJSONObject(k).has("stationId")
                        ||!stopPoints.getJSONObject(k).has("name")
                        ||!stopPoints.getJSONObject(k).has("lat")
                        ||!stopPoints.getJSONObject(k).has("lon"))
                    missingNum++;
            }
            if(missingNum == stopPoints.length())
                throw new TfLLineDataMissingException();

            for(int j=0;j<stopPoints.length();j++){
                /*if(!stopPoints.getJSONObject(j).has("stationId")
                        ||!stopPoints.getJSONObject(j).has("name")
                        ||!stopPoints.getJSONObject(j).has("lat")
                        ||!stopPoints.getJSONObject(j).has("lon"))
                    throw new TfLLineDataMissingException();*/

                if(stopPoints.getJSONObject(j).has("stationId")
                        &&stopPoints.getJSONObject(j).has("name")
                        &&stopPoints.getJSONObject(j).has("lat")
                        &&stopPoints.getJSONObject(j).has("lon")){
                    String stationId = stopPoints.getJSONObject(j).getString("stationId");
                    String name=stopPoints.getJSONObject(j).getString("name");
                    LatLon latLon
                            =new LatLon(stopPoints.getJSONObject(j).getDouble("lat"),
                            stopPoints.getJSONObject(j).getDouble("lon"));
                    Station station=
                            new Station(stationId,TfLAbstractParser.parseName(name),latLon);
                    if(!stns.contains(station))
                        stns.add(station);
                }
            }

        }

    }


    private static void parseBranch(JSONArray lineStrings) throws JSONException {
        for(int i=0;i<lineStrings.length();i++){
            branchesForLine.add(new Branch(lineStrings.getString(i)));
        }

    }
}
