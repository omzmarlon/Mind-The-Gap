package ca.ubc.cs.cpsc210.mindthegap.model;

import ca.ubc.cs.cpsc210.mindthegap.model.exception.StationException;
import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;
import ca.ubc.cs.cpsc210.mindthegap.util.SphericalGeometry;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Manages all tube stations on network.
 *
 * Singleton pattern applied to ensure only a single instance of this class that
 * is globally accessible throughout application.
 */
public class StationManager implements Iterable<Station> {
    //Todo testing
    public static final int RADIUS = 10000;
    private static StationManager instance;
    private Set<Station> stns;
    private Station selected;

    /**
     * Constructs station manager with empty set of stations and null as the selected station
     */
    private StationManager() {
        // stub
        stns=new HashSet<Station>();
        selected=null;
    }

    /**
     * Gets one and only instance of this class
     *
     * @return  instance of class
     */
    public static StationManager getInstance() {
        // Do not modify the implementation of this method!
        if(instance == null) {
            instance = new StationManager();
        }

        return instance;
    }

    public Station getSelected() {
        return selected;  // stub
    }

    /**
     * Get station with given id or null if no such station is found in this manager
     *
     * @param id  the id of this station
     *
     * @return  station with given id or null if no such station is found
     */
    public Station getStationWithId(String id) {
        for(Station stn:stns){
            if(stn.getID().equals(id))
                return stn;
        }
        return null;
    }

    /**
     * Set the station selected by user
     *
     * @param selected   station selected by user
     * @throws StationException when station manager doesn't contain selected station
     */
    public void setSelected(Station selected) throws StationException {
        // stub
        if(stns.contains(selected)) this.selected=selected;
        else throw new StationException();
    }

    /**
     * Clear selected station (selected station is null)
     */
    public void clearSelectedStation() {
        // stub
        this.selected=null;
    }

    /**
     * Add all stations on given line. Station added only if it is not already in the collection.
     *
     * @param line  the line from which stations are to be added
     */
    public void addStationsOnLine(Line line) {
        // stub
        List<Station> los=line.getStations();
        for(Station s : los){
            if(!this.stns.contains(s)) this.stns.add(s);
        }
    }

    /**
     * Get number of stations managed
     *
     * @return  number of stations added to manager
     */
    public int getNumStations() {
        return stns.size();   // stub
    }

    /**
     * Remove all stations from station manager
     */
    public void clearStations() {
        // stub
        stns.clear();
        this.selected=null;
    }

    /**
     * Find nearest station to given point.  Returns null if no station is closer than RADIUS metres.
     *
     * @param pt  point to which nearest station is sought
     * @return    station closest to pt but less than 10,000m away; null if no station is within RADIUS metres of pt
     */
    public Station findNearestTo(LatLon pt) {
        // stub
        for(Station station : stns){
            if(SphericalGeometry.distanceBetween(pt,station.getLocn())<RADIUS)
                return station;
        }
        return null;
    }

    private double distance(LatLon p1,LatLon p2){
        double d=Math.pow(p1.getLatitude() - p2.getLatitude(),2) +
                Math.pow(p1.getLongitude()-p2.getLongitude(),2);
        return Math.sqrt(d);
    }

    @Override
    public Iterator<Station> iterator() {
        // Do not modify the implementation of this method!
        return stns.iterator();
    }
}
