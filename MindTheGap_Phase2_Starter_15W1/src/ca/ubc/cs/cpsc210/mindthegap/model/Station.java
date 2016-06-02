package ca.ubc.cs.cpsc210.mindthegap.model;

import ca.ubc.cs.cpsc210.mindthegap.util.LatLon;

import java.util.*;

/**
 * Represents a station on the underground with an id, name, location (lat/lon)
 * set of lines with stops at this station and a list of arrival boards.
 */
public class Station implements Iterable<ArrivalBoard> {
    //Todo testing
    private List<ArrivalBoard> arrivalBoards;
    private Set<Line> lines;

    private String id;
    private String name;
    private LatLon locn;

    /**
     * Constructs a station with given id, name and location.
     * Set of lines and list of arrival boards are empty.
     *
     * @param id    the id of this station (cannot by null)
     * @param name  name of this station
     * @param locn  location of this station
     */
    public Station(String id, String name, LatLon locn) {
        // stub
        this.id=id;
        this.name=name;
        this.locn=locn;

        arrivalBoards=new ArrayList<ArrivalBoard>();
        lines=new HashSet<Line>();
    }

    public String getName() {
        return this.name;   // stub
    }

    public LatLon getLocn() {
        return this.locn;   // stub
    }

    public String getID() {
        return this.id;   // stub
    }

    public Set<Line> getLines() {
        return this.lines;   // stub
    }

    public int getNumArrivalBoards() {
        return this.arrivalBoards.size();   // stub
    }
    //extra:
    //public List<ArrivalBoard> getArrivalBoards(){return this.arrivalBoards;}

    /**
     * Add line to set of lines with stops at this station.
     *
     * @param line  the line to add
     */
    public void addLine(Line line) {
        // stub
        if(!this.lines.contains(line)){
            line.addStation(this);
            this.lines.add(line);
        }

    }

    /**
     * Remove line from set of lines with stops at this station
     *
     * @param line the line to remove
     */
    public void removeLine(Line line) {
        // stub
        if(this.lines.contains(line)){
            line.removeStation(this);
            this.lines.remove(line);
        }

    }

    /**
     * Determine if this station is on a given line
     * @param line  the line
     * @return  true if this station is on given line
     */
    public boolean hasLine(Line line) {
        return lines.contains(line);   // stub
    }

    /**
     * Add train arrival travelling on a particular line in a particular direction to this station.
     * Arrival is added to corresponding arrival board based on the line on which it is
     * operating and the direction of travel (as indicated by platform prefix).  If the arrival
     * board for given line and travel direction does not exist, it is created and added to
     * arrival boards for this station.
     *
     * @param line    line on which train is travelling
     * @param arrival the train arrival to add to station
     */
    public void addArrival(Line line, Arrival arrival) {
        // stub
        ArrivalBoard temp=new ArrivalBoard(line,arrival.getTravelDirn());
        if(arrivalBoards.contains(temp)){
            int index=arrivalBoards.indexOf(temp);
            arrivalBoards.get(index).addArrival(arrival);
        }
        else{
            temp.addArrival(arrival);
            arrivalBoards.add(temp);
        }

    }
    //
    /**
     * Remove all arrival boards from this station
     */
    public void clearArrivalBoards() {
        // stub
        arrivalBoards.clear();
    }

    /**
     * Two stations are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        // stub
        if (this == o) return true;
        if (!(o instanceof Station)) return false;

        Station stn=(Station)o;

        return this.id.equals(stn.getID());
    }

    /**
     * Two stations are equal if their ids are equal
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();   // stub
    }

    @Override
    public Iterator<ArrivalBoard> iterator() {
        // Do not modify the implementation of this method!
        return arrivalBoards.iterator();
    }
}
