package ca.ubc.cs.cpsc210.mindthegap.model;

import java.util.*;

/**
 * Represents a line on the underground with a name, id, list of stations and list of branches.
 *
 * Invariants:
 * - no duplicates in list of stations
 * - iterator iterates over stations in the order in which they were added to the line
 */
public class Line implements Iterable<Station> {
    //Todo testing
    private Set<Branch> branches;
    private List<Station> stns;

    private LineResourceData lmd;
    private String id;
    private String name;

    /**
     * Constructs a line with given resource data, id and name.
     * List of stations and list of branches are empty.
     *
     * @param lmd     the line meta-data
     * @param id      the line id
     * @param name    the name of the line
     */
    public Line(LineResourceData lmd, String id, String name) {
        // stub
        this.lmd=lmd;
        this.id=id;
        this.name=name;
        branches=new HashSet<Branch>();
        stns=new LinkedList<Station>();
    }

    public String getName() {
        return name;   // stub
    }

    public String getId() {
        return id;   // stub
    }

    /**
     * Get colour specified by line resource data
     *
     * @return  colour in which to plot this line
     */
    public int getColour() {
        return this.lmd.getColour();   // stub
    }

    /**
     * Add station to line.
     *
     * @param stn  the station to add to this line
     */
    public void addStation(Station stn) {
        // stub
        if(!stns.contains(stn)){
            this.stns.add(stn);
            stn.addLine(this);
        }

    }

    /**
     * Remove station from line
     *
     * @param stn  the station to remove from this line
     */
    public void removeStation(Station stn) {
        // stub
        if(this.stns.contains(stn)){
            stn.removeLine(this);
            this.stns.remove(stn);
        }

    }

    /**
     * Clear all stations from this line
     */
    public void clearStations() {
        // stub
        //Todo change2
        for(Station s:stns){
            s.getLines().remove(this);
        }
        this.stns.clear();
    }

    public List<Station> getStations() {
        return this.stns;   // stub
    }

    /**
     * Determines if this line has a stop at a given station
     *
     * @param stn  the station
     * @return  true if line has a stop at given station
     */
    public boolean hasStation(Station stn) {
        return this.stns.contains(stn);   // stub
    }

    /**
     * Add a branch to this line
     *
     * @param b  the branch to add to line
     */
    public void addBranch(Branch b) {
        // stub
        this.branches.add(b);
    }

    public Set<Branch> getBranches() {
        return branches;   // stub
    }

    /**
     * Two lines are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {// stub
        if (this == o) return true;
        if (!(o instanceof Line)) return false;

        Line line=(Line)o;

        return this.id.equals(line.getId());
    }

    /**
     * Two lines are equal if their ids are equal
     */
    @Override
    public int hashCode() {
        return this.id.hashCode();   // stub
    }

    @Override
    public Iterator<Station> iterator() {
        // Do not modify the implementation of this method!
        return stns.iterator();
    }
}
